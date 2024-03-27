package coffee.backoffice.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.vo.req.AllTransactionReq;
import coffee.backoffice.finance.vo.req.PlayerValidBetReq;
import coffee.backoffice.finance.vo.res.AllTransactionRes;
import coffee.backoffice.finance.vo.res.PlayerValidBetProductRes;
import coffee.backoffice.finance.vo.res.PlayerValidBetRes;
import coffee.backoffice.finance.vo.res.TransactionGameRes;
import coffee.backoffice.finance.vo.res.TransactionLogRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/transaction/")
@Slf4j
public class AllTransactionController {

	@Autowired
	private AllTransactionService allTransactionService;

	@GetMapping("get-all")
	public ResponseData<List<TransactionList>> getAll() {
		ResponseData<List<TransactionList>> response = new ResponseData<List<TransactionList>>();
		try {
			response.setData(allTransactionService.getAll());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AllTransactionController => getAll");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AllTransactionController => getAll :" + e);
		}
		return response;
	}

	@GetMapping("get-all-game")
	public ResponseData<List<TransactionGame>> getAllGame() {
		ResponseData<List<TransactionGame>> response = new ResponseData<List<TransactionGame>>();
		try {
			response.setData(allTransactionService.getAllGame());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AllTransactionController => getAllGame");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AllTransactionController => getAllGame :" + e);
		}
		return response;
	}

	@PostMapping("get-transaction-game")
	public ResponseData<DataTableResponse<TransactionGameRes>> getTransactionGame(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<TransactionGameRes>> response = new ResponseData<DataTableResponse<TransactionGameRes>>();
		try {
			response.setData(allTransactionService.getTransactionGame(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AllTransactionController => getTransactionGame");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AllTransactionController => getTransactionGame :" + e);
		}
		return response;
	}

	@PostMapping("get-transaction-log")
	public ResponseData<DataTableResponse<TransactionLogRes>> getTransactionLog(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<TransactionLogRes>> response = new ResponseData<DataTableResponse<TransactionLogRes>>();
		try {
			response.setData(allTransactionService.getTransactionLog(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AllTransactionController => getAll");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AllTransactionController => getAll :" + e);
		}
		return response;
	}

	@PostMapping("get-player-valid-bet-provider")
	public ResponseData<List<PlayerValidBetRes>> getPlayerValidBetProvider(@RequestBody PlayerValidBetReq req) {
		ResponseData<List<PlayerValidBetRes>> response = new ResponseData<List<PlayerValidBetRes>>();
		try {
			response.setData(allTransactionService.getPlayerValidBetProvider(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AllTransactionController => getPlayerValidBetProvider");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AllTransactionController => getPlayerValidBetProvider :" + e);
		}
		return response;
	}
	
	@PostMapping("get-player-valid-bet-product")
	public ResponseData<List<PlayerValidBetProductRes>> getPlayerValidBetProduct(@RequestBody PlayerValidBetReq req) {
		ResponseData<List<PlayerValidBetProductRes>> response = new ResponseData<List<PlayerValidBetProductRes>>();
		try {
			response.setData(allTransactionService.getPlayerValidBetProduct(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AllTransactionController => getPlayerValidBet");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AllTransactionController => getPlayerValidBet :" + e);
		}
		return response;
	}
	
	
//	@PostMapping("search-time")
//	public ResponseData<List<AllTransactionRes>> getSearchTime(@RequestBody AllTransactionReq req) {
//		ResponseData<List<AllTransactionRes>> response = new ResponseData<List<AllTransactionRes>>();
//		try {
//			response.setData(allTransactionService.getSearchTime(req));
//			response.setMessage(GET.SUCCESS);
//			response.setStatus(RESPONSE_STATUS.SUCCESS);
//			log.info("Success Calling API AllTransactionController => getPlayerValidBet");
//		} catch (Exception e) {
//			response.setMessage(GET.FAILED);
//			response.setStatus(RESPONSE_STATUS.FAILED);
//			log.error("Error Calling API AllTransactionController => getPlayerValidBet :" + e);
//		}
//		return response;
//	}
	
	@PostMapping("all-transaction")
	public ResponseData <AllTransactionRes> getAllTransaction(@RequestBody AllTransactionReq req) {
		ResponseData <AllTransactionRes> response = new ResponseData <AllTransactionRes>();
		try {
			response.setData(allTransactionService.getAllTransaction(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AllTransactionController => getAllTransaction");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AllTransactionController => getAllTransaction :" + e);
		}
		return response;
	}
}
