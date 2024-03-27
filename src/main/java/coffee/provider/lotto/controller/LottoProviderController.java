package coffee.provider.lotto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.provider.lotto.service.LottoProviderService;
import coffee.provider.lotto.vo.req.LottoPayCostReq;
import coffee.provider.lotto.vo.req.LottoTransactionReq;
import coffee.provider.lotto.vo.req.LotttoProviderUpdateWalletReqModel;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/lotto-provider")
@Slf4j
public class LottoProviderController {

	@Autowired
	private LottoProviderService lottoProviderService;

	@PostMapping("/update-wallet-prize")
	public ResponseData<String> lotttoProviderUpdateWallet(@RequestBody LotttoProviderUpdateWalletReqModel req) {
		ResponseData<String> response = new ResponseData<>();
		try {
			response.setData(lottoProviderService.lottoProviderUpdateWallet(req));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => LottoProviderController.lotttoProviderUpdateWallet()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => LottoProviderController.lotttoProviderUpdateWallet() : " + e);
		}
		return response;
	}
	
//	@PostMapping("/update-lotto-transaction")
//	public ResponseData<String> updateLottoTransaction(@RequestBody LottoTransactionReq req) {
//		ResponseData<String> response = new ResponseData<>();
//		try {
//			lottoProviderService.updateTransaction(req);
//			response.setMessage(response.getData());
//			response.setStatus(RESPONSE_STATUS.SUCCESS);
//			log.info("Success Calling API => LottoProviderController.updateLottoTransaction()");
//
//		} catch (Exception e) {
//			response.setMessage(RESPONSE_MESSAGE.ERROR500);
//			response.setStatus(RESPONSE_STATUS.FAILED);
//			log.info("Failed Calling API => LottoProviderController.updateLottoTransaction() : " + e);
//		}
//		return response;
//	}
	
	@PostMapping("/update-wallet-lotto-cancel")
	public ResponseData<String> updateWalletLottoCancel(@RequestBody List<LottoPayCostReq> req) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			response.setData(lottoProviderService.updateWalletLottoCancel(req));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => LottoProviderController.updateWalletLottoCancel()");

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Failed Calling API => LottoProviderController.updateWalletLottoCancel() : " + e);
		}
		return response;
	}

//	@PostMapping("/gen-key-lotto")
//	public ResponseData<String> genKeyTest(@RequestBody LotttoProviderUpdateWalletReqModel req) {
//		ResponseData<String> response = new ResponseData<>();
//		try {
//			response.setData(lottoProviderService.genKeyTest(req));
//			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
//			response.setStatus(RESPONSE_STATUS.SUCCESS);
//			log.info("Success Calling API => LottoProviderController.genKeyTest()");
//
//		} catch (Exception e) {
//			response.setMessage(RESPONSE_MESSAGE.ERROR500);
//			response.setStatus(RESPONSE_STATUS.FAILED);
//			log.info("Failed Calling API => LottoProviderController.genKeyTest() : " + e);
//		}
//		return response;
//	}

}
