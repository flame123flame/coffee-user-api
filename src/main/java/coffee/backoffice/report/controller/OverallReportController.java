package coffee.backoffice.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.report.service.OverallReportService;
import coffee.backoffice.report.vo.req.OverallGameWinLossReq;
import coffee.backoffice.report.vo.req.OverallReportReq;
import coffee.backoffice.report.vo.req.ValidBestsWinLossReq;
import coffee.backoffice.report.vo.res.CompanySurplusRes;
import coffee.backoffice.report.vo.res.DepositWithdrawRes;
import coffee.backoffice.report.vo.res.OverallCompanyProfitLossRes;
import coffee.backoffice.report.vo.res.OverallGameWinLossRes;
import coffee.backoffice.report.vo.res.OverallReportRes;
import coffee.backoffice.report.vo.res.RegDepositRes;
import coffee.backoffice.report.vo.res.ValidBestsWinLossRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/overall-report/")
@Slf4j
public class OverallReportController {

	@Autowired
	private OverallReportService overallReportService;

	@PostMapping("get-total-summary")
	public ResponseData<OverallReportRes> getTotalSummary(@RequestBody OverallReportReq req) {
		ResponseData<OverallReportRes> response = new ResponseData<>();
		try {
			response.setData(overallReportService.getTotalSummary(req.getFirstDayDate(), req.getLastDayDate()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API OverallReportController => getTotalSummary");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API OverallReportController => getTotalSummary :" + e);
		}
		return response;
	}

	@PostMapping("get-reg-deposit")
	public ResponseData<List<RegDepositRes>> getRegDeposit(@RequestBody OverallReportReq req) {
		ResponseData<List<RegDepositRes>> response = new ResponseData<List<RegDepositRes>>();
		try {
			response.setData(overallReportService.getRegDeposit(req.getFirstDayDate(), req.getLastDayDate()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API OverallReportController => getRegDeposit");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API OverallReportController => getRegDeposit :" + e);
		}
		return response;
	}

	@PostMapping("get-deposit-withdraw")
	public ResponseData<List<DepositWithdrawRes>> getDepositWithdraw(@RequestBody OverallReportReq req) {
		ResponseData<List<DepositWithdrawRes>> response = new ResponseData<List<DepositWithdrawRes>>();
		try {
			response.setData(overallReportService.getDepositWithdraw(req.getFirstDayDate(), req.getLastDayDate()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API OverallReportController => getDepositWithdraw");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API OverallReportController => getDepositWithdraw :" + e);
		}
		return response;
	}

	@PostMapping("get-company-surplus")
	public ResponseData<List<CompanySurplusRes>> getCompanySurplus(@RequestBody OverallReportReq req) {
		ResponseData<List<CompanySurplusRes>> response = new ResponseData<List<CompanySurplusRes>>();
		try {
			response.setData(overallReportService.getCompanyTotal(req.getFirstDayDate(), req.getLastDayDate()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API OverallReportController => getCompanySurplus");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API OverallReportController => getCompanySurplus :" + e);
		}
		return response;
	}

	@PostMapping("get-validbest-winloss")
	public ResponseData<List<ValidBestsWinLossRes>> getValidBestsWinLoss(@RequestBody ValidBestsWinLossReq req) {
		ResponseData<List<ValidBestsWinLossRes>> response = new ResponseData<List<ValidBestsWinLossRes>>();
		try {
			response.setData(overallReportService.getValidBestsWinLoss(req.getFirstDayDate(), req.getLastDayDate(),
					req.getProductCode()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API OverallReportController => getValidBestsWinLoss");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API OverallReportController => getValidBestsWinLoss :" + e);
		}
		return response;
	}

	@PostMapping("get-game-winloss")
	public ResponseData<OverallGameWinLossRes> getGameWinLoss(@RequestBody OverallGameWinLossReq req) {
		ResponseData<OverallGameWinLossRes> response = new ResponseData<OverallGameWinLossRes>();
		try {
			response.setData(overallReportService.getOverallGameWinLoss(req.getFirstDayDate(), req.getLastDayDate(),
					req.getType()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API OverallReportController => getGameWinLoss");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API OverallReportController => getGameWinLoss :" + e);
		}
		return response;
	}

	@PostMapping("get-company-profit-loss")
	public ResponseData<OverallCompanyProfitLossRes> getCompanyProfitLossTable(@RequestBody OverallReportReq req) {
		ResponseData<OverallCompanyProfitLossRes> response = new ResponseData<OverallCompanyProfitLossRes>();
		try {
			response.setData(
					overallReportService.getCompanyProfitLossTable(req.getFirstDayDate(), req.getLastDayDate()));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API OverallReportController => getCompanyProfitLossTable");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API OverallReportController => getCompanyProfitLossTable :" + e);
		}
		return response;
	}
}
