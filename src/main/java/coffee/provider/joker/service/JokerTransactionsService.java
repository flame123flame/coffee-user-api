package coffee.provider.joker.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.provider.joker.vo.model.TransactionList;
import coffee.provider.joker.vo.req.TransactionByDateRequest;
import coffee.provider.joker.vo.res.GenarateSignatureRes;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.constant.ProjectConstant.TRANSACTION_METHOD;
import framework.utils.ConvertDateUtils;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JokerTransactionsService {
	
	@Value("${path.jokerProvider.api}")
	private String configPathJocker;
	
	@Value("${path.jokerProvider.appid}")
	private String jokerProviderAppid;
	
	@Autowired
	private JokerProviderService jokerProviderService;
	
	@Autowired
	private AllTransactionService allTransactionService;

	public void updatedTransaction() {
		TransactionByDateRequest dataTBD = new TransactionByDateRequest(TRANSACTION_METHOD.TRANSACTION_MINUTES);
		TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.JOKER);
		Date curr = new Date();
		Calendar calendar = Calendar.getInstance();
		Calendar day1 = Calendar.getInstance();
		
		
		if(checkTopOne != null) {
			calendar.setTime(checkTopOne.getCreatedDate());
//			calendar.add(Calendar.MINUTE, -13 );
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			day1.setTime(curr);
			day1.add(Calendar.HOUR_OF_DAY, -12);
			if(calendar.getTime().compareTo(day1.getTime()) < 0) {
				calendar.setTime(day1.getTime());
			}
			dataTBD.setNextId(checkTopOne.getGameSessionId());
		}else{
			calendar.setTime(curr);
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			dataTBD.setNextId("");
		}
		dataTBD.setStartDate(ConvertDateUtils.formatDateToStringEn(calendar.getTime(), ConvertDateUtils.YYYY_MM_DD_HHMM));
		dataTBD.setEndDate(ConvertDateUtils.formatDateToStringEn(curr, ConvertDateUtils.YYYY_MM_DD_HHMM));
		
		
		TransactionList res = new TransactionList();
		int round = 0;
		while(res.getData() == null && round < 10) {
			round++;
			GenarateSignatureRes sigKeyRes = jokerProviderService.genarateSignature(dataTBD);
			dataTBD.setTimestamp(sigKeyRes.getTimestampUnix());
			String url = configPathJocker + "?appid=" + jokerProviderAppid + "&signature=" + sigKeyRes.getSignature()
					+ "&timeZone=SE%20Asia%20Standard%20Time";
			String reponseString = OkHttpClientUtils.doPost(url, dataTBD, MediaType.APPLICATION_JSON_VALUE);
			
			log.error("== : Joker transaction : == Metohd --> " + TRANSACTION_METHOD.TRANSACTION_MINUTES );
			log.error("== : Joker transaction : == URL --> " + url );
//			log.error("== : Joker transaction : == reponseString --> " + reponseString );
			
			Gson gson = new Gson();
			res = gson.fromJson(reponseString, TransactionList.class);
			
			if(res.getMessage() != null) {
				log.error("== : Joker transaction : == Error -->" + res.getMessage() );
			}
		}
		
		if(res.getData() != null) {
			allTransactionService.createTransactionJokerUser(res.getData().getGame(),calendar.getTime(),curr);
		}
		
	};	
}
