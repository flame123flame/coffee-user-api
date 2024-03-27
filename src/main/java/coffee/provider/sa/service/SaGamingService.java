package coffee.provider.sa.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import coffee.backoffice.finance.service.GameHistoryService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WalletTransferService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.provider.sa.vo.model.CreditBalance;
import coffee.provider.sa.vo.model.DebitAllBalance;
import coffee.provider.sa.vo.model.KickUser;
import coffee.provider.sa.vo.model.LoginRequest;
import coffee.provider.sa.vo.model.RegUserInfo;
import coffee.provider.sa.vo.res.CreditBalanceResponse;
import coffee.provider.sa.vo.res.DebitAllBalanceResponse;
import coffee.provider.sa.vo.res.KickUserResponse;
import coffee.provider.sa.vo.res.LoginRequestResponse;
import coffee.provider.sa.vo.res.RegUserInfoResponse;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.constant.ProjectConstant.STATUS;
import framework.utils.ConvertDateUtils;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SaGamingService {
	
	@Value("${path.saGamingProvider.api}")
	private String apiPath;
	
	@Value("${path.saGamingProvider.apiClient}")
	private String apiClient;
	
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
	
	@Value("${path.saGamingProvider.homePath}")
	private String homePath;

	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private GameHistoryService gameHistoryService;
	
	@Autowired
	private WalletTransferService walletTransferService;
	
	public String connectApi(String queryString,String time) {
		String q = encryptionService.desEncrypt(queryString,encryptKey);
		String s = encryptionService.buildMD5(queryString+md5Key+time+secretKey);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("q", q));
		nvps.add(new BasicNameValuePair("s", s));
		String reponseString = OkHttpClientUtils.doPost(apiPath, nvps);
		return reponseString;
	}
	
	public RegUserInfoResponse createMember(String username) {
		String time = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMMDDHHMMSS, ConvertDateUtils.LOCAL_EN);
		String queryString = "method=RegUserInfo"+"&Key="+secretKey+"&Time="+time+"&Username="+username+"&CurrencyType=THB";
		
		JSONObject xmlJSONObj = XML.toJSONObject(connectApi(queryString,time));
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
         
        Gson gson = new Gson();
        RegUserInfoResponse res = gson.fromJson(jsonPrettyPrintString, RegUserInfoResponse.class);
        if(res.getRegUserInfoResponse() != null) {
        	RegUserInfo response = res.getRegUserInfoResponse();
			log.info("== : SA createMember [Username :"+username+"] : == Response -->" + response.getErrorMsg() );
		}
		return res;
	}
	
	public LoginRequestResponse loginRequest(String username,String time ) {
//		String time = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMMDDHHMMSS, ConvertDateUtils.LOCAL_EN);
		String queryString = "method=LoginRequest"+"&Key="+secretKey+"&Time="+time+"&Username="+username+"&CurrencyType=THB";
		
		JSONObject xmlJSONObj = XML.toJSONObject(connectApi(queryString,time));
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
         
        Gson gson = new Gson();
        LoginRequestResponse res = gson.fromJson(jsonPrettyPrintString, LoginRequestResponse.class);
        if(res.getLoginRequestResponse() != null) {
        	LoginRequest response = res.getLoginRequestResponse();
			log.info("== : SA withdraw [Username :"+username+"] : == Response -->" + response.getErrorMsg() );
		}
		return res;
	}
	
	public KickUserResponse kickMember(String username) {
		String time = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMMDDHHMMSS, ConvertDateUtils.LOCAL_EN);
		String queryString = "method=KickUser"+"&Key="+secretKey+"&Time="+time+"&Username="+username;
		
		JSONObject xmlJSONObj = XML.toJSONObject(connectApi(queryString,time));
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
         
        Gson gson = new Gson();
        KickUserResponse res = gson.fromJson(jsonPrettyPrintString, KickUserResponse.class);
        if(res.getKickUserResponse() != null) {
        	KickUser response = res.getKickUserResponse();
			log.info("== : SA withdraw [Username :"+username+"] : == Response -->" + response.getErrorMsg() );
		}
		return res;
	}
	
	public DebitAllBalanceResponse withdraw(String username) {
		String time = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMMDDHHMMSS, ConvertDateUtils.LOCAL_EN);
		String queryString = "method=DebitAllBalanceDV"+"&Key="+secretKey+"&Time="+time+"&Username="+username+"&OrderId=OUT"+time+username;
		
		JSONObject xmlJSONObj = XML.toJSONObject(connectApi(queryString,time));
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
         
        Gson gson = new Gson();
        DebitAllBalanceResponse res = gson.fromJson(jsonPrettyPrintString, DebitAllBalanceResponse.class);
        if(res.getDebitAllBalanceResponse() != null) {
        	DebitAllBalance response = res.getDebitAllBalanceResponse();
			log.info("== : SA withdraw [Username :"+username+"] : == Response -->" + response.getErrorMsg() );
		}
		return res;
	}
	
	public CreditBalanceResponse deposit(String username,BigDecimal amount ,String orderId ,String time) {
		String queryString = "method=CreditBalanceDV"+"&Key="+secretKey+"&Time="+time+"&Username="+username+"&OrderId=IN"+time+username+"&CreditAmount="+amount.toString();
		
		JSONObject xmlJSONObj = XML.toJSONObject(connectApi(queryString,time));
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
         
        Gson gson = new Gson();
        CreditBalanceResponse res = gson.fromJson(jsonPrettyPrintString, CreditBalanceResponse.class);
        if(res.getCreditBalanceResponse() != null) {
        	CreditBalance response = res.getCreditBalanceResponse();
			log.info("== : SA deposit [Username :"+username+"] : == Response -->" + response.getErrorMsg() );
		}
		return res;
	}
	
	
	public String playGame(String username,BigDecimal amount) {
		log.info(" [SA] playGame : == On start --> ");
		String time = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMMDDHHMMSS, ConvertDateUtils.LOCAL_EN);
		String url = "";
				
		LoginRequestResponse temp = loginRequest(username,time);
		if(temp.getLoginRequestResponse().getToken() != null) {
			
			String token = temp.getLoginRequestResponse().getToken();
			if(amount.compareTo(BigDecimal.ZERO) > 0) {
				String orderId = "IN"+time+username;
				CreditBalanceResponse check = deposit(username,amount,orderId,time);
				String errorId = String.valueOf(check.getCreditBalanceResponse().getErrorMsgId());
				if(STATUS.SA_0.equals(errorId)) {
					url = apiClient+"?username="+username+"&token="+token+"&lobby="+lobbyCode+"&lang=th"+"&returnurl="+homePath+"&mobile=true&options=defaulttable=601,hidelogo=1";
					log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+", orderId ="+orderId+"] =:= ");
					
					walletTransferService.tranferFromMain(orderId, username, PROVIDERS.SA, amount);
					log.info(" [SA] playGame : == Ending ");
				}
			}else {
				url = apiClient+"?username="+username+"&token="+token+"&lobby="+lobbyCode+"&lang=th"+"&returnurl="+homePath+"&mobile=true&options=defaulttable=601,hidelogo=1";
				log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+"] =:= ");
			}
		}else {
			String errorId = String.valueOf(temp.getLoginRequestResponse().getErrorMsgId());
			log.info(" [SA] playGame : Login == Error :"+errorId);
		}
		
		log.info(" [SA] playGame : == Ending ");
		return url;
	}
	
	public void updateCredit(Customer customer,String provider) {
		log.info(" [SA] updateCredit : == On start --> ");
		
		if (StringUtils.isNoneBlank(customer.getLastProvider())) {
			String username = customer.getUsername();
			
			DebitAllBalanceResponse withdraw = withdraw(username);
			if (withdraw != null) {
				DebitAllBalance data = withdraw.getDebitAllBalanceResponse();
				if (STATUS.SA_0.equals(String.valueOf(data.getErrorMsgId()))) {
					
					BigDecimal amount = data.getDebitAmount();
					log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+", orderId ="+data.getOrderId()+"] =:= ");
					
					walletService.updateWallet(username, provider, amount);
					walletTransferService.tranferToMain(data.getOrderId(), username, PROVIDERS.SA, amount);
					gameHistoryService.updateGameHistory(username, provider, amount,"");
					kickMember(username);
					
					customer.setLastProvider("");
					customerService.saveCustomer(customer);
					
				}
			}
		}
		
		log.info(" [SA] updateCredit : == Ending ");
	}
	
}
