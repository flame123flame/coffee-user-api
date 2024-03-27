package coffee.backoffice.rebate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import coffee.backoffice.cashback.model.Cashback;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.rebate.model.RebateBatchWaiting;
import coffee.backoffice.rebate.repository.dao.RebateBatchWaitingDao;
import coffee.backoffice.rebate.repository.jpa.RebateBatchWaitingJpa;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.GamesNoIcon;
import coffee.backoffice.casino.repository.jpa.GamesNoIconJpa;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.repository.jpa.TransactionGameRepository;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.dao.CustomerDao;
import coffee.backoffice.player.service.BonusInformationService;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.service.PromotionService;
import coffee.backoffice.rebate.constant.RebateConstant;
import coffee.backoffice.rebate.model.Rebate;
import coffee.backoffice.rebate.model.RebateCondition;
import coffee.backoffice.rebate.model.RebateHistory;
import coffee.backoffice.rebate.repository.jpa.RebateConditionJpa;
import coffee.backoffice.rebate.repository.jpa.RebateHistoryRepository;
import coffee.backoffice.rebate.repository.jpa.RebateJpa;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.STATUS;
import framework.utils.GenerateRandomString;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Service
@Slf4j
public class CalculateRebateService {

    @Autowired
    private RebateJpa rebateJpa;

    @Autowired
    private RebateConditionJpa rebateConditionJpa;

    @Autowired
    private GamesNoIconJpa gamesNoIconJpa;

    @Autowired
    private TransactionGameRepository transactionGameRepository;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private WalletService walletService;
    @Autowired
    private BonusInformationService bonusInformationService;

    @Autowired
    private RebateHistoryRepository historyRepository;

    @Autowired
    private AllTransactionService allTransactionService;

    @Autowired
    private ProviderSummaryService providerSummaryService;

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private WithdrawConditionService withdrawConditionService;
    @Autowired
    private RebateBatchWaitingJpa rebateBatchWaitingJpa;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RebateBatchWaitingDao rebateBatchWaitingDao;


    public DataTableResponse<RebateBatchWaiting> getPendingPaginate(DatatableRequest req) {
        DataTableResponse<RebateBatchWaiting> dataTable = new DataTableResponse<>();
        DataTableResponse<RebateBatchWaiting> newData = new DataTableResponse<>();
        newData = rebateBatchWaitingDao.paginate(req);
        dataTable.setRecordsTotal(newData.getRecordsTotal());
        dataTable.setDraw(newData.getDraw());
        dataTable.setData(newData.getData());
        return dataTable;
    }

    @Transactional
    public void findRebateDaily(Boolean isTest) {
        log.info("[================ Start Rebate ==============]");
        Date current = new Date();
        List<Rebate> dataRebate = rebateJpa.findAllMustDoRebate(new Date());
        log.info("Count Task : " + dataRebate.size());
        for (Rebate rb : dataRebate) {
            findCondition(rb, current);
//            SET NEXT JOB DATE
            Date date = getNextBatchJobDate(rb);
            if (isTest) {
                rb.setNextBatchJobDate(current);
            } else {
                rb.setNextBatchJobDate(date);
            }
            rb.setStartBatchJobDate(current);
            rebateJpa.save(rb);
        }
        log.info("[================ End Rebate ==============]");
    }

    @Transactional
    public void findCondition(Rebate rb, Date current) {
        List<RebateCondition> dataListRBCD = rebateConditionJpa.findAllByRebateCode(rb.getCode());
        calculateRebate(rb, dataListRBCD, current);

    }

