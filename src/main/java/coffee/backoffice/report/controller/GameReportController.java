package coffee.backoffice.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.report.service.GameReportService;
import coffee.backoffice.report.vo.res.GameReportRes;
import framework.constant.ProjectConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/game-report/")
@Slf4j
public class GameReportController {

	@Autowired
	private GameReportService gameReportService;

	@PostMapping("get-report-daily")
	public ResponseData<GameReportRes> getGameReportDaily(@RequestBody DatatableRequest req) {
		ResponseData<GameReportRes> response = new ResponseData<GameReportRes>();
		try {
			response.setData(gameReportService.getGameReportDaily(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getGameReportDaily()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getGameReportDaily() :" + e);
		}
		return response;
	}
	
	@PostMapping("get-report-monthly")
	public ResponseData<GameReportRes> getGameReportMonthly(@RequestBody DatatableRequest req) {
		ResponseData<GameReportRes> response = new ResponseData<GameReportRes>();
		try {
			response.setData(gameReportService.getGameReportMonthly(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getGameReportMonthly()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getGameReportMonthly() :" + e);
		}
		return response;
	}
	
	@PostMapping("get-report-game")
	public ResponseData<GameReportRes> getGameReportGame(@RequestBody DatatableRequest req) {
		ResponseData<GameReportRes> response = new ResponseData<GameReportRes>();
		try {
			response.setData(gameReportService.getGameReportGame(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getGameReportGame()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getGameReportGame() :" + e);
		}
		return response;
	}
}
