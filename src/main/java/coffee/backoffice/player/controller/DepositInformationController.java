package coffee.backoffice.player.controller;

import coffee.backoffice.player.model.DepositInformation;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.service.DepositInformationService;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.website.register.service.RegisterOtpService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/deposit-information")
@Slf4j
public class DepositInformationController {
    @Autowired
    private DepositInformationService depositInformationService;

    @GetMapping("get-by-username/{username}")
    public ResponseData<DepositInformation> getByUsername(@PathVariable String username) {
        ResponseData<DepositInformation> response = new ResponseData<DepositInformation>();
        try {
            response.setData(depositInformationService.getByUsername(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API DepositInformationController => getByUsername");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API DepositInformationController => getByUsername :" + e);
        }
        return response;
    }
}
