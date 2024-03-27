package coffee.backoffice.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.frontend.model.DepositSetting;
import coffee.backoffice.frontend.service.DepositSettingService;
import coffee.backoffice.frontend.vo.req.DepositSettingReq;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/deposit-setting/")
@Slf4j
public class DepositSettingController {
 
	@Autowired
	private DepositSettingService depositSettingService;
	
	@GetMapping("get-deposit-setting")
	public ResponseData<DepositSetting> getdepositSetting() {
		ResponseData<DepositSetting> responseData = new ResponseData<>();
		try {
			responseData.setData(depositSettingService.getDepositSetting());
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API depositSettingController => getdepositSetting");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API depositSettingController => getdepositSetting :" + e);
		}
		return responseData;
	}
	
	@GetMapping("get-deposit-setting-by-id/{id}")
	public ResponseData<DepositSetting> getdepositSettingById(@PathVariable Long id) {
		ResponseData<DepositSetting> responseData = new ResponseData<>();
		try {
			responseData.setData(depositSettingService.getDepositSettingById(id));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API depositSettingController => getdepositSettingById ");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API depositSettingController => getdepositSettingById :" + e);
		}
		return responseData;
	}
	
	@PostMapping("save-deposit-setting")
	public ResponseData<?> savedepositSetting(@RequestBody DepositSettingReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			depositSettingService.saveDepositSetting(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API DepositSettingController => saveDepositSetting");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API DepositSettingController => saveDepositSetting :" + e);
		}
		return response;
	}
	
	
}