    @Transactional
    public void calculateRebate(Rebate rb, List<RebateCondition> dataRBCD, Date current) {
//        CHECK HAVE USER THAT CAN GET REBATE
        List<String> vipGroupCodes = new ArrayList<String>(Arrays.asList(rb.getVipGroupCode().split(",")));
        List<String> excludedTag = new ArrayList<String>(Arrays.asList(rb.getTagCode().split(",")));
        if (excludedTag.get(0).equals("null")) {
            excludedTag = new ArrayList<>();
        }
        log.info("vipGroupCodes : " + vipGroupCodes.toString());
        log.info("excludedTag : " + excludedTag.toString());

        List<Customer> tempCM = customerDao.findAllFindByGroupCodeInAndNotInExcludedTag(vipGroupCodes, excludedTag);
        log.info("GET ALL CUSTOMER THAT CAN GET REBATE");
        log.info("customer count: " + tempCM.size());

        if (tempCM.size() == 0)
            return;

//        GET GAMES FOR GET REBATE
        List<String> excluedGame = new ArrayList<String>(Arrays.asList(rb.getGamesCodeExclude().split(",")));

        for (RebateCondition item : dataRBCD) {
            List<GamesNoIcon> gamesNoIcons = gamesNoIconJpa.findAllByProviderCodeAndGameGroupCodeAndGameCodeNotIn(
                    item.getGameProviderCode(), item.getGameGroupCode(), excluedGame);
            log.info("GET ALL GAMES THAT CAN GET REBATE");
            log.info("games count: " + gamesNoIcons.size());
            log.info("provider code: " + item.getGameProviderCode());
            log.info("group code: " + item.getGameGroupCode());
            List<String> gamesCodes = new ArrayList<>();
            if (gamesNoIcons.size() != 0) {
                gamesCodes = gamesNoIcons.stream().map(GamesNoIcon::getGameCode).collect(Collectors.toList());
            }
            // DO CALCULATE
            for (Customer customer : tempCM) {
                List<TransactionGame> transactionGames;
//              GET TRANSACTION GAME
                if (rb.getStartBatchJobDate() == null)
                    transactionGames = transactionGameRepository.findByUsernameAndGameCodeInAndCreatedDateBetween(
                            customer.getUsername(), gamesCodes, rb.getStartDate(), current);
                else
                    transactionGames = transactionGameRepository.findByUsernameAndGameCodeInAndCreatedDateBetween(
                            customer.getUsername(), gamesCodes, rb.getStartBatchJobDate(), current);
                log.info("GET ALL TRANSACTION FROM USER");
                log.info("username: " + customer.getUsername());
                if (rb.getStartBatchJobDate() == null)
                    log.info("from: " + rb.getStartDate().toString());
                else
                    log.info("from: " + rb.getStartBatchJobDate().toString());
                log.info("to: " + current.toString());
                log.info("transactionGames count: " + transactionGames.size());
//            	SUM
                BigDecimal totalBet = BigDecimal.ZERO;
                for (TransactionGame i : transactionGames) {
                    if (i.getValidBet() != null)
                        totalBet = totalBet.add(i.getValidBet());
                }
                PromotionMapping usernameActive = promotionService.findByUsernameAndStatus(customer.getUsername(),
                        STATUS.ACTIVE);
                log.info("CHECK USERNAME IN ACTIVE PROMOTION");
                if (usernameActive == null) {
                    log.info("TOTAL VALID BET");
                    log.info("total valid bet: " + totalBet);
                    log.info("CHECK FOR ADD WALLET");
                    if (totalBet.compareTo(BigDecimal.ZERO) > 0) {
                        RebateBatchWaiting rebateBatchWaiting = new RebateBatchWaiting();
                        rebateBatchWaiting.setAmount(totalBet);
                        rebateBatchWaiting.setRebateCode(item.getCode());
                        rebateBatchWaiting.setRebateConditionCode(rb.getCode());
                        rebateBatchWaiting.setUsername(customer.getUsername());
                        rebateBatchWaiting.setIsAuto(rb.getIsAutoRebate());
                        rebateBatchWaiting.setRebateTitle(rb.getTitle());
                        rebateBatchWaiting.setConditionMultiple(rb.getRebateConditionMultiplier());
                        rebateBatchWaiting.setDatePeriodType(rb.getPeriodStatus());
                        rebateBatchWaiting.setStatus("WAITING");
                        rebateBatchWaiting.setReceiveDate(getNextBatchJobDate(rb));
                        rebateBatchWaitingJpa.save(rebateBatchWaiting);
                    }
                } else {
                    log.info("USERNAME: " + customer.getUsername() + " ACTIVE PROMOTION WILL NOT RUN REBATE.");
                }
            }
        }

    }

