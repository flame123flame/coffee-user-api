package coffee.provider.sbobet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.provider.sbobet.service.SboBetService;
import coffee.provider.sbobet.vo.req.SboBetRequest;
import coffee.provider.sbobet.vo.res.SboBetResponse;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/sbobet")
@Slf4j
public class SboBetController {
	
    @Autowired
    private SboBetService sboBetService;

    @PostMapping("/play")
    public ResponseData<String> play(@RequestBody SboBetRequest req) {
        ResponseData<String> response = new ResponseData<>();
        try {
            response.setData(sboBetService.playGame(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => SboBetController.play()");

        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => SboBetController.play() : " + e);
        }
        return response;
    }
    
    @PostMapping("/register")
    public ResponseData<SboBetResponse> register(@RequestBody SboBetRequest req) {
        ResponseData<SboBetResponse> response = new ResponseData<SboBetResponse>();
        try {
            response.setData(sboBetService.registerPlayer(req.getUsername(),"testAgent2"));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => SboBetController.register()");

        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => SboBetController.register() : " + e);
        }
        return response;
    }
}
