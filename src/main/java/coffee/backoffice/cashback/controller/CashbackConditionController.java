package coffee.backoffice.cashback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.cashback.service.CashbackConditionService;
import coffee.backoffice.cashback.vo.req.CashbackConditionReq;
import coffee.backoffice.cashback.vo.res.CashbackConditionRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/cashback-condition")
@Slf4j
public class CashbackConditionController {

	@Autowired
	private CashbackConditionService cashBackConditionService;

	@GetMapping("get-by-id/{id}")
	@ResponseBody
	public ResponseData<CashbackConditionRes> getId(@PathVariable("id") Long id) {
		ResponseData<CashbackConditionRes> response = new ResponseData<CashbackConditionRes>();
		try {
			response.setData(cashBackConditionService.getOne(id));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashBackConditionController.getId()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashBackConditionController.getId() : " + e);
		}
		return response;
	}
	
	@GetMapping
    @ResponseBody
    public ResponseData<List<CashbackConditionRes>> getAll() {
    ResponseData<List<CashbackConditionRes>> response = new ResponseData<List<CashbackConditionRes>>();
    try {
        response.setData(cashBackConditionService.getAll());
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
        log.info("Success Calling API => CashBackConditionController.getAll()");
    } catch (Exception e) {
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
        log.error("Error Calling API CashBackConditionController.getAll() :" + e);
    }
    return response;
}

    //  GET PAGINATE
    @PostMapping("/paginate")
    @ResponseBody
    public ResponseData<DataTableResponse<CashbackConditionRes>> getDataPaginate(@RequestBody DatatableRequest param) {
    ResponseData<DataTableResponse<CashbackConditionRes>> response = new ResponseData<DataTableResponse<CashbackConditionRes>>();
    try {
        response.setData(cashBackConditionService.getPaginateModel(param));
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

    //  INSERT
    @PostMapping
    @ResponseBody
    public ResponseData<CashbackConditionRes> insert(@RequestBody CashbackConditionReq param) {
    ResponseData<CashbackConditionRes> response = new ResponseData<CashbackConditionRes>();
    try {
        response.setData(cashBackConditionService.insertOne(param));
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
        log.info("Success Calling API => CashBackConditionController.insert()");
    } catch (Exception e) {
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
        log.error("Error Calling API CashBackConditionController.insert() :" + e);
    }
    return response;
}

    //    UPDATE
    @PutMapping("/update-cashback-condition")
    @ResponseBody
    public ResponseData<CashbackConditionRes> update(@RequestBody CashbackConditionReq param) {
    ResponseData<CashbackConditionRes> response = new ResponseData<CashbackConditionRes>();
    try {
        response.setData(cashBackConditionService.updateOne(param));
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
        log.info("Success Calling API => CashBackConditionController.update()");
    } catch (Exception e) {
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
        log.error("Error Calling API CashBackConditionController.update() :" + e);
    }
    return response;
}

    //    DELETE
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<CashbackConditionRes> delete(@PathVariable("id") Long id) {
    ResponseData<CashbackConditionRes> response = new ResponseData<CashbackConditionRes>();
    try {
        response.setData(cashBackConditionService.delete(id));
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
        log.info("Success Calling API => CashBackConditionController.delete()");
    } catch (Exception e) {
        response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
        response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
        log.error("Error Calling API CashBackConditionController.delete() :" + e);
    }
    return response;
}

}
