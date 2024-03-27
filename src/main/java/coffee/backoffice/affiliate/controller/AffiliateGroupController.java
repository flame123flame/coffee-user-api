package coffee.backoffice.affiliate.controller;

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

import coffee.backoffice.affiliate.service.AffiliateGroupService;
import coffee.backoffice.affiliate.vo.req.AffiliateGroupReq;
import coffee.backoffice.affiliate.vo.res.AffiliateGroupRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/affiliate-group/")
@Slf4j
public class AffiliateGroupController {
	
	@Autowired
	private AffiliateGroupService groupService;
	
	@PostMapping("save-affiliate-group")
	public ResponseData<?> saveAffiliateGroup(@RequestBody AffiliateGroupReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			groupService.saveAffiliateGroup(form);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AffiliateGroupController => saveAffiliateGroup");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AffiliateGroupController => saveAffiliateGroup :" + e);
		}
		return responseData;
	}
	@PutMapping("edit-affiliate-group")
	public ResponseData<?> editAffiliateGroup(@RequestBody AffiliateGroupReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			groupService.editAffiliateGroup(form);
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AffiliateGroupController => editAffiliateGroup");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AffiliateGroupController => editAffiliateGroup :" + e);
		}
		return responseData;
	}
	
	@GetMapping("get-affiliate-group-all")
	public ResponseData<List<AffiliateGroupRes>> getAffiliateGroupList() {
		ResponseData<List<AffiliateGroupRes>> responseData = new ResponseData<List<AffiliateGroupRes>>();
		try {
			responseData.setData(groupService.getAffiliateGroupList());
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AffiliateGroupController => getCustomerAll");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AffiliateGroupController => getCustomerAll :" + e);
		}
		return responseData;
	}
	
	@GetMapping("get-affiliate-group-by-code/{affiliateGroupCode}")
	public ResponseData<AffiliateGroupRes> getAffiliateGroupByCode(@PathVariable("affiliateGroupCode") String code) {
		ResponseData<AffiliateGroupRes> responseData = new ResponseData<AffiliateGroupRes>();
		try {
			responseData.setData(groupService.getAffiliateGroupByCode(code));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AffiliateGroupController => getAffiliateGroupByCode");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AffiliateGroupController => getAffiliateGroupByCode :" + e);
		}
		return responseData;
	}

	@DeleteMapping("delete-affiliate-group/{affiliateGroupCode}")
	public ResponseData<?> deleteAffiliateGroup(@PathVariable("affiliateGroupCode") String code) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			groupService.updateEnableAffiliateGroupByCode(code);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AffiliateGroupController => updateAffiliateGroup");
		} catch (Exception e) {
			log.error("Error Calling API AffiliateGroupController => updateAffiliateGroup :" + e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	

}
