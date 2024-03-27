package coffee.backoffice.lotto.service;

import coffee.backoffice.affiliate.service.AffiliateService;
import coffee.backoffice.casino.model.*;
import coffee.backoffice.casino.repository.jpa.GameProductTypeJpa;
import coffee.backoffice.casino.repository.jpa.GameProviderNoIconJpa;
import coffee.backoffice.casino.repository.jpa.GamesJpa;
import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.repository.jpa.TransactionGameRepository;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.lotto.model.LottoHistory;
import coffee.backoffice.lotto.repository.jpa.LottoHistoryRepository;
import coffee.backoffice.lotto.vo.req.LottoBuyReq;
import coffee.backoffice.lotto.vo.res.BuyLottoStatusRes;
import coffee.backoffice.lotto.vo.res.LottoSyncRes;
import coffee.backoffice.lotto.vo.res.SendBuyLottoRes;
import coffee.backoffice.lotto.vo.res.SendRefundLottoRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.player.repository.jpa.GroupLevelRepository;
import coffee.backoffice.promotion.repository.jpa.IssueSettingRepository;
import coffee.backoffice.promotion.repository.jpa.PromotionMappingRepository;
import coffee.backoffice.rebate.service.RebateService;
import framework.constant.ProjectConstant;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import coffee.backoffice.lotto.vo.req.LottoTimeDetailReq;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

import javax.net.ssl.SSLContext;
import javax.transaction.Transactional;

@Service
@Slf4j
public class LottoService {

    @Value("${path.lottoProvider.name}")
    String lottoProviderName;

    @Autowired
    WalletService walletService;

    @Autowired
    LottoHistoryRepository lottoHistoryRepository;

    @Autowired
    TransactionGameRepository transactionGameRepository;

    @Autowired
    GroupLevelRepository groupLevelRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    GameProviderNoIconJpa gameProviderNoIconJpa;

    @Autowired
    RebateService rebateService;
    @Autowired
    AffiliateService affiliateService;

    @Autowired
    GameProductTypeJpa gameProductTypeJpa;

    @Autowired
    GamesJpa gamesJpa;

    @Autowired
    PromotionMappingRepository promotionMappingRepository;
    @Autowired
    IssueSettingRepository issueSettingRepository;

    @Value("${path.lottoProvider.api}")
    private String lottoProvider;

    public void addConfigLottoTime(LottoTimeDetailReq req) {

    }

    @Transactional
    public BuyLottoStatusRes filterAndCreateTransaction(LottoBuyReq req) throws Exception {
        log.info("LOTTO_SERVICE : start buy lotto");
        BuyLottoStatusRes res = new BuyLottoStatusRes();
        log.info("LOTTO_SERVICE : check buy able");
        BigDecimal allBuyCost = new BigDecimal(0);
        for (LottoBuyReq.PayNumber item : req.getPayNumber()) {
            for (LottoBuyReq.LottoBuy buy : item.getLottoBuy()) {
                allBuyCost = allBuyCost.add(buy.getPayCost());
            }
        }

        Boolean userBonus = walletService.checkUseBonus(req.getUsername(), lottoProvider);
        if (userBonus) {
            if (allBuyCost.compareTo(walletService.findBalanceWithBonus(UserLoginUtil.getUsername())) > 0) {
                res.setOutOffBalance(true);
                return res;
            }
        } else {
            if (allBuyCost.compareTo(walletService.findBalanceWithoutBonus(UserLoginUtil.getUsername())) > 0) {
                res.setOutOffBalance(true);
                return res;
            }
        }


        log.info("LOTTO_SERVICE : set request header");

        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        String url = lottoProvider + "/api/buy";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + UserLoginUtil.getToken());
        // prepare entity req
        LottoBuyReq lottoBuyReq = req;
        // get commission rate
        Customer customer = customerRepository.findByUsername(UserLoginUtil.getUsername());
        GameProviderNoIcon gameProvider = gameProviderNoIconJpa.findByCode(this.lottoProviderName);
        BigDecimal commissionPercent = getCommissionRate(UserLoginUtil.getUsername(), gameProvider.getCode(),
                gameProvider.getNameEn(), customer.getGroupCode());
        lottoBuyReq.setCommissionPercent(commissionPercent);
        HttpEntity<LottoBuyReq> reqEntity = new HttpEntity<LottoBuyReq>(req, headers);

