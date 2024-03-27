package coffee.website.wallet.controller;

import coffee.backoffice.finance.vo.req.WithdrawReq;
import coffee.website.wallet.service.WithdrawPlayerService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/withdraw/")
@Slf4j
public class WithdrawPlayerController {

    @Autowired
    private WithdrawPlayerService withdrawPlayerService;

    @PostMapping("player-request")
    public ResponseData<?> playerRequest(@RequestBody WithdrawReq req) {
        ResponseData<?> response = new ResponseData<>();
        try {
            withdrawPlayerService.newPlayerRequest(req);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WithdrawController => playerRequest");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WithdrawController => playerRequest :");
            e.printStackTrace();
        }
        return response;
    }


}