    @Transactional
    public void approveRebateByCode(String code) {
        RebateBatchWaiting rebateBatchWaitings = rebateBatchWaitingJpa.findByCode(code);
        Rebate rebate = rebateJpa.findByCode(rebateBatchWaitings.getRebateCode());
        RebateCondition rebateCondition = rebateConditionJpa.findByCode(rebateBatchWaitings.getRebateConditionCode()).get();
        Customer customer = customerRepository.findByUsername(rebateBatchWaitings.getUsername());
        setRebate(rebateBatchWaitings.getAmount(), rebateCondition, rebate, customer);
        rebateBatchWaitings.setStatus("RECEIVED");
        rebateBatchWaitings.setUpdateDate(new Date());
        rebateBatchWaitings.setUpdatedBy(UserLoginUtil.getUsername());
        rebateBatchWaitingJpa.save(rebateBatchWaitings);
    }

    @Transactional
    public void approveAllRebateByRebateCode(String rebateCode) {
        List<RebateBatchWaiting> rebateBatchWaitings = rebateBatchWaitingJpa.findAllByRebateCodeAndStatusAndIsAuto(rebateCode, "WAITING", false);
        for (RebateBatchWaiting item : rebateBatchWaitings
        ) {
            Rebate rebate = rebateJpa.findByCode(item.getRebateCode());
            RebateCondition rebateCondition = rebateConditionJpa.findByCode(item.getRebateConditionCode()).get();
            Customer customer = customerRepository.findByUsername(item.getUsername());
            setRebate(item.getAmount(), rebateCondition, rebate, customer);
            item.setStatus("RECEIVED");
            item.setUpdateDate(new Date());
            item.setUpdatedBy(UserLoginUtil.getUsername());
            rebateBatchWaitingJpa.save(item);
        }
    }

    @Transactional
    public void rejectRebateByCode(String code) {
        RebateBatchWaiting rebateBatchWaitings = rebateBatchWaitingJpa.findByCode(code);
        rebateBatchWaitings.setStatus("REJECT");
        rebateBatchWaitings.setUpdateDate(new Date());
        rebateBatchWaitings.setUpdatedBy(UserLoginUtil.getUsername());
        rebateBatchWaitingJpa.save(rebateBatchWaitings);
    }

    @Transactional
    public void rejectAllRebateByRebateCode(String rebateCode) {
        List<RebateBatchWaiting> rebateBatchWaitings = rebateBatchWaitingJpa.findAllByRebateCodeAndStatusAndIsAuto(rebateCode, "WAITING", false);
        for (RebateBatchWaiting item : rebateBatchWaitings
        ) {
            item.setStatus("REJECT");
            item.setUpdateDate(new Date());
            item.setUpdatedBy(UserLoginUtil.getUsername());
            rebateBatchWaitingJpa.save(item);
        }
    }

    @Transactional
    public void doRebateWaiting() {
        List<RebateBatchWaiting> rebateBatchWaitings = rebateBatchWaitingJpa.findAllByStatusAndIsAuto("WAITING", true);
        for (RebateBatchWaiting item : rebateBatchWaitings
        ) {
            Rebate rebate = rebateJpa.findByCode(item.getRebateCode());
            RebateCondition rebateCondition = rebateConditionJpa.findByCode(item.getRebateConditionCode()).get();
            Customer customer = customerRepository.findByUsername(item.getUsername());
            setRebate(item.getAmount(), rebateCondition, rebate, customer);
            item.setStatus("RECEIVED");
            item.setUpdateDate(new Date());
            item.setUpdatedBy(UserLoginUtil.getUsername());
            rebateBatchWaitingJpa.save(item);
        }
    }

