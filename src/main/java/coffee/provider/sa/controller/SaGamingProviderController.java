package coffee.provider.sa.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.provider.sa.service.SaGamingService;
import coffee.provider.sa.service.SaGamingTransactionService;
import coffee.provider.sa.vo.res.CreditBalanceResponse;
import coffee.provider.sa.vo.res.DebitAllBalanceResponse;
import coffee.provider.sa.vo.res.GetAllBetDetailsForTimeIntervalResponse;
import coffee.provider.sa.vo.res.KickUserResponse;
import coffee.provider.sa.vo.res.LoginRequestResponse;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/sa-provider")
@Slf4j
public class SaGamingProviderController {

	@Autowired
	private SaGamingService saGamingService;
	
	@Autowired
	private SaGamingTransactionService saGamingTransactionService;
	
	@GetMapping("/login/{username}")
	public ResponseData<LoginRequestResponse> loginApi(@PathVariable("username") String username) {
		ResponseData<LoginRequestResponse> response = new ResponseData<LoginRequestResponse>();
		try {
			response.setData(saGamingService.loginRequest(username,""));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => SaGamingProviderController.loginApi()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => SaGamingProviderController.loginApi() : " );
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("/kick/{username}")
	public ResponseData<KickUserResponse> kickApi(@PathVariable("username") String username) {
		ResponseData<KickUserResponse> response = new ResponseData<KickUserResponse>();
		try {
			response.setData(saGamingService.kickMember(username));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => SaGamingProviderController.kickApi()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => SaGamingProviderController.kickApi() : " );
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/deposit/{username}")
	public ResponseData<CreditBalanceResponse> depositApi(@PathVariable("username") String username) {
		ResponseData<CreditBalanceResponse> response = new ResponseData<CreditBalanceResponse>();
		try {
			response.setData(saGamingService.deposit(username,new BigDecimal(100),"",""));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => SaGamingProviderController.depositApi()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => SaGamingProviderController.depositApi() : " );
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/withdraw/{username}")
	public ResponseData<DebitAllBalanceResponse> withdrawApi(@PathVariable("username") String username) {
		ResponseData<DebitAllBalanceResponse> response = new ResponseData<DebitAllBalanceResponse>();
		try {
			response.setData(saGamingService.withdraw(username));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => SaGamingProviderController.withdrawApi()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => SaGamingProviderController.withdrawApi() : " );
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/playGame/{username}")
	public ResponseData<String> playGame(@PathVariable("username") String username) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			response.setData(saGamingService.playGame(username,new BigDecimal(100)));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => SaGamingProviderController.playGame()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => SaGamingProviderController.playGame() : " );
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/test-transaction")
	public ResponseData<GetAllBetDetailsForTimeIntervalResponse> testTransaction() {
		ResponseData<GetAllBetDetailsForTimeIntervalResponse> response = new ResponseData<GetAllBetDetailsForTimeIntervalResponse>();
		try {
			response.setData(saGamingTransactionService.testTransaction());
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => SaGamingProviderController.testTransaction()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => SaGamingProviderController.testTransaction() : " );
			e.printStackTrace();
		}
		return response;
	}

}
