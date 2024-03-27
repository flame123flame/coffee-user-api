package coffee.provider.sexy.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import coffee.provider.sexy.vo.model.Transaction;
import coffee.provider.sexy.vo.res.GetSummaryTransactionByTxTimeHourResponse;
import coffee.provider.sexy.vo.res.GetTransactionByTxTimeResponse;
import framework.constant.ProjectConstant;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.provider.sexy.vo.res.GetTransactionByUpdatedDateResponse;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.utils.ConvertDateUtils;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MxTransactionService {
	
	@Value("${path.mxProvider.api}")
	private String configPathMx;

	@Value("${path.mxProvider.cert}")
	private String cert;

	@Value("${path.mxProvider.agentId}")
	private String agentId;

	@Value("${path.mxProvider.gameType}")
	private String gameType;

	@Value("${path.mxProvider.platform}")
	private String platformMx;
	
	@Value("${path.kmProvider.platform}")
	private String platformKm;
	
	@Value("${path.jiliProvider.platform}")
	private String platformJili;
	
	@Value("${path.pgProvider.platform}")
	private String platformPg;
	
	@Value("${path.rtProvider.platform}")
	private String platformRt;
	
	@Autowired
	private AllTransactionService allTransactionService;

	public GetTransactionByUpdatedDateResponse getTransactionByUpdateDate(String timeFrom,String platform) {
		String url = configPathMx + "/fetch/getTransactionByUpdateDate";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("timeFrom", timeFrom));
		nvps.add(new BasicNameValuePair("platform", platform));

		String responseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		GetTransactionByUpdatedDateResponse res = gson.fromJson(responseString, GetTransactionByUpdatedDateResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [MX,JILLI,KM] getTransactionByUpdateDate  : == Response --> Platform ="+platform+" ,Code ="+res.getStatus());
		}
		return res;
	}

	public GetTransactionByTxTimeResponse getTransactionByTxTime(String platform, String startTime, String endTime) {
		String url = configPathMx + "/fetch/getTransactionByTxTime";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("startTime", startTime));
		nvps.add(new BasicNameValuePair("endTime", endTime));
		nvps.add(new BasicNameValuePair("platform", platform));

		String responseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		GetTransactionByTxTimeResponse res = gson.fromJson(responseString, GetTransactionByTxTimeResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [MX,JILLI,KM] getTransactionByTxTime  : == Response --> Platform ="+platform+" ,Code ="+res.getStatus());
		}
		return res;
	}

	public GetSummaryTransactionByTxTimeHourResponse getSummaryByTxTimeHour(String startTime, String endTime) {
		String url = configPathMx + "/fetch/getTransactionByUpdateDate";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("startTime", startTime));
		nvps.add(new BasicNameValuePair("endTime", endTime));

		String responseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		GetSummaryTransactionByTxTimeHourResponse res = gson.fromJson(responseString, GetSummaryTransactionByTxTimeHourResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [MX,JILLI,KM] getSummaryByTxTimeHour  : == Response --> Code ="+res.getStatus());
		}
		return res;
	}
	
	public void updatedTransaction() {
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.MX);
		Calendar time = Calendar.getInstance();
		Date curr = new Date();
		Calendar day1 = Calendar.getInstance();
		String timeFrom = "";
		if(checkTopOne != null) {
			time.setTime(checkTopOne.getCreatedDate());
			
			day1.setTime(curr);
			day1.add(Calendar.HOUR_OF_DAY, -12);
			if(time.getTime().compareTo(day1.getTime()) < 0) {
				time.setTime(day1.getTime());
			}
			
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}else {
			time.setTime(curr);
			time.add(Calendar.HOUR_OF_DAY, -12);
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}
		GetTransactionByUpdatedDateResponse res = getTransactionByUpdateDate(timeFrom,platformMx);
		if(res != null) {
			allTransactionService.createTransactionMxUser(res.getTransactions());
		}
	}
	
	public void updatedTransactionJili() {
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.JILI);
		Calendar time = Calendar.getInstance();
		Date curr = new Date();
		Calendar day1 = Calendar.getInstance();
		String timeFrom = "";
		if(checkTopOne != null) {
			time.setTime(checkTopOne.getCreatedDate());
			
			day1.setTime(curr);
			day1.add(Calendar.HOUR_OF_DAY, -12);
			if(time.getTime().compareTo(day1.getTime()) < 0) {
				time.setTime(day1.getTime());
			}
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}else {
			time.setTime(curr);
			time.add(Calendar.HOUR_OF_DAY, -12);
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}
		GetTransactionByUpdatedDateResponse res = getTransactionByUpdateDate(timeFrom,platformJili);
		if(res != null) {
			allTransactionService.createTransactionMxUser(res.getTransactions());
		}
	}
	
	public void updatedTransactionKm() {
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.KM);
		Calendar time = Calendar.getInstance();
		Date curr = new Date();
		Calendar day1 = Calendar.getInstance();
		String timeFrom = "";
		if(checkTopOne != null) {
			time.setTime(checkTopOne.getCreatedDate());
			
			day1.setTime(curr);
			day1.add(Calendar.HOUR_OF_DAY, -12);
			if(time.getTime().compareTo(day1.getTime()) < 0) {
				time.setTime(day1.getTime());
			}
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}else {
			time.setTime(curr);
			time.add(Calendar.HOUR_OF_DAY, -12);
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}
		GetTransactionByUpdatedDateResponse res = getTransactionByUpdateDate(timeFrom,platformKm);
		if(res != null) {
			allTransactionService.createTransactionMxUser(res.getTransactions());
		}
	}
	
	public void updatedTransactionRt() {
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.RT);
		Calendar time = Calendar.getInstance();
		Date curr = new Date();
		Calendar day1 = Calendar.getInstance();
		String timeFrom = "";
		if(checkTopOne != null) {
			time.setTime(checkTopOne.getCreatedDate());
			
			day1.setTime(curr);
			day1.add(Calendar.HOUR_OF_DAY, -12);
			if(time.getTime().compareTo(day1.getTime()) < 0) {
				time.setTime(day1.getTime());
			}
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}else {
			time.setTime(curr);
			time.add(Calendar.HOUR_OF_DAY, -12);
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}
		GetTransactionByUpdatedDateResponse res = getTransactionByUpdateDate(timeFrom,platformRt);
		if(res != null) {
			allTransactionService.createTransactionMxUser(res.getTransactions());
		}
	}
	
	public void updatedTransactionPg() {
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.PG);
		Calendar time = Calendar.getInstance();
		Date curr = new Date();
		Calendar day1 = Calendar.getInstance();
		String timeFrom = "";
		if(checkTopOne != null) {
			time.setTime(checkTopOne.getCreatedDate());
			
			day1.setTime(curr);
			day1.add(Calendar.HOUR_OF_DAY, -12);
			if(time.getTime().compareTo(day1.getTime()) < 0) {
				time.setTime(day1.getTime());
			}
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}else {
			time.setTime(curr);
			time.add(Calendar.HOUR_OF_DAY, -12);
			timeFrom = ConvertDateUtils.calendarToISO8601(time);
		}
		GetTransactionByUpdatedDateResponse res = getTransactionByUpdateDate(timeFrom,platformPg);
		if(res != null) {
			allTransactionService.createTransactionMxUser(res.getTransactions());
		}
	}

	public void fixedOnDayTransaction() {
		fixedTransaction(PROVIDERS.MX, platformMx);
		fixedTransaction(PROVIDERS.KM, platformKm);
		fixedTransaction(PROVIDERS.JILI, platformJili);
		fixedTransaction(PROVIDERS.PG, platformPg);
		fixedTransaction(PROVIDERS.RT, platformRt);
	}

	private void fixedTransaction(String provider, String platform) {
		Calendar start = Calendar.getInstance();
		start.setTime(new Date());
		start.set(Calendar.HOUR, 23);
		start.set(Calendar.MINUTE, 59);
		start.set(Calendar.SECOND, 0);
		Calendar end = start;
		start.add(Calendar.DAY_OF_MONTH, -1);
		String startTime = ConvertDateUtils.calendarToISO8601(start);
		String endTime = ConvertDateUtils.calendarToISO8601(end);

		BigDecimal totalRealBetAmount = BigDecimal.ZERO;
		BigDecimal totalTurnOver = BigDecimal.ZERO;
		List<TransactionGame> transactionGamesList = allTransactionService.getGameTransactionByGameProviderBetween(provider, start.getTime(), end.getTime());
		for (TransactionGame temp : transactionGamesList) {
			totalRealBetAmount = totalRealBetAmount.add(temp.getBet());
			totalTurnOver = totalTurnOver.add(temp.getValidBet());
		}

		GetSummaryTransactionByTxTimeHourResponse resSummary = getSummaryByTxTimeHour(startTime, endTime);
		if (resSummary != null) {
			if (resSummary.getStatus().equals(ProjectConstant.STATUS.MX_0000)) {
				BigDecimal sumBet = resSummary.getTransactions().getRealBetAmount();
				BigDecimal sumTurn = resSummary.getTransactions().getTurnover();

				if (sumBet.compareTo(totalRealBetAmount) != 0 || sumTurn.compareTo(totalTurnOver) != 0) {
					GetTransactionByTxTimeResponse resTransactionMx = getTransactionByTxTime(platform, startTime, endTime);
					if (resTransactionMx != null) {
						allTransactionService.createTransactionMxUser(resTransactionMx.getTransactions());
					}
				}
			}
		}
	}
}
