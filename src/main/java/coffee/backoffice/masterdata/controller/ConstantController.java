package coffee.backoffice.masterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.masterdata.model.redis.ConstantRedisModel;
import coffee.backoffice.masterdata.service.ConstantService;
import coffee.backoffice.masterdata.vo.req.ConstantReq;
import coffee.backoffice.masterdata.vo.res.ConstantRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/constant/")
@Slf4j
public class ConstantController {

	@Autowired
	private ConstantService constantService;

	/*
	 * from redis
	 */
	@GetMapping("get/{constant-code}")
	public ResponseData<ConstantRedisModel> getConstant(@PathVariable("constant-code") String constantCode) {
		ResponseData<ConstantRedisModel> responseData = new ResponseData<ConstantRedisModel>();
		try {
			responseData.setData(constantService.getConstant(constantCode));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("ConstantController: getConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("get-list")
	public ResponseData<List<ConstantRes>> getListConstant(@RequestBody ConstantReq form) {
		ResponseData<List<ConstantRes>> responseData = new ResponseData<List<ConstantRes>>();
		try {
			responseData.setData(constantService.getConstantList(form));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("ConstantController: getListConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("save")
	public ResponseData<String> saveConstant(@RequestBody ConstantReq form) {
		ResponseData<String> responseData = new ResponseData<>();
		try {
			responseData.setData(constantService.saveConstant(form));
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("ConstantController: saveConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PutMapping("edit")
	public ResponseData<?> editConstant(@RequestBody ConstantReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			constantService.editConstant(form);
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("ConstantController: editConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("get-by-id/{id}")
	public ResponseData<ConstantRes> getConstantById(@PathVariable("id") Long id) {
		ResponseData<ConstantRes> responseData = new ResponseData<ConstantRes>();
		try {
			responseData.setData(constantService.getConstantById(id));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("ConstantService: getConstantById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@DeleteMapping("delete-constant/{id}")
	public ResponseData<?> deleteConstant(@PathVariable("id") Long id) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			constantService.deleteConstant(id);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("ConstantController: deleteConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
