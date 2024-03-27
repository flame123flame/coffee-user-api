package coffee.backoffice.promotion.controller;

import java.util.List;

import coffee.backoffice.promotion.vo.res.PromotionDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import coffee.backoffice.promotion.service.PromotionService;
import coffee.backoffice.promotion.vo.model.PromotionDetail;
import coffee.backoffice.promotion.vo.req.PromotionReq;
import coffee.backoffice.promotion.vo.res.PromotionPlayerRes;
import coffee.backoffice.promotion.vo.res.PromotionProfileRes;
import coffee.backoffice.promotion.vo.res.PromotionRes;
import framework.constant.ResponseConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/promotion/")
@Slf4j
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @GetMapping("get-all")
    @ResponseBody
    public ResponseData<List<PromotionRes>> getAll() {
        ResponseData<List<PromotionRes>> response = new ResponseData<>();
        try {
            response.setData(promotionService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => PromotionController.getAll() : " + e);
        }
        return response;
    }

    @GetMapping("clone/{promoCode}")
    @ResponseBody
    public ResponseData<?> clone(@PathVariable("promoCode") String promoCode) {
        ResponseData<?> response = new ResponseData<>();
        try {
            promotionService.clone(promoCode);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.getAllToPlayer()");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => PromotionController.getAllToPlayer() : " + e);
        }
        return response;
    }

    @PostMapping("new-promotion")
    @ResponseBody
    public ResponseData<?> insert(@RequestBody PromotionReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            promotionService.create(form, false);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionController.insert() :" + e);
        }
        return response;
    }

    @PostMapping("clone-promotion")
    @ResponseBody
    public ResponseData<?> clone(@RequestBody PromotionReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            promotionService.create(form,true);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionController.insert() :" + e);
        }
        return response;
    }

    @PutMapping("edit-promotion/{promoCode}")
    @ResponseBody
    public ResponseData<?> edit(@PathVariable("promoCode") String promoCode,@RequestBody PromotionReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            promotionService.edit(promoCode,form);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionController.insert() :" + e);
        }
        return response;
    }

    @GetMapping("get-by-promo-code")
    @ResponseBody
    public ResponseData<PromotionDetail> getByPromoCode(@RequestParam String promoCode) {
        ResponseData<PromotionDetail> response = new ResponseData<PromotionDetail>();
        try {
            response.setData(promotionService.findPromotionDetail(promoCode));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API PromotionController.insert() :" + e);
        }
        return response;
    }

    @GetMapping("get-promotion-by-groupcode/{groupCode}")
    @ResponseBody
    public ResponseData<List<PromotionRes>> findPromotionByGroupCode(@PathVariable("groupCode") String code) {
        ResponseData<List<PromotionRes>> response = new ResponseData<>();
        try {
            response.setData(promotionService.findPromotionByGroupCode(code));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => PromotionController.getAll() : " + e);
        }
        return response;
    }

    @PostMapping("new-promotion-posting")
    @ResponseBody
    public ResponseData<?> insertPromotionPosting(@RequestBody PromotionReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            promotionService.createPromotionPosting(form);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => new-promotion-posting");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API => new-promotion-posting : " + e);
        }
        return response;
    }

    @GetMapping("get-promotion-by-username/{username}")
    @ResponseBody
    public ResponseData<PromotionProfileRes> getPromotionByUsername(@PathVariable("username") String username) {
        ResponseData<PromotionProfileRes> response = new ResponseData<PromotionProfileRes>();
        try {
            response.setData(promotionService.findPromotionByUsername(username));
            response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.getPromotionByUsername()");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => PromotionController.getPromotionByUsername() : " + e);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<PromotionProfileRes> deleteOne(@PathVariable("id") Long id) {
        ResponseData<PromotionProfileRes> response = new ResponseData<PromotionProfileRes>();
        try {
            promotionService.deletePromotion(id);
            response.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.deleteOne()");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => PromotionController.deleteOne() : " + e);
        }
        return response;
    }

    @PutMapping("toggle-status/{id}")
    @ResponseBody
    public ResponseData<PromotionProfileRes> toggleViewStatus(@PathVariable("id") Long id) {
        ResponseData<PromotionProfileRes> response = new ResponseData<PromotionProfileRes>();
        try {
            promotionService.toggleViewStatus(id);
            response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => PromotionController.toggleViewStatus()");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => PromotionController.toggleViewStatus() : " + e);
        }
        return response;
    }

    //  GET PAGINATE
    @PostMapping("/paginate")
    @ResponseBody
    public ResponseData<DataTableResponse<PromotionDatatableRes>> getDataPaginate(@RequestBody DatatableRequest param) {
        ResponseData<DataTableResponse<PromotionDatatableRes>> response = new ResponseData<DataTableResponse<PromotionDatatableRes>>();
        try {
            response.setData(promotionService.getPaginate(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CashBackConditionController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CashBackConditionController.getDataPaginate() :" + e);
        }
        return response;
    }
}
