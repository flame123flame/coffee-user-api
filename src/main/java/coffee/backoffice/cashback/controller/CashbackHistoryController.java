package coffee.backoffice.cashback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.cashback.model.Cashback;
import coffee.backoffice.cashback.service.CalculateCashbackService;
import coffee.backoffice.cashback.service.CashbackHistoryService;
import coffee.backoffice.cashback.vo.res.CashbackHistoryRes;
import coffee.backoffice.cashback.vo.res.CashbackHistoryUserRes;
import framework.constant.ResponseConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/cashback-history/")
@Slf4j
public class CashbackHistoryController {
	@Autowired
	private CashbackHistoryService cashbackHistoryService;
	@Autowired
	private CalculateCashbackService calculateCashbackService;
	
	@GetMapping("get-history")
	public ResponseData<List<CashbackHistoryRes>> getAll() {
		ResponseData<List<CashbackHistoryRes>> response = new ResponseData<List<CashbackHistoryRes>>();
		try {
			response.setData(cashbackHistoryService.getAll());
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackHistoryService.getAll()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackHistoryService.getAll() :" + e);
		}
		return response;
	}
	
	@GetMapping("/sync-cashback")
	@ResponseBody
	public ResponseData<Cashback> syncCashback() {
		ResponseData<Cashback> response = new ResponseData<Cashback>();
		try {
			response.setData(calculateCashbackService.findCashbackDaily(false));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.syncCashback()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.syncCashback() :" + e);
		}
		return response;
	}
}
