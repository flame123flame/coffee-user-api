package coffee.backoffice.player.controller;


import coffee.backoffice.player.model.DepositInformation;
import coffee.backoffice.player.model.WithdrawalInformation;
import coffee.backoffice.player.service.WithdrawalInformationService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/withdrawal-information")
@Slf4j
public class WithdrawalInformationController {
    @Autowired
    private WithdrawalInformationService withdrawalInformationService;

    @GetMapping("get-by-username/{username}")
    public ResponseData<WithdrawalInformation> getByUsername(@PathVariable String username) {
        ResponseData<WithdrawalInformation> response = new ResponseData<WithdrawalInformation>();
        try {
            response.setData(withdrawalInformationService.getByUsername(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WithdrawalInformationController => getByUsername");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WithdrawalInformationController => getByUsername :" + e);
        }
        return response;
    }
}
