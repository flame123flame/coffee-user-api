package coffee.website.history.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.website.history.service.HistoryService;
import coffee.website.history.vo.req.HistoryRequest;
import coffee.website.history.vo.res.HistoryResponse;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/player-history/")
@Slf4j

public class HistoryController {
	
	@Autowired
	private HistoryService historyService;
	
	@PostMapping("get-history")
	public ResponseData<List<HistoryResponse>> getHistoryPlayer(@RequestBody HistoryRequest req) {
		ResponseData<List<HistoryResponse>> response = new ResponseData<List<HistoryResponse>>();
		try {
			response.setData(historyService.getHistoryPlayer(req));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => HistoryController.getHistoryPlayer()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API HistoryController.getHistoryPlayer() :" + e);
		}
		return response;
	}

}
