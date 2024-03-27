package coffee.website.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.website.frontend.service.LandingWebPlayerService;
import coffee.website.frontend.vo.res.LandingWebPlayer;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/landing/")
@Slf4j
public class LandingWebPlayerController {
	
	@Autowired
	private LandingWebPlayerService landingWebPlayerService;
	
	@GetMapping("get-landing/{configPath}")
    @ResponseBody
    public ResponseData<LandingWebPlayer> getLanding(@PathVariable("configPath") String configPath) {
        ResponseData<LandingWebPlayer> responseData = new ResponseData<LandingWebPlayer>();
        try {
            responseData.setData(landingWebPlayerService.getLanding(configPath));
            responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            responseData.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API LandingWebPlayerController => getLanding");
        } catch (Exception e) {
            log.error("Error Calling API LandingWebPlayerController =>  getLanding ", e);
            responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
            responseData.setStatus(RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }
}
