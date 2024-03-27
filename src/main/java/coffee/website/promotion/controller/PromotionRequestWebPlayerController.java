package coffee.website.promotion.controller;

import coffee.backoffice.promotion.model.PromotionRequest;
import coffee.backoffice.promotion.vo.req.PromotionRequestReq;
import coffee.website.promotion.service.PromotionRequestWebPlayerService;
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
@RequestMapping("api/web-player/promotion-request")
@Slf4j
public class PromotionRequestWebPlayerController {

    @Autowired
    private PromotionRequestWebPlayerService promotionRequestWebPlayerService;

    @GetMapping("/promotion-history/{username}")
    @ResponseBody
    public ResponseData<List<PromotionRequest>> promotionHistory(@PathVariable("username") String username){
        ResponseData<List<PromotionRequest>> response = new ResponseData<List<PromotionRequest>>();
        try {
            response.setData(promotionRequestWebPlayerService.promotionPlayerHistory(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionRequestController.promotionHistory()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionRequestController.promotionHistory() :" + e);
        }
        return response;
    }

    @PostMapping("/recive-promotion")
    @ResponseBody
    public ResponseData<?> promotionRequest(@RequestBody PromotionRequestReq req) {
        ResponseData<?> response = new ResponseData<>();
        try {
            promotionRequestWebPlayerService.recivePromotion(req);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionRequestController.promotionRequest()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionRequestController.promotionRequest() :" + e);
        }
        return response;
    }


}
