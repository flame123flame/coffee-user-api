package coffee.provider.lotto.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.WalletService;
import coffee.provider.lotto.vo.model.LottoTransaction;
import coffee.provider.lotto.vo.req.LottoPayCostReq;
import coffee.provider.lotto.vo.req.LottoTransactionReq;
import coffee.provider.lotto.vo.req.LotttoProviderUpdateWalletReqModel;
import coffee.provider.lotto.vo.req.LotttoProviderUpdateWalletReqModel.Lotto;
import coffee.provider.lotto.vo.res.LottoTransactionRes;
import framework.constant.ProjectConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.utils.ConvertDateUtils;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LottoProviderService {

	@Value("${path.lottoProvider.api}")
	private String lottoApiPath;

	@Autowired
	private WalletService walletService;

	@Autowired
	private AllTransactionService allTransactionService;

	public String genarateSignature(List<Lotto> req) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder rawData = new StringBuilder();

		req.forEach(item -> {
			String json = "";
			try {
				json = mapper.writeValueAsString(item);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			rawData.append(json);
		});
		String secretKeyJoker = "∆@∂ßπ≠‘“πø¬æ≥≤µ∫Ω≈ç√ƒ∂ß∑†¥¨ˆø£";
		String signature = null;

		try {
			signature = getHMACSHA1Signature(rawData.toString(), secretKeyJoker);
		} catch (Exception e) {
			log.error("Error Genarate Signature => ", e);
		}
		return signature;
	}

	private String getHMACSHA1Signature(String rawData, String secretKey) throws Exception {
		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signingKey);
		byte[] hashedValue = mac.doFinal(rawData.getBytes());
		return Base64.getEncoder().encodeToString(hashedValue);
	}

	private Boolean checkSignature(List<Lotto> req, String signature) {
		return signature.equals(genarateSignature(req));
	}

	public String lottoProviderUpdateWallet(LotttoProviderUpdateWalletReqModel req) {
		log.info("=== LottoProviderService => lotttoProviderUpdateWallet() on Started ===");
		// if (checkSignature(req.getData(), req.getKey())) {
		System.out.println(req.getKey());
		for (Lotto data : req.getData()) {
			walletService.addBalanceWallet(data.getUsername(), new BigDecimal(data.getPrize()));
		}
		return RESPONSE_MESSAGE.SUCCESS;
		// } else {
		// System.out.println(req.getKey());
		// return "KEY NOT MATCH";
		// }

	}

	public String genKeyTest(LotttoProviderUpdateWalletReqModel req) {
		String key = genarateSignature(req.getData());
		System.out.println("KEY >>> " + key);
		return key;
	}
	
	public void getLottoTransactionByUpdateDate() {
//		TransactionGame lastTransaction = allTransactionService.findLastTransationProviderByUpdatedDate(ProjectConstant.PROVIDERS.LOTTO);
		String url = lottoApiPath + "/api/lotto-transaction/get-transaction-bo";
		LottoTransactionReq req = new LottoTransactionReq();
		Calendar time = Calendar.getInstance();
		time.setTime(new Date());
		req.setTimeEnd(time.getTime());
		time.add(Calendar.MINUTE, -1);
		req.setTimeStart(time.getTime());
		
		String reponseString = OkHttpClientUtils.doPost(url, req ,MediaType.APPLICATION_JSON_VALUE);

		Gson gson = new Gson();
		LottoTransactionRes res = gson.fromJson(reponseString, LottoTransactionRes.class);
		if(res.getData() != null && res.getData().size() > 0) {
			allTransactionService.createTransactionLottoUser(res.getData());
		}
	}

	public void updateTransaction(LottoTransactionRes req) {
		if (req.getData() != null) {
			allTransactionService.createTransactionLottoUser(req.getData());
		}
	}

	public String updateWalletLottoCancel(List<LottoPayCostReq> req) {
		log.info("=== LottoProviderService => updateWalletLottoCancel() on Started ===");
		LottoTransactionRes lottoTransactionReq = new LottoTransactionRes();
		List<LottoTransaction> LottoTransactionList = new ArrayList<LottoTransaction>();
		for (LottoPayCostReq data : req) {
//			System.out.println("TRANSACTION_GAME_CODE:::::::::::" + data.getLottoGroupTransactionCode());
//			System.out.println("USERNAME :::::::::::" + data.getUsername());
//			System.out.println("LOTTO_CLASS_CODE :::" + data.getLottoClassCode());
//			System.out.println("INSTALLMENT ::::::::" + data.getInstallment());
//			System.out.println("ROUND_YEEKEE :::::::" + data.getRoundYeekee());
//			System.out.println("PAY_COST :::::::" + data.getPayCost());
			LottoTransaction item = new LottoTransaction();
			walletService.addBalanceWallet(data.getUsername(), data.getPayCost());
			item.setLottoGroupTransactionCode(data.getLottoGroupTransactionCode());
			item.setStatus("CANCEL");
			item.setPayCost(data.getPayCost());
			LottoTransactionList.add(item);
		}
		System.out.println("LottoTransactionList :::::::" + LottoTransactionList);
		lottoTransactionReq.setData(LottoTransactionList);
		updateTransaction(lottoTransactionReq);
		return RESPONSE_MESSAGE.SUCCESS;
	}
}
