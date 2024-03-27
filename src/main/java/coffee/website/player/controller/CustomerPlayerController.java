package coffee.website.player.controller;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.vo.model.RebateDetail;
import coffee.backoffice.player.vo.model.TurnOverDetail;
import coffee.backoffice.player.vo.req.*;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.website.player.service.CustomerPlayerService;
import coffee.website.register.service.RegisterOtpService;
import coffee.website.register.vo.req.GetOtpReq;
import coffee.website.register.vo.res.GetOtpRes;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/customer/")
@Slf4j
public class CustomerPlayerController {

    @Autowired
    private CustomerPlayerService customerPlayerService;

    @Autowired
    private RegisterOtpService registerOtpService;

    @GetMapping("get-customer-by-id/{username}")
    public ResponseData<CustomerRes> getCustomerByCode(@PathVariable("username") String username) {
        ResponseData<CustomerRes> responseData = new ResponseData<CustomerRes>();
        try {
            responseData.setData(customerPlayerService.getCustomerByUsername(username));
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API CustomerController => getCustomerByCode");
        } catch (Exception e) {
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CustomerController => getCustomerByCode :" + e);
        }
        return responseData;
    }

    @PutMapping("forgot-password/{phoneNumber}/{username}")
    public ResponseData<?> forgotPasswordCustomer(@PathVariable("phoneNumber") String phoneNumber,
                                                  @PathVariable("username") String username) {
        ResponseData<String> response = new ResponseData<String>();
        ResponseData<GetOtpRes> responseData = new ResponseData<GetOtpRes>();
        try {

            if (customerPlayerService.forgotPasswordCustomer(phoneNumber) != null
                    && customerPlayerService.getCustomerByUsername(username) != null) {
                Customer xus = customerPlayerService.forgotPasswordCustomer(phoneNumber);
                GetOtpReq getOtp = new GetOtpReq();
                System.out.println(xus);
                getOtp.setPhoneNumber(xus.getMobilePhone());
                getOtp.setOtp("");

                responseData.setData(registerOtpService.getOtpResetPassword(getOtp));
                responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
                responseData.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
                log.info("Success Calling API forgot-password => resetPasswordCustomer");
            } else if (customerPlayerService.forgotPasswordCustomer(phoneNumber) == null
                    && customerPlayerService.getCustomerByUsername(username) != null) {
                response.setMessage("Phone number is not detected");
                response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
                return response;
            } else if (customerPlayerService.getCustomerByUsername(username) == null
                    && customerPlayerService.forgotPasswordCustomer(phoneNumber) != null) {
                response.setMessage("Username is not detected");
                response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
                return response;
            } else if (customerPlayerService.getCustomerByUsername(username) == null
                    && customerPlayerService.forgotPasswordCustomer(phoneNumber) == null) {
                response.setMessage("Username and Phone number is not detected");
                response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
                return response;
            }
        } catch (Exception e) {
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API => forgot-password :" + e);
        }
        return responseData;
    }

    @PutMapping("reset-password")
    public ResponseData<?> resetPasswordCustomer(@RequestBody CustomerResetPasswordReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            customerPlayerService.resetPasswordCustomer(form);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.EDIT.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API reset-passwword => resetPasswordCustomer");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.EDIT.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API reset-passwword => resetPasswordCustomer :" + e);
        }
        return response;
    }


    @GetMapping("get-bet-daily/{username}")
    public ResponseData<BigDecimal> getBetDaily(@PathVariable String username) {
        ResponseData<BigDecimal> response = new ResponseData<BigDecimal>();
        try {
            response.setData(customerPlayerService.getBetDaily(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API CustomerController => getBetDaily");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CustomerController => getBetDaily :" + e);
        }
        return response;
    }

    @GetMapping("get-rebate-detail/{username}")
    public ResponseData<RebateDetail> getRebateDetail(@PathVariable String username) {
        ResponseData<RebateDetail> response = new ResponseData<RebateDetail>();
        try {
            response.setData(customerPlayerService.getRebate(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API CustomerController => getRebateDetail");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CustomerController => getRebateDetail :" + e);
        }
        return response;
    }

    @GetMapping("get-turnover-detail/{username}")
    public ResponseData<TurnOverDetail> getTurnOverDetail(@PathVariable String username) {
        ResponseData<TurnOverDetail> response = new ResponseData<TurnOverDetail>();
        try {
            response.setData(customerPlayerService.getTurnOver(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API CustomerController => getTurnOverDetail");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CustomerController => getTurnOverDetail :" + e);
        }
        return response;
    }


}
