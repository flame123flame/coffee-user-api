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

import coffee.backoffice.masterdata.model.FwLovDtl;
import coffee.backoffice.masterdata.model.redis.LovDetail;
import coffee.backoffice.masterdata.service.LovService;
import coffee.backoffice.masterdata.vo.req.LovHdrReq;
import coffee.backoffice.masterdata.vo.res.LovHdrRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/lov/")
@Slf4j
public class LovController {
	
	@Autowired
	private LovService lovService;

	/*
	 * from redis
	 */
	@GetMapping("get/{lov-code}")
	public ResponseData<List<LovDetail>> getLov(@PathVariable("lov-code") String lovCode) {
		ResponseData<List<LovDetail>> responseData = new ResponseData<List<LovDetail>>();
		try {
			responseData.setData(lovService.getLov(lovCode));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("LovController: getLov ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	
	@GetMapping("get-list")
	public ResponseData<List<LovHdrRes>> getListLov() {
		ResponseData<List<LovHdrRes>> responseData = new ResponseData<List<LovHdrRes>>();
		try {
			responseData.setData(lovService.getListLov());
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("LovController: getListLov ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("save")
	public ResponseData<?> saveLov(@RequestBody LovHdrReq req) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			lovService.saveLov(req);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("LovController: saveLov ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PutMapping("edit")
	public ResponseData<?> editLov(@RequestBody LovHdrReq req) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			lovService.editLov(req);
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("LovController: editLov ", e);
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("get-by-id/{id}")
	public ResponseData<LovHdrRes> getLovById(@PathVariable("id") Long id) {
		ResponseData<LovHdrRes> responseData = new ResponseData<LovHdrRes>();
		try {

			responseData.setData(lovService.getLovById(id));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("LovController: getLovById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("get-by-key")
	public ResponseData<List<FwLovDtl>> save(@RequestBody LovHdrReq formClient) {
		ResponseData<List<FwLovDtl>> responseData = new ResponseData<List<FwLovDtl>>();
		try {
			responseData.setData(lovService.getLovByKey(formClient));
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("AssetsConvertedController: save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@DeleteMapping("delete-lov-hdr/{lovKey}")
	public ResponseData<?> deleteLovHdr(@PathVariable("lovKey") String lovKey) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			lovService.deleteLovHdr(lovKey);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("LovController: deleteLovHdr ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@DeleteMapping("delete-lov-dtl/{id}")
	public ResponseData<?> deleteLovDtl(@PathVariable("id") Long id) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			lovService.deleteLovDtl(id);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			log.error("LovController: deleteLovDtl ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
