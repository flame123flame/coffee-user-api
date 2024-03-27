package coffee.backoffice.promotion.controller;

import coffee.backoffice.promotion.model.PromotionRequest;
import coffee.backoffice.promotion.service.PromotionRequestService;
import coffee.backoffice.promotion.vo.req.PromotionRequestReq;
import coffee.backoffice.promotion.vo.res.PromotionRequestRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/promotion-request")
@Slf4j
public class PromotionRequestController {

    @Autowired
    private PromotionRequestService promotionRequestService;

    //    GET ALL
    @GetMapping
    @ResponseBody
    public ResponseData<List<PromotionRequestRes>> getAll() {
        ResponseData<List<PromotionRequestRes>> response = new ResponseData<List<PromotionRequestRes>>();
        try {
            response.setData(promotionRequestService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionRequestController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionRequestController.getAll() :" + e);
        }
        return response;
    }

    @PutMapping("/change-status")
    @ResponseBody
    public ResponseData<?> changeStatus(@RequestBody PromotionRequestReq param) {
        ResponseData<?> response = new ResponseData<>();
        try {
        	promotionRequestService.changeStatusPromotion(param);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionRequestController.changeStatus()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionRequestController.changeStatus() :" + e);
        }
        return response;
    }

}
