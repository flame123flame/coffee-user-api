package coffee.website.rebate.controller;

import coffee.backoffice.rebate.vo.res.RebateHistoryRes;
import coffee.website.rebate.service.RebateHistoryWebPlayerService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/rebate-history/")
@Slf4j
public class RebateHistoryWebPlayerController {
    @Autowired
    private RebateHistoryWebPlayerService rebateHistoryWebPlayerService;

    @GetMapping("get-history")
    public ResponseData<List<RebateHistoryRes>> getAll() {
        ResponseData<List<RebateHistoryRes>> response = new ResponseData<List<RebateHistoryRes>>();
        try {
            response.setData(rebateHistoryWebPlayerService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => RebateController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API RebateController.getAll() :" + e);
        }
        return response;
    }

}