    public void setRebate(BigDecimal totalBet, RebateCondition dataRBCD, Rebate rb, Customer cm) {
        if (totalBet.compareTo(dataRBCD.getValidBets()) != 1) {
            return;
        }
        BigDecimal result = totalBet.multiply(dataRBCD.getRebatePercent().divide(new BigDecimal(100))).setScale(2,
                BigDecimal.ROUND_HALF_UP);
        if (dataRBCD.getMaxRebate().compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(">>>>>>>>>>>>>>>>>>>> " + cm.getUsername() + " ==> Total Rebet = " + result);
            RebateHistory save = new RebateHistory();
            save.setRebateHistoryCode(GenerateRandomString.generate());
            save.setRebateTitle(rb.getTitle());
            save.setUsername(cm.getUsername());
            save.setGroupCode(cm.getGroupCode());
            save.setIsAutoRebate(rb.getIsAutoRebate());
            save.setValidBets(totalBet);
            save.setOriginalRebate(result);
            save.setActualRebate(result);
            save.setRemark("");
            save.setStatus(true);
            save.setCreatedBy("_system");
            save.setCreatedDate(new Date());
            save.setRebateCode(rb.getCode());
            historyRepository.save(save);

            Wallet tempWallet = walletService.findWalletData(cm.getUsername());

            TransactionList transaction = new TransactionList();
            transaction.setTransactionDate(new Date());
            transaction.setTransactionId(GenerateRandomString.generateUUID());
            transaction.setUsername(cm.getUsername());
            transaction.setBeforeBalance(tempWallet.getBalance());
            transaction.setTransactionType(ProjectConstant.TRANSACTION_TYPE.REBATE);
            transaction.setAddBalance(result);
            transaction.setFromSender("-");
            transaction.setToRecive(ProjectConstant.WALLET.MAIN_WALLET);
            transaction.setTotalBalance(tempWallet.getBalance().add(result));
            transaction.setStatus(ProjectConstant.STATUS.SUCCESS);
            transaction.setCreatedBy("_system");
            transaction.setTransactionAmount(result);

            transaction.setAfterBalance(transaction.getTotalBalance());
            allTransactionService.createTransaction(transaction);
            walletService.addBalance(tempWallet.getUsername(), result);
            bonusInformationService.addOrUpdateTotalRebateBonus(tempWallet.getUsername(), result);
        } else if (dataRBCD.getMaxRebate().compareTo(BigDecimal.ZERO) == 1) {
            System.out.println(
                    ">>>>>>>>>>>>>>>>>>>> " + cm.getUsername() + " ==> Total Rebet = " + dataRBCD.getMaxRebate());
            if (result.compareTo(dataRBCD.getMaxRebate()) == 1) {
                result = dataRBCD.getMaxRebate();
            }
            RebateHistory save = new RebateHistory();
            save.setRebateHistoryCode(GenerateRandomString.generate());
            save.setRebateTitle(rb.getTitle());
            save.setUsername(cm.getUsername());
            save.setGroupCode(cm.getGroupCode());
            save.setIsAutoRebate(rb.getIsAutoRebate());
            save.setValidBets(totalBet);
            save.setOriginalRebate(result);
            save.setActualRebate(result);
            save.setRemark("");
            save.setStatus(true);
            save.setCreatedBy("_system");
            save.setCreatedDate(new Date());
            save.setRebateCode(rb.getCode());
            historyRepository.save(save);

            Wallet tempWallet = walletService.findWalletData(cm.getUsername());

            TransactionList transaction = new TransactionList();
            transaction.setTransactionDate(new Date());
            transaction.setTransactionId(GenerateRandomString.generateUUID());
            transaction.setUsername(cm.getUsername());
            transaction.setBeforeBalance(tempWallet.getBalance());
            transaction.setTransactionType(ProjectConstant.TRANSACTION_TYPE.REBATE);
            transaction.setAddBalance(result);
            transaction.setFromSender("-");
            transaction.setToRecive(ProjectConstant.TRANSACTION_TYPE.REBATE);
            transaction.setTotalBalance(tempWallet.getBalance().add(result));
            transaction.setStatus(ProjectConstant.STATUS.SUCCESS);
            transaction.setCreatedBy("_system");
            transaction.setTransactionAmount(result);
            transaction.setAfterBalance(result);
            allTransactionService.createTransaction(transaction);

            walletService.addBalance(tempWallet.getUsername(), result);
            withdrawConditionService.increasedOrCreateGeneral(cm.getUsername(), result, rb.getRebateConditionMultiplier());
            bonusInformationService.addOrUpdateTotalRebateBonus(tempWallet.getUsername(), result);

        }
    }

