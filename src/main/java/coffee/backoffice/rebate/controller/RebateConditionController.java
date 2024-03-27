package coffee.backoffice.rebate.controller;

import coffee.backoffice.rebate.service.RebateConditionService;
import coffee.backoffice.rebate.vo.req.RebateConditionReq;
import coffee.backoffice.rebate.vo.res.RebateConditionRes;
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
@RequestMapping("api/rebate-condition")
@Slf4j
public class RebateConditionController {

	@Autowired
	private RebateConditionService rebateConditionService;

	// GET ONE
	@GetMapping("get-by-id/{id}")
	@ResponseBody
	public ResponseData<RebateConditionRes> getId(@PathVariable("id") Long id) {
		ResponseData<RebateConditionRes> response = new ResponseData<RebateConditionRes>();
		try {
			response.setData(rebateConditionService.getOne(id));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateConditionController.getId()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => RebateConditionController.getId() : " + e);
		}
		return response;
	}

	// GET ALL
	@GetMapping
	@ResponseBody
	public ResponseData<List<RebateConditionRes>> getAll() {
		ResponseData<List<RebateConditionRes>> response = new ResponseData<List<RebateConditionRes>>();
		try {
			response.setData(rebateConditionService.getAll());
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateConditionController.getAll()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateConditionController.getAll() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/paginate")
	@ResponseBody
	public ResponseData<DataTableResponse<RebateConditionRes>> getDataPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<RebateConditionRes>> response = new ResponseData<DataTableResponse<RebateConditionRes>>();
		try {
			response.setData(rebateConditionService.getPaginateModel(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateConditionController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateConditionController.getDataPaginate() :" + e);
		}
		return response;
	}

	// INSERT
	@PostMapping
	@ResponseBody
	public ResponseData<RebateConditionRes> insert(@RequestBody RebateConditionReq param) {
		ResponseData<RebateConditionRes> response = new ResponseData<RebateConditionRes>();
		try {
			response.setData(rebateConditionService.insertOne(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateConditionController.insert()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateConditionController.insert() :" + e);
		}
		return response;
	}

	// UPDATE
	@PutMapping
	@ResponseBody
	public ResponseData<RebateConditionRes> update(@RequestBody RebateConditionReq param) {
		ResponseData<RebateConditionRes> response = new ResponseData<RebateConditionRes>();
		try {
			response.setData(rebateConditionService.updateOne(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateConditionController.update()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateConditionController.update() :" + e);
		}
		return response;
	}

	// DELETE
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseData<RebateConditionRes> delete(@PathVariable("id") Long id) {
		ResponseData<RebateConditionRes> response = new ResponseData<RebateConditionRes>();
		try {
			response.setData(rebateConditionService.delete(id));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateConditionController.delete()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateConditionController.delete() :" + e);
		}
		return response;
	}

}
