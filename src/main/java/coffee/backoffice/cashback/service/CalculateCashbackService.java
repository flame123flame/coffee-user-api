package coffee.backoffice.cashback.service;

import coffee.backoffice.cashback.model.Cashback;
import coffee.backoffice.cashback.model.CashbackBatchWaiting;
import coffee.backoffice.cashback.model.CashbackCondition;
import coffee.backoffice.cashback.model.CashbackHistory;
import coffee.backoffice.cashback.repository.dao.CashbackBatchWaitingDao;
import coffee.backoffice.cashback.repository.jpa.*;
import coffee.backoffice.casino.repository.jpa.GamesNoIconJpa;
import coffee.backoffice.casino.repository.jpa.ProviderSummaryRepository;
import coffee.backoffice.casino.service.GamesService;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.repository.dao.TransactionListDao;
import coffee.backoffice.finance.repository.jpa.TransactionGameRepository;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.dao.CustomerDao;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.player.service.BonusInformationService;
import coffee.backoffice.rebate.constant.RebateConstant;
import coffee.backoffice.rebate.model.RebateBatchWaiting;
import coffee.backoffice.rebate.repository.dao.RebateBatchWaitingDao;
import framework.constant.ProjectConstant.STATUS;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.constant.ProjectConstant.WALLET;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class CalculateCashbackService {

    @Autowired
    private CashbackJpa cashbackJpa;

    @Autowired
    private GamesService gamesService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private GamesNoIconJpa gamesNoIconJpa;
    @Autowired
    private TransactionGameRepository transactionGameRepository;

    @Autowired
    private CalculateCashbackRepository calculateCashbackRepository;

    @Autowired
    private CashbackHistoryRepository cashbackHistoryRepository;

    @Autowired
    private CashbackConditionJpa cashbackConditionJpa;

    @Autowired
    private WalletService walletService;
    @Autowired
    private BonusInformationService bonusInformationService;

    @Autowired
    private AllTransactionService allTransactionService;
    @Autowired
    private TransactionListDao transactionListDao;
    @Autowired
    private CashBackBatchWaitingJpa cashBackBatchWaitingJpa;

    @Autowired
    private ProviderSummaryService providerSummaryService;

    @Autowired
    private ProviderSummaryRepository providerSummaryRepository;

    @Autowired
    private CashbackBalanceService cashbackBalanceService;

    @Autowired
    private WithdrawConditionService withdrawConditionService;

    @Autowired
    private CashbackBatchWaitingDao cashbackBatchWaitingDao;


    public DataTableResponse<CashbackBatchWaiting> getPendingPaginate(DatatableRequest req) {
        DataTableResponse<CashbackBatchWaiting> dataTable = new DataTableResponse<>();
        DataTableResponse<CashbackBatchWaiting> newData = new DataTableResponse<>();
        newData = cashbackBatchWaitingDao.paginate(req);
        dataTable.setRecordsTotal(newData.getRecordsTotal());
        dataTable.setDraw(newData.getDraw());
        dataTable.setData(newData.getData());
        return dataTable;
    }


    @Transactional
    public Cashback findCashbackDaily(Boolean isTest) {
        log.info("[================ Start Rebate ==============]");
        Date current = new Date();
        List<Cashback> dataRebate = cashbackJpa.findAllMustDoCashBack(new Date());
        log.info("Count Task : " + dataRebate.size());
        for (Cashback rb : dataRebate) {
            calculateCashBack(rb, current);
//            SET NEXT JOB DATE
            Date date = getNextBatchJobDate(rb);
            if (isTest) {
                rb.setNextBatchJobDate(current);
            } else {
                rb.setNextBatchJobDate(date);
            }
            rb.setStartBatchJobDate(current);
            cashbackJpa.save(rb);
        }
        log.info("[================ End Rebate ==============]");
        return null;
    }

    @Transactional
    public void calculateCashBack(Cashback cb, Date current) {
        List<String> vipGroupCodes = new ArrayList<>(Arrays.asList(cb.getVipGroupCode().split(",")));
        List<String> excludedTag = new ArrayList<>(Arrays.asList(cb.getTagCode().split(",")));
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

        for (Customer customer : tempCM
        ) {
            SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

            Date from;
            if (cb.getStartBatchJobDate() == null) {
                from = cb.getStartDate();
            } else {
                from = cb.getStartBatchJobDate();
            }
            log.info("DO CASHBACK USERNAME : " + customer.getUsername());
            log.info("DO from  : " + DateFor.format(from));
            log.info("DO to : " + DateFor.format(current));
            List<TransactionList> depositTransactionLists = transactionListDao.getTransactionListByUsername(from, current, customer.getUsername(), "DEPOSIT");
            List<TransactionList> withdrawTransaction = transactionListDao.getTransactionListByUsername(from, current, customer.getUsername(), "WITHDRAW");
            log.info("GET DEPOSIT : " + depositTransactionLists.size());
            log.info("GET WITHDRAW : " + withdrawTransaction.size());
            // TODO : check null

            BigDecimal depositAmount = BigDecimal.ZERO;
            BigDecimal withdrawAmount = BigDecimal.ZERO;
            for (TransactionList depositTransactionList : depositTransactionLists) {
                depositAmount = depositAmount.add(depositTransactionList.getTransactionAmount());
            }
            for (TransactionList transactionList : withdrawTransaction) {
                withdrawAmount = withdrawAmount.add(transactionList.getTransactionAmount());
            }

            log.info("ALL DEPOSIT : " + depositAmount);
            log.info("ALL WITHDRAW : " + withdrawAmount);
            BigDecimal beforeBalance = BigDecimal.ZERO;
            if (depositTransactionLists.size() != 0)
                beforeBalance = depositTransactionLists.get(0).getBeforeBalance();

            if (cashBackBatchWaitingJpa.existsByUsernameAndStatus(customer.getUsername(), "WAITING")) {
                CashbackBatchWaiting cashbackBatchWaiting = cashBackBatchWaitingJpa.findByUsernameAndStatus(customer.getUsername(), "WAITING");
                cashbackBatchWaiting.setDeposit(cashbackBatchWaiting.getDeposit().add(depositAmount));
                cashbackBatchWaiting.setWithdraw(cashbackBatchWaiting.getWithdraw().add(withdrawAmount));
                cashbackBatchWaiting.setCurrentBalance(walletService.findBalanceWithoutBonus(customer.getUsername()));
                cashbackBatchWaiting.setUpdatedDate(new Date());
                cashBackBatchWaitingJpa.save(cashbackBatchWaiting);
            } else {
                CashbackBatchWaiting cashbackBatchWaiting = new CashbackBatchWaiting();
                cashbackBatchWaiting.setUsername(customer.getUsername());
                cashbackBatchWaiting.setBeforeBalance(beforeBalance);
                cashbackBatchWaiting.setDeposit(depositAmount);
                cashbackBatchWaiting.setIsAuto(cb.getIsAutoCashback());
                cashbackBatchWaiting.setWithdraw(withdrawAmount);
                cashbackBatchWaiting.setReceiveDate(getReceiveDate(cb));
//                cashbackBatchWaiting.setReceiveDate(new Date());
                cashbackBatchWaiting.setStatus("WAITING");
                cashbackBatchWaiting.setCurrentBalance(walletService.findBalanceWithoutBonus(customer.getUsername()));
                cashbackBatchWaiting.setCashbackCode(cb.getCode());
                cashbackBatchWaiting.setCashbackTitle(cb.getTitle());
                cashbackBatchWaiting.setDatePeriodType(cb.getPeriodStatus());
                cashbackBatchWaiting.setConditionMultiple(cb.getCashbackConditionMultiplier());
                cashbackBatchWaiting.setTotalLoss(
                        (cashbackBatchWaiting.getCurrentBalance()
                                .add(cashbackBatchWaiting.getWithdraw())
                        )
                                .subtract(
                                        (cashbackBatchWaiting.getBeforeBalance()
                                                .add(cashbackBatchWaiting.getDeposit()))
                                )
                );
                cashbackBatchWaiting.setReceivedAmount(calculateReceiveCashbackAmount(cb, cashbackBatchWaiting.getTotalLoss()));
                cashBackBatchWaitingJpa.save(cashbackBatchWaiting);
            }

        }

    }

    @Transactional
    public void doCashback(Boolean isTest) {
        Date date = new Date();
        List<CashbackBatchWaiting> cashbackBatchWaiting;
        if (isTest) {
            cashbackBatchWaiting = cashBackBatchWaitingJpa.findAllByStatusAndIsAuto("WAITING", true);
        } else {
            cashbackBatchWaiting = cashBackBatchWaitingJpa.findAllByStatusAndReceiveDateLessThanAndIsAuto("WAITING", date, true);
        }
        log.info("cashbackBatchWaiting  : " + cashbackBatchWaiting.size());
        for (CashbackBatchWaiting item : cashbackBatchWaiting
        ) {
            log.info("==============================");
            log.info("username : " + item.getUsername());
            log.info("current balance : " + (item.getCurrentBalance().add(item.getWithdraw())));
            log.info("before balance : " + (item.getBeforeBalance().add(item.getDeposit())));
            Cashback cashback = cashbackJpa.findByCode(item.getCashbackCode());
            item.setStatus("NOT_RECEIVED");
//            if result be positive value mean that user will not get cash back
            BigDecimal result = item.getTotalLoss();
            log.info("cal result  : " + result);
            if (result.compareTo(BigDecimal.valueOf(0)) < 0) {
                item.setStatus("RECEIVED");
                Customer customer = customerRepository.findByUsername(item.getUsername());
                setCashback(item.getReceivedAmount(), result, cashback, customer);
                cashbackBalanceService.updateCashback(item.getUsername(), calculateReceiveCashbackAmount(cashback, result));
            }
            item.setUpdatedDate(new Date());
            cashBackBatchWaitingJpa.save(item);
        }
    }

    @Transactional
    public void approveByCode(String code) {
        Date date = new Date();
        CashbackBatchWaiting cashbackBatchWaiting = cashBackBatchWaitingJpa.findByCode(code);
        log.info("============== doCashbackByCode ================");
        log.info("username : " + cashbackBatchWaiting.getUsername());
        log.info("current balance : " + (cashbackBatchWaiting.getCurrentBalance().add(cashbackBatchWaiting.getWithdraw())));
        log.info("before balance : " + (cashbackBatchWaiting.getBeforeBalance().add(cashbackBatchWaiting.getDeposit())));
        Cashback cashback = cashbackJpa.findByCode(cashbackBatchWaiting.getCashbackCode());
        cashbackBatchWaiting.setStatus("NOT_RECEIVED");
//            if result be positive value mean that user will not get cash back
        BigDecimal result = cashbackBatchWaiting.getTotalLoss();
        log.info("cal result  : " + result);
        if (result.compareTo(BigDecimal.valueOf(0)) < 0) {
            cashbackBatchWaiting.setStatus("RECEIVED");
            Customer customer = customerRepository.findByUsername(cashbackBatchWaiting.getUsername());
            setCashback(cashbackBatchWaiting.getReceivedAmount(), result, cashback, customer);
            cashbackBalanceService.updateCashback(cashbackBatchWaiting.getUsername(), calculateReceiveCashbackAmount(cashback, result));
        }
        cashbackBatchWaiting.setUpdatedDate(new Date());
        cashBackBatchWaitingJpa.save(cashbackBatchWaiting);
    }


    @Transactional
    public void approveAllByCashbackCode(String code) {
        Date date = new Date();
        List<CashbackBatchWaiting>
                cashbackBatchWaiting = cashBackBatchWaitingJpa.findAllByCashbackCodeAndStatus(code, "WAITING");

        log.info("cashbackBatchWaiting  : " + cashbackBatchWaiting.size());
        for (CashbackBatchWaiting item : cashbackBatchWaiting
        ) {
            log.info("==============================");
            log.info("username : " + item.getUsername());
            log.info("current balance : " + (item.getCurrentBalance().add(item.getWithdraw())));
            log.info("before balance : " + (item.getBeforeBalance().add(item.getDeposit())));
            Cashback cashback = cashbackJpa.findByCode(item.getCashbackCode());
            item.setStatus("NOT_RECEIVED");
//            if result be positive value mean that user will not get cash back
            BigDecimal result = item.getTotalLoss();
            log.info("cal result  : " + result);
            if (result.compareTo(BigDecimal.valueOf(0)) < 0) {
                item.setStatus("RECEIVED");
                Customer customer = customerRepository.findByUsername(item.getUsername());
                setCashback(item.getReceivedAmount(), result, cashback, customer);
                cashbackBalanceService.updateCashback(item.getUsername(), calculateReceiveCashbackAmount(cashback, result));
            }
            item.setUpdatedDate(new Date());
            cashBackBatchWaitingJpa.save(item);
        }
    }

    public void rejectCashback(String code) {
        CashbackBatchWaiting cashbackBatchWaiting = cashBackBatchWaitingJpa.findByCode(code);
        cashbackBatchWaiting.setStatus("REJECT");
        cashbackBatchWaiting.setUpdatedDate(new Date());
        cashBackBatchWaitingJpa.save(cashbackBatchWaiting);
    }

    @Transactional
    public void rejectAllCashback(String cashbackCode) {
        List<CashbackBatchWaiting> cashbackBatchWaiting = cashBackBatchWaitingJpa.findAllByCashbackCodeAndStatus(cashbackCode, "WAITING");
        for (CashbackBatchWaiting item : cashbackBatchWaiting
        ) {
            item.setStatus("REJECT");
            item.setUpdatedDate(new Date());
            cashBackBatchWaitingJpa.save(item);
        }
    }

    private BigDecimal calculateReceiveCashbackAmount(Cashback cb, BigDecimal loss) {
        List<CashbackCondition> cashbackCondition = cashbackConditionJpa.findAllByCashbackCodeOrderByMoreThanAmountDesc(cb.getCode());
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal lossAbs = loss.abs();
        for (CashbackCondition item : cashbackCondition
        ) {
            log.info("loss  : " + lossAbs);
            log.info("item.getMoreThanAmount()  : " + item.getMoreThanAmount());
            if (lossAbs.compareTo(item.getMoreThanAmount()) >= 0) {
                BigDecimal percent = item.getCashbackPercent().divide(BigDecimal.valueOf(100), BigDecimal.ROUND_UNNECESSARY);
                log.info("percent  : " + percent);
                result = lossAbs.multiply(percent);
                log.info("result percent  : " + result);
                if (result.compareTo(item.getMaxCashbackAmount()) > 0)
                    result = item.getMaxCashbackAmount();
                break;
            }
        }
        log.info("calculateReceiveCashbackAmount result  : " + result);
        return result;
    }

    @Transactional
    public void setCashback(BigDecimal cashbackDaily, BigDecimal totalLoss, Cashback cb, Customer cm) {

        System.out.println("setCashback >>>>>>>>> cashbackDaily ==> " + cashbackDaily);
        System.out.println("cashbackDaily < 0");
        BigDecimal result = cashbackDaily.abs();
        System.out.println(">>>>>>>>>>>>>>>>>>>> " + cm.getUsername() + " ==> Total Result = " + result);
        CashbackHistory save = new CashbackHistory();
        save.setCashbackHistoryCode(GenerateRandomString.generate());
        save.setCashbackTitle(cb.getTitle());
        save.setUsername(cm.getUsername());
        save.setGroupCode(cm.getGroupCode());
        save.setIsAutoCashback(cb.getIsAutoCashback());
        save.setTotalLoss(totalLoss);
        save.setOriginalCashback(result);
        save.setActualCashback(result);
        save.setRemark("");
        save.setStatus(true);
        save.setCreatedBy("_system");
        save.setCreatedDate(new Date());
        save.setCashbackCode(cb.getCode());
        cashbackHistoryRepository.save(save);

        Wallet tempWallet = walletService.findWalletData(cm.getUsername());

        TransactionList transaction = new TransactionList();
        transaction.setTransactionDate(new Date());
        transaction.setTransactionId(GenerateRandomString.generateUUID());
        transaction.setUsername(cm.getUsername());
        transaction.setBeforeBalance(tempWallet.getBalance());
        transaction.setTransactionType(TRANSACTION_TYPE.CASHBACK);
        transaction.setAddBalance(result);
        transaction.setFromSender("-");
        transaction.setToRecive(WALLET.MAIN_WALLET);
        transaction.setTotalBalance(tempWallet.getBalance().add(result));
        transaction.setStatus(STATUS.SUCCESS);
        transaction.setCreatedBy("_system");
        transaction.setTransactionAmount(result);
        transaction.setAfterBalance(transaction.getTotalBalance());
        allTransactionService.createTransaction(transaction);

        walletService.addBalanceWallet(tempWallet.getUsername(), result);
        withdrawConditionService.increasedOrCreateGeneral(tempWallet.getUsername(), result, cb.getCashbackConditionMultiplier());
        bonusInformationService.addOrUpdateTotalCashBackBonusBonus(tempWallet.getUsername(), result);
    }

    private Date getNextBatchJobDate(Cashback rb) {
        Date date;
        if (rb.getNextBatchJobDate() == null) {
            date = rb.getStartDate();
        } else {
            date = rb.getNextBatchJobDate();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }

    private Date getReceiveDate(Cashback rb) {
        Date date;
        if (rb.getNextBatchJobDate() == null) {
            date = rb.getStartDate();
        } else {
            date = rb.getNextBatchJobDate();
        }
        if (rb.getPeriodStatus().equals(RebateConstant.PERIOD_STATUS.WEEKLY)) {
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