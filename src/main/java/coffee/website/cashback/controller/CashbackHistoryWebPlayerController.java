package coffee.website.cashback.controller;


import coffee.backoffice.cashback.vo.res.CashbackHistoryUserRes;
import coffee.website.cashback.service.CashbackHistoryWebPlayerService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/cashback-history/")
@Slf4j
public class CashbackHistoryWebPlayerController {

    @Autowired
    private CashbackHistoryWebPlayerService cashbackHistoryWebPlayerService;

    @GetMapping("get-history-by-user/{username}")
    public ResponseData<List<CashbackHistoryUserRes>> getCashbackHistoryByUser(@PathVariable("username") String username) {
        ResponseData<List<CashbackHistoryUserRes>> response = new ResponseData<List<CashbackHistoryUserRes>>();
        try {
            response.setData(cashbackHistoryWebPlayerService.getCashbackHistoryByUser(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CashbackHistoryService.getCashbackHistoryByUser()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CashbackHistoryService.getCashbackHistoryByUser() :" + e);
        }
        return response;
    }

}
