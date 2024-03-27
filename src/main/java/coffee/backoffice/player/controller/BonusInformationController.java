package coffee.backoffice.player.controller;


import coffee.backoffice.player.model.BonusInformation;
import coffee.backoffice.player.model.DepositInformation;
import coffee.backoffice.player.service.BonusInformationService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/bonus-information")
@Slf4j
public class BonusInformationController {
    @Autowired
    private BonusInformationService bonusInformationService;

    @GetMapping("get-by-username/{username}")
    public ResponseData<BonusInformation> getByUsername(@PathVariable String username) {
        ResponseData<BonusInformation> response = new ResponseData<BonusInformation>();
        try {
            response.setData(bonusInformationService.getByUsername(username));
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
