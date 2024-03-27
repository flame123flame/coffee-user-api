package coffee.provider.sbobet.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.provider.sbobet.vo.req.GetBetListRequest;
import coffee.provider.sbobet.vo.res.GetBetListResponse;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.utils.ConvertDateUtils;
import framework.utils.GenerateRandomString;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SboBetTransactionService {

	@Value("${path.sboBetProvider.api}")
    private String sboBetApi;

    @Value("${path.sboBetProvider.name}")
    private String sboBetProviderName;

    @Value("${path.sboBetProvider.companyKey}")
    private String sboBetProviderCompanyKey;
    
    @Value("${path.sboBetProvider.portfolio}")
    private String sboBetProviderSportsBook;
    
    @Autowired
	private AllTransactionService allTransactionService;
    
    private GetBetListResponse getTransactionByModifyDate(String startDate , String endDate) {
    	String serverId = GenerateRandomString.generateUUID();
    	GetBetListRequest data = new GetBetListRequest(sboBetProviderCompanyKey,serverId,sboBetProviderSportsBook);
    	data.setStartDate(startDate);
    	data.setEndDate(endDate);
    	
        String url = sboBetApi + "/web-root/restricted/report/v2/get-bet-list-by-modify-date.aspx";
        String responseString = OkHttpClientUtils.doPost(url, data, MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();
        GetBetListResponse res = gson.fromJson(responseString, GetBetListResponse.class);
        return res;
    }
    
    public void updatedTransaction() {
    	TransactionGame checkTopOne = allTransactionService.findLastTransationProviderByCreatedDate(PROVIDERS.SBO);
    	Date curr = new Date();
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		if(checkTopOne !=null) {
			startDate.setTime(curr);
			startDate.add(Calendar.MINUTE, -1);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			
			endDate.setTime(curr);
			endDate.set(Calendar.SECOND, 0);
			endDate.set(Calendar.MILLISECOND, 0);
		}else {
			startDate.setTime(curr);
			startDate.add(Calendar.HOUR_OF_DAY, -12);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			
			endDate.setTime(curr);
			endDate.set(Calendar.SECOND, 0);
			endDate.set(Calendar.MILLISECOND, 0);
		}
		
		GetBetListResponse response = getTransactionByModifyDate(ConvertDateUtils.calendarToISO8601(startDate),ConvertDateUtils.calendarToISO8601(endDate));
        if(response !=null && response.getResult().size() > 0) {
        	allTransactionService.createTransactionSboUser(response.getResult(),startDate.getTime(),endDate.getTime());
        }
    }
}
