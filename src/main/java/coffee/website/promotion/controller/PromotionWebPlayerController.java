package coffee.website.promotion.controller;

import coffee.backoffice.promotion.vo.res.PromotionPlayerRes;
import coffee.website.promotion.service.PromotionWebPlayerService;
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
@RequestMapping("api/web-player/promotion/")
@Slf4j
public class PromotionWebPlayerController {
    @Autowired
    PromotionWebPlayerService promotionWebPlayerService;

    @GetMapping("get-promotion-player")
    @ResponseBody
    public ResponseData<List<PromotionPlayerRes>> getAllToPlayer() {
        ResponseData<List<PromotionPlayerRes>> response = new ResponseData<>();
        try {
            response.setData(promotionWebPlayerService.getAllToPlayer());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.getAllToPlayer()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => PromotionController.getAllToPlayer() : " + e);
        }
        return response;
    }
}