    @Transactional
    public BigDecimal getRebateDetail(String username) {
        BigDecimal rebateTotal = BigDecimal.ZERO;
        Date current = new Date();
        PromotionMapping usernameActive = promotionService.findByUsernameAndStatus(username, STATUS.ACTIVE);
        if (usernameActive == null) {
            List<Rebate> dataRebate = rebateJpa.findAllByPeriodStatus((long) 1);
            for (Rebate rb : dataRebate) {
                List<RebateCondition> dataRBCD = rebateConditionJpa.findAllByRebateCode(rb.getCode());

                List<String> vipGroupCodes = new ArrayList<String>(Arrays.asList(rb.getVipGroupCode().split(",")));
                List<String> excludedTag = new ArrayList<String>(Arrays.asList(rb.getTagCode().split(",")));
                if (excludedTag.get(0).isEmpty()) {
                    excludedTag = new ArrayList<>();
                }
                List<Customer> tempCM = customerDao.findAllFindByGroupCodeInAndNotInExcludedTag(vipGroupCodes,
                        excludedTag);

                if (tempCM.size() == 0)
                    return rebateTotal;

                List<String> excluedGame = new ArrayList<String>(Arrays.asList(rb.getGamesCodeExclude().split(",")));

                for (RebateCondition item : dataRBCD) {
                    List<GamesNoIcon> gamesNoIcons = gamesNoIconJpa.findAllByProviderCodeAndGameGroupCodeAndGameCodeNotIn(
                            item.getGameProviderCode(), item.getGameGroupCode(), excluedGame);

                    List<String> gamesCodes = new ArrayList<>();
                    if (gamesNoIcons.size() != 0) {
                        gamesCodes = gamesNoIcons.stream().map(GamesNoIcon::getGameCode).collect(Collectors.toList());
                    }

                    for (Customer customer : tempCM) {
                        if (username.equals(customer.getUsername())) {
                            List<TransactionGame> transactionGames;
                            if (rb.getStartBatchJobDate() == null)
                                transactionGames = transactionGameRepository
                                        .findByUsernameAndGameCodeInAndCreatedDateBetween(customer.getUsername(),
                                                gamesCodes, rb.getStartDate(), current);
                            else
                                transactionGames = transactionGameRepository
                                        .findByUsernameAndGameCodeInAndCreatedDateBetween(customer.getUsername(),
                                                gamesCodes, rb.getStartBatchJobDate(), current);
                            log.info("GET ALL TRANSACTION FROM USER");
                            log.info("username: " + customer.getUsername());
                            if (rb.getStartBatchJobDate() == null)
                                log.info("from: " + rb.getStartDate().toString());
                            else
                                log.info("from: " + rb.getStartBatchJobDate().toString());
                            log.info("to: " + current.toString());
                            log.info("transactionGames count: " + transactionGames.size());

                            BigDecimal totalBet = BigDecimal.ZERO;
                            for (TransactionGame i : transactionGames) {
                                if (i.getValidBet() != null)
                                    totalBet = totalBet.add(i.getValidBet());
                            }
                            rebateTotal = rebateTotal.add(totalBet);
                        }
                    }
                }
            }
        }
        System.out.println(" reurn >> rebate >>>>>>>>>>>>>>>>> " + rebateTotal);
        return rebateTotal;
    }

    private Date getNextBatchJobDate(Rebate rb) {
        Date date;
        if (rb.getNextBatchJobDate() == null) {
            date = rb.getStartDate();
        } else {
            date = rb.getNextBatchJobDate();
        }
        if (rb.getPeriodStatus().equals(RebateConstant.PERIOD_STATUS.DAILY)) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();
        } else if (rb.getPeriodStatus().equals(RebateConstant.PERIOD_STATUS.WEEKLY)) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 7);
            date = c.getTime();
        } else if (rb.getPeriodStatus().equals(RebateConstant.PERIOD_STATUS.MONTH)) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, 1);
            date = c.getTime();
        }
        return date;
    }

}
