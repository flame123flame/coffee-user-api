package coffee.website.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.website.frontend.service.DepositSettingWebPlayerService;
import coffee.website.frontend.vo.res.DepositSettingWebPlayerRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix = "controller-config.enable", name = "player", havingValue = "true")
@RequestMapping("api/web-player/deposit-setting/")
@Slf4j
public class DepositSettingWebPlayerController {
	
	@Autowired
	private DepositSettingWebPlayerService depositSettingWebPlayerService;
	@GetMapping("deposit-system/{username}")
	public ResponseData<DepositSettingWebPlayerRes> transferStatus(@PathVariable("username") String username) {
		ResponseData<DepositSettingWebPlayerRes> responseData = new ResponseData<DepositSettingWebPlayerRes>();
		try {
			responseData.setData(depositSettingWebPlayerService.transferStatus(username));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API DepositSettingWebPlayerController => transferStatus");
		} catch (Exception e) {
			log.error("Error Calling API DepositSettingWebPlayerController =>  transferStatus ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
