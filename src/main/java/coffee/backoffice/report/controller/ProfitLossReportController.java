package coffee.backoffice.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.report.service.ProfitLossReportService;
import coffee.backoffice.report.vo.req.ProfitLossReportReq;
import coffee.backoffice.report.vo.res.ProfitLossReportRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/profit-loss-report/")
@Slf4j
public class ProfitLossReportController {
	@Autowired
	private ProfitLossReportService profitLossReportService;
	
	@PostMapping("get-profit-loss")
	public ResponseData<ProfitLossReportRes> getProfitLossReport(@RequestBody ProfitLossReportReq req){
		ResponseData<ProfitLossReportRes> response = new ResponseData<ProfitLossReportRes>();
		try {
			response.setData(profitLossReportService.getProfitLossReport(req.getFirstDayDate(),req.getLastDayDate()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API ProfitLossReportController.getProfitLossReport");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ProfitLossReportController.getProfitLossReport :" + e);
		}
		return response;
	}
	
	@PostMapping("get-profit-loss-monthly")
	public ResponseData<ProfitLossReportRes> getProfitLossMonthlyReport(@RequestBody ProfitLossReportReq req){
		ResponseData<ProfitLossReportRes> response = new ResponseData<ProfitLossReportRes>();
		try {
			response.setData(profitLossReportService.getProfitLossMonthlyReport(req.getFirstDayDate(),req.getLastDayDate()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API ProfitLossReportController.getProfitLossReport");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ProfitLossReportController.getProfitLossReport :" + e);
		}
		return response;
	}
	
}
