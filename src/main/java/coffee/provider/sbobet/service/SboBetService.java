package coffee.provider.sbobet.service;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.service.GameHistoryService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WalletTransferService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.provider.sbobet.vo.model.Error;
import coffee.provider.sbobet.vo.req.AgentRegisterRequest;
import coffee.provider.sbobet.vo.req.DepositRequest;
import coffee.provider.sbobet.vo.req.GetPlayerBalanceRequest;
import coffee.provider.sbobet.vo.req.PlayerLoginRequest;
import coffee.provider.sbobet.vo.req.PlayerLogoutRequest;
import coffee.provider.sbobet.vo.req.PlayerRegisterRequest;
import coffee.provider.sbobet.vo.req.SboBetRequest;
import coffee.provider.sbobet.vo.req.WithdrawRequest;
import coffee.provider.sbobet.vo.res.DepositResponse;
import coffee.provider.sbobet.vo.res.GetPlayerBalanceResponse;
import coffee.provider.sbobet.vo.res.LoginResponse;
import coffee.provider.sbobet.vo.res.LogoutResponse;
import coffee.provider.sbobet.vo.res.SboBetResponse;
import coffee.provider.sbobet.vo.res.WithdrawResponse;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.constant.ProjectConstant.STATUS;
import framework.utils.GenerateRandomString;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SboBetService {
	
    @Value("${path.sboBetProvider.api}")
    private String sboBetApi;

    @Value("${path.sboBetProvider.name}")
    private String sboBetProviderName;

    @Value("${path.sboBetProvider.companyKey}")
    private String sboBetProviderCompanyKey;
    
    @Autowired
	private CustomerService customerService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private WalletTransferService walletTransferService;
	
	@Autowired
	private GameHistoryService gameHistoryService;

    public SboBetResponse registerAgent(String username, String password, BigDecimal min, BigDecimal max, BigDecimal maxPerMatch, Integer tableLimit) {
        AgentRegisterRequest dataMock = new AgentRegisterRequest();
        dataMock.setCompanyKey(sboBetProviderCompanyKey);
        dataMock.setServerId("FINBETDEV");
        dataMock.setUsername(username);
        dataMock.setPassword(password);
        dataMock.setCurrency("THB");
        dataMock.setMin(min);
        dataMock.setMax(max);
        dataMock.setMaxPerMatch(maxPerMatch);
        dataMock.setCasinoTableLimit(tableLimit);

        log.info(" =:= [SBO] registerAgent =:= ");
        log.info(" =:= Infomation --> [ Request = "+dataMock+" ] =:= ");
        SboBetResponse res;
        String url = sboBetApi + "/web-root/restricted/agent/register-agent.aspx";
        String responseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
        log.info("== : [SBO] registerAgent : == ResponseString --> Code = "+responseString);

        Gson gson = new Gson();
        res = gson.fromJson(responseString, SboBetResponse.class);
        if(res.getError() != null) {
        	Error response = res.getError();
        	log.info("== : [SBO] registerAgent : == Response --> Code = "+response.getId()+" || Message = "+response.getMsg());
		}
        return res;
    }

    public SboBetResponse registerPlayer(String username, String agent) {
        PlayerRegisterRequest dataMock = new PlayerRegisterRequest();
        dataMock.setCompanyKey(sboBetProviderCompanyKey);
        dataMock.setServerId("FINBETDEV");
        dataMock.setUsername(username);
        dataMock.setAgent(agent);

        log.info(" =:= [SBO] registerPlayer =:= ");
        log.info(" =:= Infomation --> [ Request = "+dataMock+" ] =:= ");
        SboBetResponse res;
        String url = sboBetApi + "/web-root/restricted/player/register-player.aspx";
        String responseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
        log.info("== : [SBO] registerPlayer : == ResponseString --> Code = "+responseString);

        Gson gson = new Gson();
        res = gson.fromJson(responseString, SboBetResponse.class);
        if(res.getError() != null) {
        	Error response = res.getError();
        	log.info("== : [SBO] registerPlayer : == Response --> Code = "+response.getId()+" || Message = "+response.getMsg());
		}
        return res;
    }

    public LoginResponse login(String username) {
        PlayerLoginRequest dataMock = new PlayerLoginRequest();
        dataMock.setCompanyKey(sboBetProviderCompanyKey);
        dataMock.setServerId("FINBETDEV");
        dataMock.setUsername(username);
        dataMock.setPortfolio("SportsBook");

        log.info(" =:= [SBO] login =:= ");
        log.info(" =:= Infomation --> [ Request = "+dataMock+" ] =:= ");
        LoginResponse res;
        String url = sboBetApi + "/web-root/restricted/player/login.aspx";
        String responseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
        log.info("== : [SBO] login : == ResponseString --> Code = "+responseString);

        Gson gson = new Gson();
        res = gson.fromJson(responseString, LoginResponse.class);
        if(res.getError() != null) {
        	Error response = res.getError();
        	log.info("== : [SBO] login : == Response --> Code = "+response.getId()+" || Message = "+response.getMsg());
		}
        return res;
    }
    
    public LogoutResponse logout(String username) {
    	PlayerLogoutRequest dataMock = new PlayerLogoutRequest();
        dataMock.setCompanyKey(sboBetProviderCompanyKey);
        dataMock.setServerId("FINBETDEV");
        dataMock.setUsername(username);
        
        log.info(" =:= [SBO] logout =:= ");
        log.info(" =:= Infomation --> [ Request = "+dataMock+" ] =:= ");
        LogoutResponse res;
        String url = sboBetApi + "/web-root/restricted/player/logout.aspx";
        String responseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
        log.info("== : [SBO] logout : == ResponseString --> Code = "+responseString);

        Gson gson = new Gson();
        res = gson.fromJson(responseString, LogoutResponse.class);
        if(res.getError() != null) {
        	Error response = res.getError();
        	log.info("== : [SBO] logout : == Response --> Code = "+response.getId()+" || Message = "+response.getMsg());
		}
        return res;
    }

    public DepositResponse deposit(String username, BigDecimal amount,String orderId) {
        DepositRequest dataMock = new DepositRequest();
        dataMock.setCompanyKey(sboBetProviderCompanyKey);
        dataMock.setServerId("FINBETDEV");
        dataMock.setUsername(username);
        dataMock.setAmount(amount);
        dataMock.setTxnId(orderId.substring(0, 29));

        log.info(" =:= [SBO] deposit =:= ");
        log.info(" =:= Infomation --> [ Request = "+dataMock+" ] =:= ");
        DepositResponse res;
        String url = sboBetApi + "/web-root/restricted/player/deposit.aspx";
        String responseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
        log.info("== : [SBO] deposit : == ResponseString --> Code = "+responseString);
        
        Gson gson = new Gson();
        res = gson.fromJson(responseString, DepositResponse.class);
        if(res.getError() != null) {
        	Error response = res.getError();
        	log.info("== : [SBO] deposit : == Response --> Code = "+response.getId()+" || Message = "+response.getMsg());
		}
        return res;
    }

    public WithdrawResponse withdraw(String username, BigDecimal amount, Boolean isFullAmount,String orderId) {
        WithdrawRequest dataMock = new WithdrawRequest();
        dataMock.setCompanyKey(sboBetProviderCompanyKey);
        dataMock.setServerId("FINBETDEV");
        dataMock.setUsername(username);
        dataMock.setAmount(amount);
        dataMock.setTxnId(orderId);
        dataMock.setIsFullAmount(isFullAmount);

        log.info(" =:= [SBO] withdraw =:= ");
        log.info(" =:= Infomation --> [ Request = "+dataMock+" ] =:= ");
        WithdrawResponse res;
        String url = sboBetApi + "/web-root/restricted/player/withdraw.aspx";
        String responseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
        log.info("== : [SBO] withdraw : == ResponseString --> Code = "+responseString);
        
        Gson gson = new Gson();
        res = gson.fromJson(responseString, WithdrawResponse.class);
        if(res.getError() != null) {
        	Error response = res.getError();
        	log.info("== : [SBO] withdraw : == Response --> Code = "+response.getId()+" || Message = "+response.getMsg());
		}
        return res;
    }
    
    public GetPlayerBalanceResponse getPlayerBalance(String username) {
    	GetPlayerBalanceRequest dataMock = new GetPlayerBalanceRequest();
        dataMock.setCompanyKey(sboBetProviderCompanyKey);
        dataMock.setServerId("FINBETDEV");
        dataMock.setUsername(username);

        log.info(" =:= [SBO] getPlayerBalance =:= ");
        log.info(" =:= Infomation --> [ Request = "+dataMock+" ] =:= ");
        GetPlayerBalanceResponse res;
        String url = sboBetApi + "/web-root/restricted/player/get-player-balance.aspx";
        String responseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
        log.info("== : [SBO] getPlayerBalance : == ResponseString --> Code = "+responseString);

        Gson gson = new Gson();
        res = gson.fromJson(responseString, GetPlayerBalanceResponse.class);
        if(res.getError() != null) {
        	Error response = res.getError();
        	log.info("== : [SBO] getPlayerBalance : == Response --> Code = "+response.getId()+" || Message = "+response.getMsg());
        	if(STATUS.SBO_0 == response.getId()) {
        		log.info("== : [SBO] getPlayerBalance : == Available Balance  = "+res.getBalance()+" || Outstanding = "+res.getOutstanding());
        	}
		}
        return res;
    }

    public String playGame(SboBetRequest req) {
    	log.info(" [SBO] playGame : == On start --> ");
    	LoginResponse loginResponse = login(req.getUsername());
    	String url = "";
    	if(loginResponse.getError().getId() == 0) {
    		DepositResponse deposit = deposit(req.getUsername(),req.getBalance(),req.getOrderId());
    		if(deposit.getError().getId() == 0) {
    			log.info(" =:= Infomation --> [ username ="+req.getUsername()+" ,amount ="+req.getBalance()+", orderId ="+req.getOrderId()+"] =:= ");
        		url = "https:"+loginResponse.getUrl() + "&lang=en&oddstyle=MY&theme=sbo&oddsmode=double&device=d";
        		walletTransferService.tranferFromMain(req.getOrderId(), req.getUsername(), PROVIDERS.SBO, req.getBalance());
    		
    		}else {
    			log.info(" [SBO] playGame : == Deposit Error --> "+deposit.getError().getId());
    		}
    	}else {
    		log.info(" [SBO] playGame : == Login Error --> "+loginResponse.getError().getId());
    	}
    	log.info(" [SBO] playGame : == Ending ");
        return url;
    }
    
    public void updateCredit(Customer customer,String orderId) {
    	log.info(" [SBO] updateCredit : == On start --> ");
    	
		if (StringUtils.isNoneBlank(customer.getLastProvider())) {
			String username = customer.getUsername();
			log.info(" =:= Infomation --> [ username ="+username+", orderId In ="+orderId+"] =:= ");

			logout(username);
			GetPlayerBalanceResponse playerBalance =  getPlayerBalance(username);
			BigDecimal balance = playerBalance.getBalance().subtract(playerBalance.getOutstanding());
			if(playerBalance.getError() != null) {
				if(STATUS.SBO_0 == playerBalance.getError().getId()) {
					if(balance.compareTo(BigDecimal.ZERO) > 0) {
						WithdrawResponse temp = withdraw(username,balance,false,orderId.substring(0, 29));
						if (temp != null) {
							if(temp.getError() != null) {
								if (STATUS.SBO_0 == temp.getError().getId()) {
									
									BigDecimal amount = temp.getAmount();
									log.info(" =:= Infomation --> [ amount ="+amount+", orderId Out ="+temp.getTxnId()+"] =:= ");
									walletService.updateWallet(username, PROVIDERS.SBO, amount);
									walletTransferService.tranferToMain(temp.getTxnId(), username, PROVIDERS.SBO, amount);
									
									gameHistoryService.updateGameHistory(username, PROVIDERS.SBO, amount,"ออกจากหน้า Lobby SBO","SportsBook");
									
									customer.setLastProvider("");
									customerService.saveCustomer(customer);
								}
							}
						}else {
							log.info(" =:= withdraw --> null ");
						}
					}else {
						log.info(" =:= Infomation --> [ amount ="+BigDecimal.ZERO+", orderId In ="+orderId+"] =:= ");
						walletService.updateWallet(username, PROVIDERS.SBO, BigDecimal.ZERO);
						walletTransferService.tranferToMain(orderId, username, PROVIDERS.SBO, BigDecimal.ZERO);
						
						gameHistoryService.updateGameHistory(username, PROVIDERS.SBO, BigDecimal.ZERO,"ออกจากหน้า Lobby SBO","SportsBook");
						
						customer.setLastProvider("");
						customerService.saveCustomer(customer);
					}
					
				}
			}
		}
		
		log.info(" [SBO] updateCredit : == Ending ");
	}
    
    public void updateCredit(String username,String orderId,BigDecimal winPrize) {
    	log.info(" [SBO] updateCredit Special : == On start --> ");
    	
		logout(username);
		GetPlayerBalanceResponse playerBalance =  getPlayerBalance(username);
		BigDecimal balance = playerBalance.getBalance().subtract(playerBalance.getOutstanding());
		if(playerBalance.getError() != null) {
			if(STATUS.SBO_0 == playerBalance.getError().getId()) {
				if(balance.compareTo(winPrize) >= 0) {
					WithdrawResponse temp = withdraw(username,winPrize,false,orderId.substring(0, 29));
					if (temp != null) {
						log.info(" [SBO] updateCredit Special : win less than credit => Allow withdraw ");
						if(temp.getError() != null) {
							if (STATUS.SBO_0 == temp.getError().getId()) {
								
								BigDecimal amount = temp.getAmount();
								log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+", orderId ="+temp.getTxnId()+"] =:= ");
								walletService.addBalance(username, amount);
//								walletTransferService.tranferToMain(temp.getTxnId(), username, PROVIDERS.SBO, amount);
							}
						}
					}else {
						log.info(" [SBO] updateCredit Special : win greated than credit => Not withdraw");
					}
				}
			}
		}
		
		log.info(" [SBO] updateCredit Special : == Ending ");
	}


}
