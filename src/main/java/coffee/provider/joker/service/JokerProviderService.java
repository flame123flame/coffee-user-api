package coffee.provider.joker.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.SortedMap;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import coffee.backoffice.finance.service.GameHistoryService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WalletTransferService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.provider.joker.jpa.JokerOutstandingRepository;
import coffee.provider.joker.vo.model.JokerCreateUser;
import coffee.provider.joker.vo.model.JokerGamesList;
import coffee.provider.joker.vo.model.JokerGetCredit;
import coffee.provider.joker.vo.model.JokerOutstanding;
import coffee.provider.joker.vo.model.JokerPlayGame;
import coffee.provider.joker.vo.model.JokerTranferCredit;
import coffee.provider.joker.vo.req.CreateUserRequest;
import coffee.provider.joker.vo.req.GameListRequest;
import coffee.provider.joker.vo.req.GetCreditRequest;
import coffee.provider.joker.vo.req.PlayGameRequest;
import coffee.provider.joker.vo.req.TranferCreditRequest;
import coffee.provider.joker.vo.res.GenarateSignatureRes;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.utils.GenerateRandomString;
import framework.utils.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JokerProviderService {

	@Value("${path.jokerProvider.api}")
	private String configPathJocker;
	
	@Value("${path.jokerProvider.apiGame}")
	private String jokerPathGame;
	
	@Value("${path.jokerProvider.homePath}")
	private String jokerHomePath;

	@Value("${path.jokerProvider.name}")
	private String jokerProviderName;

	@Value("${path.jokerProvider.appid}")
	private String jokerProviderAppid;
	
	@Value("${path.jokerProvider.secretKey}")
	private String secretKeyJoker;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private WalletTransferService walletTransferService;
	
	@Autowired
	private GameHistoryService gameHistoryService;
	
	@Autowired
	private JokerOutstandingRepository jokerOutstandingRepository;

	public GenarateSignatureRes genarateSignature(Object req) {
		Long timeStampUnix = Instant.now().getEpochSecond();

		ObjectMapper oMapper = new ObjectMapper();

		/**
		 * Object "Must" String Only
		 */
		@SuppressWarnings("unchecked")
		SortedMap<String, String> data = oMapper.convertValue(req, SortedMap.class);
		data.put("Timestamp", String.valueOf(timeStampUnix));
		String rawData = data.entrySet().stream().map(Object::toString).collect(Collectors.joining("&"));
		System.out.println(data);

		/**
		 * Just Hard Code secretKey
		 */
//		String secretKeyJoker = "s5gawk7t5tf8r";
		String signature = null;
		String containSignature = null;

		try {
			signature = getHMACSHA1Signature(rawData, secretKeyJoker);
			containSignature = URLEncoder.encode(signature, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {
			log.info("Error Genarate Signature => ", e);
		}

		GenarateSignatureRes dataRes = new GenarateSignatureRes(containSignature, timeStampUnix);
		return dataRes;
	}

	private String getHMACSHA1Signature(String rawData, String secretKey) throws Exception {
		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signingKey);
		byte[] hashedValue = mac.doFinal(rawData.getBytes());
		return Base64.getEncoder().encodeToString(hashedValue);
	}

	public Boolean checkSignature(Object req, String signature, Long time) {
		return signature.equals(genarateSignature(req, time).getSignature());
	}

	private GenarateSignatureRes genarateSignature(Object req, Long time) {
		ObjectMapper oMapper = new ObjectMapper();

		/**
		 * Object "Must" String Only
		 */
		@SuppressWarnings("unchecked")
		SortedMap<String, Object> data = oMapper.convertValue(req, SortedMap.class);
		data.put("Timestamp", String.valueOf(time));
		String rawData = data.entrySet().stream().map(Object::toString).collect(Collectors.joining("&"));
		System.out.println(data);

		/**
		 * Just Hard Code secretKey
		 */
		String secretKeyJoker = "s5gawk7t5tf8r";
		String signature = null;

		try {
			signature = getHMACSHA1Signature(rawData, secretKeyJoker);
		} catch (Exception e) {
			log.info("Error Genarate Signature => ", e);
		}

		GenarateSignatureRes dataRes = new GenarateSignatureRes(signature, time);
		return dataRes;
	}
	
	public JokerGamesList listGames() {
		GameListRequest dataMock = new GameListRequest(
				ProjectConstant.JOKER_PROVIDER_METHOD.LIST_GAMES);
		JokerGamesList res = new JokerGamesList();
		int round = 0;
		while(res.getListGames() == null && round < 10) {
			round++;
			GenarateSignatureRes sigKeyRes = genarateSignature(dataMock);
			dataMock.setTimestamp(sigKeyRes.getTimestampUnix());
			String url = configPathJocker + "/?appid=" + jokerProviderAppid + "&signature=" + sigKeyRes.getSignature();
			String reponseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
	
			Gson gson = new Gson();
			res = gson.fromJson(reponseString, JokerGamesList.class);
			
			if(res.getMessage() != null) {
				log.info("== : Joker listGames : == Response -->" + res.getMessage() );
			}
		}
		return res;
	}

	public JokerPlayGame login(String username) {
		PlayGameRequest dataMock = new PlayGameRequest(
				ProjectConstant.JOKER_PROVIDER_METHOD.PLAY, username);
		JokerPlayGame res = new JokerPlayGame();
		int round = 0;
		while(res.getToken() == null && round < 10) {
			round++;
			GenarateSignatureRes sigKeyRes = genarateSignature(dataMock);
			dataMock.setTimestamp(sigKeyRes.getTimestampUnix());
			String url = configPathJocker + "/?appid=" + jokerProviderAppid + "&signature=" + sigKeyRes.getSignature();
			String reponseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);

			Gson gson = new Gson();
			res = gson.fromJson(reponseString, JokerPlayGame.class);
			
			if(res.getMessage() != null) {
				log.info("== : Joker login [Username :"+username+"] : == Response -->" + res.getMessage() );
			}
		}
		
		return res;
	}

	public JokerTranferCredit tranferCredit(String username, String amount, String orderId) {
		TranferCreditRequest dataMock = new TranferCreditRequest(
				ProjectConstant.JOKER_PROVIDER_METHOD.TRANFER_CREDIT, username, amount, orderId);
		JokerTranferCredit res = new JokerTranferCredit();
		int round = 0;
		while(res.getCredit() == null && round < 10) {
			round++;
			GenarateSignatureRes sigKeyRes = genarateSignature(dataMock);
			dataMock.setTimestamp(sigKeyRes.getTimestampUnix());
			String url = configPathJocker + "/?appid=" + jokerProviderAppid + "&signature=" + sigKeyRes.getSignature();
			String reponseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
	
			Gson gson = new Gson();
			res = gson.fromJson(reponseString, JokerTranferCredit.class);
			
			if(res.getMessage() != null) {
				log.info("== : Joker tranferCredit [Username :"+username+"] : == Response -->" + res.getMessage() );
			}
		}
		return res;
	}

	public JokerGetCredit getCredit(String username) {
		GetCreditRequest dataMock = new GetCreditRequest(
				ProjectConstant.JOKER_PROVIDER_METHOD.GET_CREDIT, username);
		JokerGetCredit res = new JokerGetCredit();
		int round = 0;
//		while(res.getCredit() == null && round < 10) {
		while(res.getCredit() == null) {
			round++;
			log.info("== : Joker getCredit [round :"+round+"]");
			GenarateSignatureRes sigKeyRes = genarateSignature(dataMock);
			dataMock.setTimestamp(sigKeyRes.getTimestampUnix());
			String url = configPathJocker + "/?appid=" + jokerProviderAppid + "&signature=" + sigKeyRes.getSignature();
			String reponseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
	
			log.info("== : Joker getCredit [reponseString :"+reponseString+"]");
			Gson gson = new Gson();
			res = gson.fromJson(reponseString, JokerGetCredit.class);
			
			if(res.getMessage() != null) {
				log.info("== : Joker getCredit [Username :"+username+"] : == Response -->" + res.getMessage() );
			}
		}
		return res;
	}
	
	public JokerCreateUser createUser(String username) {
		CreateUserRequest dataMock = new CreateUserRequest(
				ProjectConstant.JOKER_PROVIDER_METHOD.CREATE_USER, username);
		JokerCreateUser res = new JokerCreateUser();
		int round = 0;
		while(res.getStatus() == null && round < 10) {
			round++;
			GenarateSignatureRes sigKeyRes = genarateSignature(dataMock);
			dataMock.setTimestamp(sigKeyRes.getTimestampUnix());
			String url = configPathJocker + "/?appid=" + jokerProviderAppid + "&signature=" + sigKeyRes.getSignature();
			String reponseString = OkHttpClientUtils.doPost(url, dataMock, MediaType.APPLICATION_JSON_VALUE);
	
			Gson gson = new Gson();
			res = gson.fromJson(reponseString, JokerCreateUser.class);
			
			if(res.getMessage() != null) {
				log.info("== : Joker createUser [Username :"+username+"] : == Response -->" + res.getMessage() );
			}
		}
		return res;
	}
	
	public String playGame(String username,String gameCode, String amount, String orderId) {
		log.info(" [JOKER] playGame : == On start --> ");
		String url = "";
		JokerPlayGame resLogin = login(username);
		if(resLogin.getToken() != null) {
			JokerGetCredit credit = getCredit(username);
			if(credit.getCredit() != null) {
				if(credit.getCredit().compareTo(BigDecimal.ZERO) == 0) {
					JokerTranferCredit tranfer = tranferCredit(username, amount.toString(),orderId);
					BigDecimal amountDecimal = new BigDecimal(amount);
					log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+", orderId ="+orderId+"] =:= ");
		    		walletTransferService.tranferFromMain(orderId, username, PROVIDERS.JOKER, amountDecimal);
				}
			}
			url = jokerPathGame + "/?token=" + resLogin.getToken() + "&game=" + gameCode
			+ "&redirectUrl="+jokerHomePath+"&mobile=true&lang=en";
			
		}
		log.info(" [JOKER] playGame : == Ending ");
		return url;
	}
	
	public Boolean updateCredit(Customer customer, String orderId) {
		log.info(" [JOKER] updateCredit : == On start --> ");
		  
		if(StringUtils.isNoneBlank(customer.getLastProvider())) {
			String username = customer.getUsername();
			
			JokerGetCredit credit = getCredit(username);
			if(credit.getCredit() != null) {
				
				JokerOutstanding joker = jokerOutstandingRepository.findByUsername(username);
				if(joker == null) {
					joker = new JokerOutstanding();
					joker.setUsername(username);
					if(credit.getOutstandingCredit().compareTo(BigDecimal.ZERO) > 0) {
						joker.setOutstandingAmount(credit.getOutstandingCredit());
						joker.setCheckOutstanding(true);
					}else {
						joker.setCheckOutstanding(false);
					}
					jokerOutstandingRepository.save(joker);
					
					if(credit.getCredit().compareTo(BigDecimal.ZERO) >= 0) {
						BigDecimal amount = credit.getCredit();

						log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+", orderId ="+orderId+"] =:= ");

						tranferCredit(customer.getUsername(), "-"+amount,orderId);
						walletService.updateWallet(username,PROVIDERS.JOKER, credit.getCredit());
						walletTransferService.tranferToMain(orderId, username, PROVIDERS.JOKER, amount);
						gameHistoryService.updateGameHistory(username,PROVIDERS.JOKER, amount,"");
						
						customer.setLastProvider("");
						customerService.saveCustomer(customer);
					}
					
				}else {
					if(joker.getCheckOutstanding() && credit.getOutstandingCredit().compareTo(joker.getOutstandingAmount()) != 0) {
						BigDecimal amount = credit.getCredit();
						tranferCredit(customer.getUsername(), "-"+amount,orderId);
						walletService.addBalance(username, amount);
					}else {
						if(credit.getCredit().compareTo(BigDecimal.ZERO) >= 0) {
							BigDecimal amount = credit.getCredit();

							log.info(" =:= Infomation --> [ username ="+username+" ,amount ="+amount+", orderId ="+orderId+"] =:= ");

							tranferCredit(customer.getUsername(), "-"+amount,orderId);
							walletService.updateWallet(username,PROVIDERS.JOKER, credit.getCredit());
							walletTransferService.tranferToMain(orderId, username, PROVIDERS.JOKER, amount);
							gameHistoryService.updateGameHistory(username,PROVIDERS.JOKER, amount,"");
							
							customer.setLastProvider("");
							customerService.saveCustomer(customer);
						}
					}
				}
			}
		}
		
		log.info(" [JOKER] updateCredit : == Ending ");
		return true;
		

	}
}
