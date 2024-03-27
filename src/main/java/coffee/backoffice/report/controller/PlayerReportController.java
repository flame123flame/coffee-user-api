package coffee.backoffice.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.report.service.PlayerReportService;
import coffee.backoffice.report.vo.res.PlayerReportRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/player-report/")
@Slf4j
public class PlayerReportController {

	@Autowired
	private PlayerReportService playerReportService;

	@PostMapping("player-paginate")
	public ResponseData<PlayerReportRes> getPlayerReport(@RequestBody DatatableRequest req) {
		ResponseData<PlayerReportRes> response = new ResponseData<PlayerReportRes>();
		try {
			response.setData(playerReportService.getPlayerReport(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API PlayerReportController.getPlayerReport");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API PlayerReportController.getPlayerReport :" + e);
		}
		return response;
	}
}
