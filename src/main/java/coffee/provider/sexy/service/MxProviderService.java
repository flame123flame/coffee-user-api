package coffee.provider.sexy.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import coffee.backoffice.finance.service.GameHistoryService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WalletTransferService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.provider.sexy.vo.model.BetLimit;
import coffee.provider.sexy.vo.model.GameType;
import coffee.provider.sexy.vo.model.Platform;
import coffee.provider.sexy.vo.req.MxRequest;
import coffee.provider.sexy.vo.res.CheckTransferOperationResponse;
import coffee.provider.sexy.vo.res.CreateMemberResponse;
import coffee.provider.sexy.vo.res.DepositResponse;
import coffee.provider.sexy.vo.res.LoginResponse;
import coffee.provider.sexy.vo.res.LogoutResponse;
import coffee.provider.sexy.vo.res.WithdrawResponse;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.constant.ProjectConstant.STATUS;
import framework.utils.GenerateRandomString;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MxProviderService {

	@Value("${path.mxProvider.api}")
	private String configPathMx;

	@Value("${path.mxProvider.cert}")
	private String cert;

	@Value("${path.mxProvider.agentId}")
	private String agentId;

	@Value("${path.mxProvider.betLimit}")
	private String betLimit;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private GameHistoryService gameHistoryService;
	
	@Autowired
	private WalletTransferService walletTransferService;

	public CreateMemberResponse createMember(String username) {
//		SexyProviderCreateMemberReqModel req = new SexyProviderCreateMemberReqModel(cert,agentId,request.getUserId(),ProjectConstant.CURRENCY.THB,String.valueOf(bet));
		BetLimit bet = new BetLimit();
		Platform pf = new Platform();
		GameType gt = new GameType();
		gt.setLimitId(Arrays.asList(betLimit.split(",")));
		pf.setLIVE(gt);
		bet.setSEXYBCRT(pf);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = mapper.writeValueAsString(bet);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String url = configPathMx + "/wallet/createMember";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("userId", username));
		nvps.add(new BasicNameValuePair("currency", ProjectConstant.CURRENCY.THB));
		nvps.add(new BasicNameValuePair("betLimit", jsonString));
		log.info(" =:= [SEXY] createMember =:= ");
		log.info(" =:= Infomation --> [ Request = "+nvps+" ] =:= ");
		String reponseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		CreateMemberResponse res = gson.fromJson(reponseString, CreateMemberResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [SEXY] createMember [Username :"+username+"] : == Response --> Code ="+res.getStatus()+" || Desc = "+res.getDesc());
		}
		return res;
	}

	public LoginResponse login(MxRequest request) {
//		SexyProviderLoginReqModel req = new SexyProviderLoginReqModel(cert,agentId,request.getUserId());

		String url = configPathMx + "/wallet/login";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("userId", request.getUserId()));
		log.info(" =:= [SEXY] login =:= ");
		log.info(" =:= Infomation --> [ Request = "+nvps+" ] =:= ");
		String reponseString = OkHttpClientUtils.doPost(url, nvps);
		
		Gson gson = new Gson();
		LoginResponse res = gson.fromJson(reponseString, LoginResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [SEXY] login [Username :"+request.getUserId()+"] : == Response --> Code ="+res.getStatus()+" || Desc = "+res.getDesc());
		}
		return res;
	}

	public LoginResponse doLoginLaunchGame(MxRequest request) {

		String url = configPathMx + "/wallet/doLoginAndLaunchGame";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("userId", request.getUserId()));
		nvps.add(new BasicNameValuePair("gameCode", request.getGameCode()));
		nvps.add(new BasicNameValuePair("gameType", request.getGameType()));
		nvps.add(new BasicNameValuePair("platform", request.getPlatform()));
		nvps.add(new BasicNameValuePair("language", "th"));
		
		log.info(" =:= [SEXY] doLoginLaunchGame =:= ");
		log.info(" =:= Infomation --> [ Request = "+nvps+" ] =:= ");
		String reponseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		LoginResponse res = gson.fromJson(reponseString, LoginResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [SEXY] doLoginLaunchGame [Username :"+request.getUserId()+"] : == Request --> URL Encode ="+nvps);
			log.info("== : [SEXY] doLoginLaunchGame [Username :"+request.getUserId()+"] : == Response --> Code ="+res.getStatus()+" || Desc = "+res.getDesc());	
		}
		return res;
	}

	public LogoutResponse logout(MxRequest request) {
//		SexyProviderLogoutReqModel req = new SexyProviderLogoutReqModel(cert,agentId,String.join(",", request.getUserIds()));

		String url = configPathMx + "/wallet/logout";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("userIds", String.join(",", request.getUserIds())));
		log.info(" =:= [SEXY] logout =:= ");
		log.info(" =:= Infomation --> [ Request = "+nvps+" ] =:= ");
		String reponseString = OkHttpClientUtils.doPost(url, nvps);
		
		Gson gson = new Gson();
		LogoutResponse res = gson.fromJson(reponseString, LogoutResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [SEXY] logout [Username :"+String.join(",", request.getUserIds())+"] : == Response --> Code = "+res.getStatus()+" || Desc = "+res.getResults());
		}
		return res;
	}

	public WithdrawResponse withdraw(MxRequest request,String orderId) {
//		SexyProviderWithdrawReqModel req = new SexyProviderWithdrawReqModel(cert,agentId,request.getUserId(),GenerateRandomString.generateUUID());

		String url = configPathMx + "/wallet/withdraw";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("userId", request.getUserId()));
		nvps.add(new BasicNameValuePair("txCode", orderId));
		log.info(" =:= [SEXY] withdraw =:= ");
		log.info(" =:= Infomation --> [ Request = "+nvps+" ] =:= ");
		String reponseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		WithdrawResponse res = gson.fromJson(reponseString, WithdrawResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [SEXY] withdraw [Username :"+request.getUserId()+"] : == Response --> Code = "+res.getStatus()+" || TxCode = "+res.getTxCode());
		}
		return res;
	}

	public DepositResponse deposit(MxRequest request) {
		String url = configPathMx + "/wallet/deposit";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("userId", request.getUserId()));
		nvps.add(new BasicNameValuePair("transferAmount", request.getAmount()));
		nvps.add(new BasicNameValuePair("txCode", request.getOrderId()));
		log.info(" =:= [SEXY] deposit =:= ");
		log.info(" =:= Infomation --> [ Request = "+nvps+" ] =:= ");
		String responseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		DepositResponse res = gson.fromJson(responseString, DepositResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [SEXY] deposit [Username :"+request.getUserId()+"] : == Response --> Code = "+res.getStatus()+" || TxCode = "+res.getTxCode());
		}
		return res;
	}

	public CheckTransferOperationResponse checkTransferOperation() {
		String url = configPathMx + "/wallet/checkTransferOperation";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cert", cert));
		nvps.add(new BasicNameValuePair("agentId", agentId));
		nvps.add(new BasicNameValuePair("txCode", GenerateRandomString.generateUUID()));
		log.info(" =:= [SEXY] checkTransferOperation =:= ");
		log.info(" =:= Infomation --> [ Request = "+nvps+" ] =:= ");
		String responseString = OkHttpClientUtils.doPost(url, nvps);

		Gson gson = new Gson();
		CheckTransferOperationResponse res = gson.fromJson(responseString, CheckTransferOperationResponse.class);
		if(res.getStatus() != null) {
			log.info("== : [SEXY] checkTransferOperation : == Response --> Code = "+res.getStatus()+" || TxCode = "+res.getTxCode());
		}
		return res;
	}

	public String playGame(MxRequest request,String provider) {
		log.info(" ["+provider+"] playGame : == On start --> ");
		BigDecimal amount = new BigDecimal(request.getAmount());
		LoginResponse loginRes = new LoginResponse();
		
		loginRes = doLoginLaunchGame(request);
		if (STATUS.MX_0000.equals(loginRes.getStatus())) {
			if(amount.compareTo(BigDecimal.ZERO) > 0) {
				DepositResponse depositRes = deposit(request);
				if (STATUS.MX_0000.equals(depositRes.getStatus())) {
					log.info(" =:= Infomation --> [ username ="+request.getUserId()+" ,amount ="+amount+", orderId ="+request.getOrderId()+", provider ="+provider+"] =:= ");
					walletTransferService.tranferFromMain(request.getOrderId(), request.getUserId(), provider, amount);
					
					log.info(" ["+provider+"] playGame : == Ending ");
					return loginRes.getUrl();
				} else {
					log.info(" ["+provider+"] Error deposit : "+depositRes.getStatus());
					log.info(" ["+provider+"] playGame : == Ending ");
					return depositRes.getStatus();
				}
			}else {
				walletTransferService.tranferFromMain(request.getOrderId(), request.getUserId(), provider, amount);
				log.info(" =:= Infomation --> [ username ="+request.getUserId()+" ,amount ="+amount+", orderId ="+request.getOrderId()+", provider ="+provider+"] =:= ");
				log.info(" ["+provider+"] playGame : == Ending ");
				return loginRes.getUrl();
			}
		}else {
			log.info(" ["+provider+"] Error Login : "+loginRes.getStatus());
			log.info(" ["+provider+"] playGame : == Ending ");
			return loginRes.getStatus();
		}
		
	}

	public void updateCredit(Customer customer,String provider,String orderId) {
		log.info(" [SEXY --> "+provider+"] updateCredit : == On start --> ");
		
		if (StringUtils.isNoneBlank(customer.getLastProvider())) {
			MxRequest request = new MxRequest();
			String username = customer.getUsername();
			request.setUserId(username);
			request.setUserIds(Arrays.asList(username));

//			CheckTransferOperationResponse checkTranfer = checkTransferOperation();
			LogoutResponse logout = logout(request);
			WithdrawResponse temp = withdraw(request,orderId);
			if (temp != null) {
				if (STATUS.MX_0000.equals(temp.getStatus())) {
					
					BigDecimal amount = new BigDecimal(temp.getAmount());
					log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+", orderId ="+temp.getTxCode()+"] =:= ");
					
					walletService.updateWallet(username, provider, amount);
					walletTransferService.tranferToMain(temp.getTxCode(), username, provider, amount);
					gameHistoryService.updateGameHistory(username, provider, amount,"");
					log.info(" ["+provider+"] updateCredit : == Success :");
					
					customer.setLastProvider("");
					customerService.saveCustomer(customer);
				}
				
				if (STATUS.MX_1018.equals(temp.getStatus())) {
					BigDecimal amount = BigDecimal.ZERO;
					walletService.updateWallet(username, provider, amount);
					walletTransferService.tranferToMain(temp.getTxCode(), username, provider, amount);
					gameHistoryService.updateGameHistory(username, provider, amount,"");
					log.info(" ["+provider+"] updateCredit : == No balance for withdraw ");

					customer.setLastProvider("");
					customerService.saveCustomer(customer);
				}
				
				if (STATUS.MX_1028.equals(temp.getStatus())) {
//					customer.setLastProvider("");
//					customerService.saveCustomer(customer);
					log.info(" ["+provider+"] updateCredit : == Error : "+temp.getStatus() );
				}
			}
		}
		

		log.info(" [SEXY -->"+provider+"] updateCredit : == Ending ");
	}
}