        log.info("LOTTO_SERVICE : send buy request to lotto provider");
        String installment = "";
        ResponseEntity<SendBuyLottoRes> result = null;
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, reqEntity, SendBuyLottoRes.class);
        } catch (Exception e) {
            throw new Exception("REQ_FAIL");
        }
        if (result.getBody() == null) {
            log.info("RESULT BODY IS NULL EXIST");
            res.setMessage("REQ_FAIL");
            res.setServerBuyFail(true);
            return res;
        }
        log.info("respond is null" + result.toString());
        if (!"SUCCESS".equals(result.getBody().getStatus())) {
            log.info("REQ_FAIL EXIST");
            res.setMessage("REQ_FAIL");
            res.setServerBuyFail(true);
            return res;
        }

        if (result.getBody().getData() != null && result.getBody().getData().getSuccessData() != null) {
            log.info("GET SUCCESS DATA");
            res.setSuccessData(result.getBody().getData().getSuccessData());
            installment = result.getBody().getData().getInstallment();
        }
        // remove balance
        if (result.getBody().getData() != null && result.getBody().getData().getError() != null) {
            log.info("GET ERROR EXIST");
            res.setMessage(result.getBody().getData().getStatus());
            res.setError(result.getBody().getData().getError());
            res.setServerBuyFail(true);
            return res;
        }
        if (!"SUCCESS".equals(result.getBody().getData().getStatus())){
            res.setMessage(result.getBody().getData().getStatus());
            return res;
        }
        log.info("LOTTO_SERVICE : send request success reduce balance");
        walletService.updateWalletLotto(UserLoginUtil.getUsername(), lottoProvider, allBuyCost);

        log.info("LOTTO_SERVICE : save transaction");
        String transactionGroupCode = result.getBody().getData().getCode();
        // Save game_transaction
        TransactionGame transactionGame = new TransactionGame();
        transactionGame.setGameProvider(this.lottoProviderName);
        transactionGame.setGameCode(req.getLottoClassCode());
        transactionGame.setBet(allBuyCost);
        transactionGame.setBetResult(BigDecimal.valueOf(0));
        transactionGame.setGameSessionId(transactionGroupCode);
        transactionGame.setBalance(walletService.findBalanceWithBonus(UserLoginUtil.getUsername()));
        transactionGame.setUsername(UserLoginUtil.getUsername());
        transactionGame.setCreatedBy("_system");
        transactionGame.setGameResult(ProjectConstant.STATUS.PENDING);
        if(req.getRoundYeeKee()!=null){
        	transactionGame.setRound(Long.valueOf(req.getRoundYeeKee()));
        }
        if(installment!=null){
        	transactionGame.setInstallment(installment);
        }
        transactionGameRepository.save(transactionGame);
        // Save lotto_transaction
        for (LottoBuyReq.PayNumber item : req.getPayNumber()) {
            for (LottoBuyReq.LottoBuy buy : item.getLottoBuy()) {
                LottoHistory newTransaction = new LottoHistory();
                newTransaction.setLottoHistoryCode(GenerateRandomString.generateUUID());
                newTransaction.setCost(buy.getPayCost());
                newTransaction.setLottoClassCode(req.getLottoClassCode());
                newTransaction.setLottoKindCode(item.getLottoKindCode());
                newTransaction.setNumber(buy.getLottoNumber());
                newTransaction.setPrize(buy.getPrize());
                newTransaction.setCreatedBy(UserLoginUtil.getUsername());
                newTransaction.setCreatedDate(new Date());
                newTransaction.setUpdatedDate(new Date());
                newTransaction.setUpdatedBy(UserLoginUtil.getUsername());
                newTransaction.setTransactionGroupCode(transactionGroupCode);
                newTransaction.setUsername(UserLoginUtil.getUsername());
                lottoHistoryRepository.save(newTransaction);
            }
        }
        log.info("LOTTO_SERVICE : buy lotto end");
        res.setLottoTransactionGroupCode(transactionGroupCode);
        res.setSuccess(true);
        return res;
    }

    @Transactional
    public String refundLotto(String groupTransCode, String classCode, String roundYeekee) throws Exception {
        log.info("LOTTO_SERVICE : set request header");
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + UserLoginUtil.getToken());
        HttpEntity<LottoBuyReq> reqEntity = new HttpEntity<LottoBuyReq>(null, headers);
        System.out.println(
                lottoProvider + "/api/transaction-group/get-refund-lotto-list/" + groupTransCode + "/" + classCode);
        String url = lottoProvider + "/api/transaction-group/get-refund-lotto-list/" + groupTransCode + "/" + classCode;
        if (roundYeekee != null) {
            url = url + "?roundYeekee=" + roundYeekee;
        }
        log.info("LOTTO_SERVICE : send refund request to lotto provider");

        ResponseEntity<SendRefundLottoRes> result;
        try {
            result = restTemplate.exchange(url, HttpMethod.GET, reqEntity,
                    SendRefundLottoRes.class);
        } catch (Exception e) {
            throw new Exception("REQ_FAIL");
        }

        if (result.getBody() == null) {
            log.info("RESULT BODY IS NULL EXIST");
            throw new Exception("REQ_FAIL");
        }

        if (!"SUCCESS".equals(result.getBody().getStatus())) {
            return result.getBody().getData().getStatus();
        }
        log.info("LOTTO_SERVICE : send request success");
        if (result.getBody().getData().getStatus().equals("SUCCESS")) {
            walletService.addBalance(UserLoginUtil.getUsername(), result.getBody().getData().getMoney());

            // Delete game_transaction
            TransactionGame transactionGame = transactionGameRepository.findByGameSessionId(groupTransCode);
            transactionGame.setGameResult("REFUND");
            transactionGameRepository.save(transactionGame);
            log.info("LOTTO_SERVICE : refund lotto end");
            return result.getBody().getData().getStatus();
        }
        return result.getBody().getData().getStatus();
    }

    public List<LottoHistory> getByUsername(String username) {
        return lottoHistoryRepository.findAllByUsername(username);
    }

    public List<LottoHistory> getAll() {
        return lottoHistoryRepository.findAll();
    }

    private BigDecimal getCommissionRate(String username, String providerCode, String productTypeCode, String groupCode) {
        BigDecimal rebate = rebateService.getCommissionPercentByGroupCodeAndProviderCode(groupCode, providerCode);
        BigDecimal aff = affiliateService.getCommissionPercentFromUsernameAndProductTypeCode(username, productTypeCode);
        if (rebate.compareTo(aff) == 1) {
            return rebate;
        }
        return aff;
    }

    @Transactional
    public void syncLotto() throws Exception {
//     /api/lotto-class/get-all-lotto-class
        log.info("LOTTO_SERVICE : set request header");
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + UserLoginUtil.getToken());
        HttpEntity<LottoBuyReq> reqEntity = new HttpEntity<LottoBuyReq>(null, headers);
        String url = lottoProvider + "/api/lotto-class/get-all-lotto-class";
        log.info("LOTTO_SERVICE : send sync req");

        ResponseEntity<LottoSyncRes> result;
        try {
            result = restTemplate.exchange(url, HttpMethod.GET, reqEntity,
                    LottoSyncRes.class);
        } catch (Exception e) {
            throw new Exception("REQ_FAIL");
        }
        if (result.getBody() == null)
            return;
        List<LottoSyncRes.BoSyncRes> boSyncRes = result.getBody().getData();
        for (LottoSyncRes.BoSyncRes item : boSyncRes
        ) {
            Games games = new Games();
            games.setNameTh(item.getClassName());
            games.setCreatedAt(new Date());
            games.setNameEn(item.getClassName());
            games.setGameProductTypeCode(lottoProviderName);
            games.setDisplayName(item.getClassName());
            games.setStatus(true);
            games.setGameCode(item.getLottoClassCode());
            games.setEnable(true);
            games.setProviderCode(lottoProviderName);
            games.setGameGroupCode(lottoProviderName);
            gamesJpa.save(games);
        }
    }

}
