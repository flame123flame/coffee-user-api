package coffee.website.register.controller;

import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.vo.req.CustomerReq;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.website.register.vo.res.checkOtpRes;
import framework.constant.ResponseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import coffee.website.register.service.RegisterOtpService;
import coffee.website.register.service.RegisterService;
import coffee.website.register.vo.req.GetOtpReq;
import coffee.website.register.vo.res.GetOtpRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/register/")
@Slf4j
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private RegisterOtpService registerOtpService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("validate-username/{username}")
    public ResponseData<Boolean> validateUserName(@PathVariable("username") String username) {
        ResponseData<Boolean> responseData = new ResponseData<Boolean>();
        try {
            responseData.setData(registerService.validateUserName(username));
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            log.error("ConstantController: getListConstant ", e);
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @GetMapping("register-fail/{username}")
    public ResponseData<Boolean> registerFail(@PathVariable("username") String username) {
        ResponseData<Boolean> responseData = new ResponseData<Boolean>();
        try {
            registerService.registerFail(username);
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.DELETE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            log.error("ConstantController: getListConstant ", e);
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.DELETE.FAILED);
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @PostMapping("check-otp")
    @ResponseBody
    public ResponseData<checkOtpRes> checkOtp(@RequestBody GetOtpReq req) {
        ResponseData<checkOtpRes> responseData = new ResponseData<checkOtpRes>();
        try {
            responseData.setData(registerOtpService.checkOtp(req));
            responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            log.error("RegisterController::checkOtp ", e);
            responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @PostMapping("get-otp")
    @ResponseBody
    public ResponseData<GetOtpRes> getOtp(@RequestBody GetOtpReq req) {
        ResponseData<GetOtpRes> responseData = new ResponseData<GetOtpRes>();
        try {
            responseData.setData(registerOtpService.getOtp(req));
            responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
            if (e.getMessage().equals("ผู้ใช้งานนี้ถูกใช้ไปแล้ว"))
                responseData.setMessage(e.getMessage());
            log.error("RegisterController::checkOtp ", e);
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }

    @PostMapping("new")
    @ResponseBody
    public ResponseData<CustomerRes> getOtp(@RequestBody CustomerReq req) {
        ResponseData<CustomerRes> responseData = new ResponseData<CustomerRes>();
        try {
            responseData.setData(customerService.saveCustomerFromFront(req));
            responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
        } catch (Exception e) {
            log.error("RegisterController::checkOtp ", e);
            responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }
}
