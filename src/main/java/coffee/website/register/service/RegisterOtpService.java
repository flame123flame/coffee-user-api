package coffee.website.register.service;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.player.service.CustomerService;
import coffee.website.register.vo.res.GetOtpRes;
import coffee.website.register.vo.res.checkOtpRes;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.website.register.model.RegisterOtp;
import coffee.website.register.repository.RegisterOtpJpa;
import coffee.website.register.vo.req.GetOtpReq;

import javax.xml.bind.JAXBContext;
import java.util.Date;
import java.util.Random;

@Service
public class RegisterOtpService {

    @Autowired
    private RegisterOtpJpa registerOtpJpa;

    @Autowired
    private SmsService smsService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private Boolean validateUsername(String username, String phoneNumber, Customer customer) {
        long timeCheck = new Date().getTime();
        if (customer.getRegisterStatus()==2)
            return true;
        if (customer.getMobilePhone().equals(phoneNumber) && customer.getUsername().equals(username))
            return false;
        if (timeCheck - customer.getCreatedDate().getTime() <= (5 * 60 * 1000))
            return true;
        return true;
    }

    public GetOtpRes getOtp(GetOtpReq req) throws Exception {
        GetOtpRes returnRef = new GetOtpRes();

//        Validate Number Range
        if (req.getPhoneNumber().length() != 10) {
            returnRef.setValidateNumber(true);
            return returnRef;
        }
//        Validate Duplicate
        if (customerRepository.existsByMobilePhoneAndRegisterStatus(req.getPhoneNumber(), (long) 2)) {
            returnRef.setDuplicateNumber(true);
            return returnRef;
        }

        Customer customer = customerRepository.findByUsername(req.getUsername());
        if (customer != null && validateUsername(req.getUsername(), req.getPhoneNumber(),customer)) {
            throw new Exception("ผู้ใช้งานนี้ถูกใช้ไปแล้ว");
        }


        String otp = generateOTP(5);
        String ref = generateRefOTP(5);

        RegisterOtp register = new RegisterOtp();
        if (registerOtpJpa.existsByMobilePhone(req.getPhoneNumber())) {
            register = registerOtpJpa.findByMobilePhone(req.getPhoneNumber()).get();
//            check for prevent spam
            String preventSpam = preventSpam(register);
            if (preventSpam != null) {
                returnRef.setNextRequestAble(preventSpam);
                return returnRef;
            }

            register.setCountOtp(register.getCountOtp() + 1);
        } else {
            register.setCountOtp((long) 0);
        }
        register.setCreatedDate(new Date());
        register.setMobilePhone(req.getPhoneNumber());
        register.setOtp(otp);

        returnRef.setRefCode(ref);

        registerOtpJpa.save(register);
        if (!customerRepository.existsByMobilePhone(req.getPhoneNumber())) {
            customerService.lockUser(req.getUsername(), req.getPhoneNumber());
        }
        smsService.sendSms(createMassage(otp, ref), req.getPhoneNumber());
        return returnRef;
    }

    public checkOtpRes checkOtp(GetOtpReq req) {
        checkOtpRes dataRes = new checkOtpRes();
        dataRes.setStatus(false);
        dataRes.setExpire(false);
        Long timeCheck = new Date().getTime();
        if (registerOtpJpa.existsByMobilePhoneAndOtp(req.getPhoneNumber(), req.getOtp())) {
            RegisterOtp row = registerOtpJpa.findByMobilePhoneAndOtp(req.getPhoneNumber(), req.getOtp()).get();
//            3 minuit
            if (timeCheck - row.getCreatedDate().getTime() <= 3 * 60 * 1000) {
                dataRes.setStatus(true);
            } else {
                dataRes.setExpire(true);
            }
        }
        return dataRes;
    }


    private String generateOTP(int len) {

        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return new String(otp);
    }

    private String generateRefOTP(int len) {

        // Using numeric values
        String numbers = "abcdefghijklmnopqrstwxyz";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return new String(otp);
    }

    private String preventSpam(RegisterOtp registerOtp) {
        Long timeCheck = new Date().getTime();
        Long cooldown = timeCheck - registerOtp.getCreatedDate().getTime();
        if (registerOtp.getCountOtp() < 2) {
            if (cooldown <= 1 * 15 * 1000) {
                return String.valueOf((((1 * 15 * 1000) - cooldown) / 1000));
            }
        } else if (registerOtp.getCountOtp() < 5) {
            if (cooldown <= 1 * 30 * 1000) {
                return String.valueOf((((1 * 30 * 1000) - cooldown) / 1000));
            }
        } else {
            if (cooldown <= 3 * 60 * 1000) {
                return String.valueOf((((3 * 60 * 1000) - cooldown) / 1000));
            }
        }
        return null;
    }

    private String createMassage(String otp, String ref) {
        StringBuilder massage = new StringBuilder();
        massage.append("รหัสยืนยันของคุณ คือ : ")
                .append(otp)
                .append('\n')
                .append("(REF : ")
                .append(ref)
                .append(")")
                .append("\nอายุการใช้งาน 3 นาที");
        return massage.toString();
    }

    public GetOtpRes getOtpResetPassword(GetOtpReq req) {
        GetOtpRes returnRef = new GetOtpRes();

        if (req.getPhoneNumber().length() != 10) {
            returnRef.setValidateNumber(true);
            return returnRef;
        }

        String otp = generateOTP(5);
        String ref = generateRefOTP(5);

        RegisterOtp register = new RegisterOtp();
        if (registerOtpJpa.existsByMobilePhone(req.getPhoneNumber())) {
            register = registerOtpJpa.findByMobilePhone(req.getPhoneNumber()).get();
//            check for prevent spam
            String preventSpam = preventSpam(register);
            if (preventSpam != null) {
                returnRef.setNextRequestAble(preventSpam);
                return returnRef;
            }

            register.setCountOtp(register.getCountOtp() + 1);
        } else {
            register.setCountOtp((long) 0);
        }
        register.setCreatedDate(new Date());
        register.setMobilePhone(req.getPhoneNumber());
        register.setOtp(otp);

        returnRef.setRefCode(ref);

        registerOtpJpa.save(register);

        smsService.sendSms(createMassage(otp, ref), req.getPhoneNumber());
        return returnRef;
    }

}
