package coffee.backoffice.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.casino.vo.res.GameProviderCodeRes;
import coffee.backoffice.finance.service.GameTransactionReportService;
import coffee.backoffice.finance.vo.res.GameTransactionRes;
import coffee.backoffice.finance.vo.res.GamesListRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/game-transaction/")
@Slf4j
public class GameTransactionController {
	
	@Autowired
	GameTransactionReportService gameTransactionReportService;
	
	@PostMapping("get-paginate")
	public ResponseData<DataTableResponse<GameTransactionRes>> getGameTransactionPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<GameTransactionRes>> response = new ResponseData<DataTableResponse<GameTransactionRes>>();
		try {
			response.setData(gameTransactionReportService.getAllPaginate(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API GameReportController => getGameTransactionPaginate");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameReportController => getGameTransactionPaginate :" + e);
		}
		return response;
	}
	
	// GET BY PRODUCT CODE
		@GetMapping("get-mapping/{productCode}")
		public ResponseData<List<GameProviderCodeRes>> getMapping(@PathVariable("productCode") String pdCode) {
			ResponseData<List<GameProviderCodeRes>> response = new ResponseData<List<GameProviderCodeRes>>();
			try {
				response.setData(gameTransactionReportService.getByProductCode(pdCode));
				response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
				response.setStatus(RESPONSE_STATUS.SUCCESS);
				log.info("Success Calling API => GameReportController getMapping");
			} catch (Exception e) {
				response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
				response.setStatus(RESPONSE_STATUS.FAILED);
				log.error("Error Calling API GameReportController  getMapping:" + e);
			}
			return response;
	}
		// GET BY GAME CODE
		@GetMapping("get-by-code")
		public ResponseData<List<GamesListRes>> getGameByCode(@RequestParam String code,String groupCode) {
			ResponseData<List<GamesListRes>> response = new ResponseData<List<GamesListRes>>();
			System.out.println(code);
			System.out.println(groupCode);
			try {
				response.setData(gameTransactionReportService.getGameByCode(code,groupCode));
				response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
				response.setStatus(RESPONSE_STATUS.SUCCESS);
				log.info("Success Calling API => GameReportController getGameByCode");
			} catch (Exception e) {
				response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
				response.setStatus(RESPONSE_STATUS.FAILED);
				log.error("Error Calling API GameReportController getGameByCode :" + e);
			}
			return response;
		}
		
		@GetMapping("get-by-provider-code/{code}")
		public ResponseData<List<GamesListRes>> getGameByProviderCode(@PathVariable("code") String code) {
			ResponseData<List<GamesListRes>> response = new ResponseData<List<GamesListRes>>();
			try {
				response.setData(gameTransactionReportService.getGameByProviderCode(code));
				response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
				response.setStatus(RESPONSE_STATUS.SUCCESS);
				log.info("Success Calling API => GameReportController getGameByProviderCode");
			} catch (Exception e) {
				response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
				response.setStatus(RESPONSE_STATUS.FAILED);
				log.error("Error Calling API GameReportController getGameByProviderCode :" + e);
			}
			return response;
		}
	
	
	
}
