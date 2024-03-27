package coffee.provider.sa.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.provider.sa.vo.model.BetList;
import coffee.provider.sa.vo.res.GetAllBetDetailsForTimeIntervalResponse;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.utils.ConvertDateUtils;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SaGamingTransactionService {
	
	
	@Value("${path.saGamingProvider.apiTransaction}")
	private String apiTransaction;
	
	@Value("${path.saGamingProvider.secretKey}")
	private String secretKey;
	
	@Value("${path.saGamingProvider.md5Key}")
	private String md5Key;
	
	@Value("${path.saGamingProvider.saEncryptKey}")
	private String saEncryptKey;
	
	@Value("${path.saGamingProvider.encryptKey}")
	private String encryptKey;
	
	@Value("${path.saGamingProvider.lobbyCode}")
	private String lobbyCode;

	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private AllTransactionService allTransactionService;
	
	public String connectApi(String queryString,String time) {
		String q = encryptionService.desEncrypt(queryString,encryptKey);
		String s = encryptionService.buildMD5(queryString+md5Key+time+secretKey);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("q", q));
		nvps.add(new BasicNameValuePair("s", s));
		String reponseString = OkHttpClientUtils.doPost(apiTransaction, nvps);
		return reponseString;
	}
	
	public GetAllBetDetailsForTimeIntervalResponse getTransactionBetweenTime(String from ,String to) {
		String time = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMMDDHHMMSS, ConvertDateUtils.LOCAL_EN);
		String queryString = "method=GetAllBetDetailsForTimeIntervalDV"+"&Key="+secretKey+"&Time="+time+"&FromTime="+from+"&ToTime="+to;
		
		String reponseString = connectApi(queryString,time);
		if(!reponseString.contains("<!doctype html>")) {
			JSONObject xmlJSONObj = XML.toJSONObject(reponseString);
			String jsonPrettyPrintString = xmlJSONObj.toString(4);
	        
	        if(jsonPrettyPrintString.length() > 1700) {
//	        	log.info(" :: Transaction SA :: ==> "+ jsonPrettyPrintString);
	        	Gson gson = new Gson();
	            GetAllBetDetailsForTimeIntervalResponse res = gson.fromJson(jsonPrettyPrintString, GetAllBetDetailsForTimeIntervalResponse.class);
	    		return res;
	        }
		}
        return null;
	}

	public GetAllBetDetailsForTimeIntervalResponse updatedTransaction() {
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.SA);
		Calendar time = Calendar.getInstance();
		Date curr = new Date();
		Calendar day1 = Calendar.getInstance();
		String from = "";
		String to = "";
		if(checkTopOne != null) {
			time.setTime(curr);
			time.add(Calendar.HOUR_OF_DAY, +1);
			to = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
			
			time.setTime(checkTopOne.getCreatedDate());
			day1.setTime(curr);
			day1.add(Calendar.HOUR_OF_DAY, -12);
			if(time.getTime().compareTo(day1.getTime()) < 0) {
				time.setTime(day1.getTime());
			}
			from = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
		}else {
			time.setTime(curr);
			time.add(Calendar.HOUR_OF_DAY, +1);
			to = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
			
			time.add(Calendar.HOUR_OF_DAY, -12);
			from = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
		}
		GetAllBetDetailsForTimeIntervalResponse res = getTransactionBetweenTime(from,to);
		if(res != null) {
			String betListString = res.getGetAllBetDetailsForTimeIntervalResponse().getBetDetailList().toString();
			if(StringUtils.isNoneBlank(betListString)) {
				if(betListString.contains("[")) {
					allTransactionService.createTransactionSaUser(res.getGetAllBetDetailsForTimeIntervalResponse().getBetDetailList().getBetDetail());
				}
			}
			
		}
		
		return res;
	}
	
	public GetAllBetDetailsForTimeIntervalResponse testTransaction() {
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.SA);
		Calendar time = Calendar.getInstance();
		String from = "";
		String to = "";
		if(checkTopOne != null) {
			time.setTime(new Date());
			time.add(Calendar.HOUR_OF_DAY, +1);
			to = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
			
			time.setTime(checkTopOne.getCreatedDate());
//			time.add(Calendar.HOUR_OF_DAY, +1);
			from = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
		}else {
			time.setTime(new Date());
			time.add(Calendar.HOUR_OF_DAY, +1);
			to = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
			
			time.add(Calendar.HOUR_OF_DAY, -12);
			from = ConvertDateUtils.formatDateToString(time.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMMSS, ConvertDateUtils.LOCAL_EN);
		}
		GetAllBetDetailsForTimeIntervalResponse res = getTransactionBetweenTime(from,to);
//		if(res != null) {
//			String betListString = res.getGetAllBetDetailsForTimeIntervalResponse().getBetDetailList().toString();
//			if(StringUtils.isNoneBlank(betListString)) {
//				if(betListString.contains("[")) {
//					allTransactionService.createTransactionSaUser(res.getGetAllBetDetailsForTimeIntervalResponse().getBetDetailList().getBetDetail());
//				}
//			}
//			
//		}
		
		return res;
	}
	
}
