package coffee.backoffice.frontend.controller;

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

import coffee.backoffice.frontend.model.Landing;
import coffee.backoffice.frontend.service.LandingService;
import coffee.backoffice.frontend.vo.req.LandingReq;
import coffee.backoffice.frontend.vo.res.LandingRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix = "controller-config.enable", name = "bo", havingValue = "true")
@RequestMapping("api/landing/")
@Slf4j
public class LandingController {

	@Autowired
	private LandingService landingService;

	@GetMapping("get-landing")
	public ResponseData<List<Landing>> getLandingSetting() {
		ResponseData<List<Landing>> response = new ResponseData<List<Landing>>();
		try {
			response.setData(landingService.getLanding());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API LandingController => getLandingSetting");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API LandingController => getLandingSetting :" + e);
		}
		return response;
	}

	@PostMapping("save-landing")
	public ResponseData<?> saveLandingSetting(@RequestBody LandingReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			landingService.saveLanding(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API LandingController => saveLandingSetting");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API LandingController => saveLandingSetting :" + e);
		}
		return response;
	}

	@PutMapping("edit-landing")
	public ResponseData<?> editLandingSetting(@RequestBody LandingReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			landingService.editLanding(req);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API LandingController => editLandingSetting");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API LandingController => editLandingSetting :" + e);
		}
		return response;
	}

	@GetMapping("get-landing-by-id/{id}")
	public ResponseData<LandingRes> getLandingSettingById(@PathVariable("id") Long id) {
		ResponseData<LandingRes> response = new ResponseData<LandingRes>();
		try {
			response.setData(landingService.getLandingById(id));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API LandingController => getLandingSettingById");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API LandingController => getLandingSettingById :" + e);
		}
		return response;
	}

	@DeleteMapping("delete-landing/{id}")
	public ResponseData<?> deleteLandingSetting(@PathVariable("id") Long id) {
		ResponseData<?> response = new ResponseData<>();
		try {
			landingService.deleteLanding(id);
			response.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API LandingController => deleteLandingSetting");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API LandingController => deleteLandingSetting :" + e);
		}
		return response;
	}
}
