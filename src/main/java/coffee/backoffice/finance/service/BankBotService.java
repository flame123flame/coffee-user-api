package coffee.backoffice.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import coffee.backoffice.cashback.service.CashbackBalanceService;
import coffee.backoffice.finance.model.BankBot;
import coffee.backoffice.finance.model.CompanyAccount;
import coffee.backoffice.finance.model.Deposit;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.repository.dao.BankBotDao;
import coffee.backoffice.finance.vo.model.ObjectString;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.service.DepositInformationService;
import coffee.backoffice.player.service.GroupLevelService;
import coffee.backoffice.promotion.service.PromotionService;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.STATUS;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.constant.ProjectConstant.WALLET;
import framework.constant.ProjectConstant.WITHDRAW_CONDITION;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankBotService {

	@Autowired
	private BankBotDao bankBotDao;
	
	@Autowired
	private AllTransactionService allTransactionService;
	
	@Autowired
	private DepositService depositService;
	
	@Autowired
    private WalletService walletService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GroupLevelService groupLevelService;
	
	@Autowired
	private WithdrawConditionService withdrawConditionService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private CashbackBalanceService cashbackBalanceService;

	@Autowired
	private DepositInformationService depositInformationService;
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private CompanyAccountService companyAccountService;
	
	public void logBankBot() {
		List<BankBot> bankBotList = bankBotDao.getAll();
		for(BankBot temp:bankBotList) {
			
			log.info(temp.toString());
		}
	}

	public void updateDepositBankBot() {
		
		Deposit lastDeposit = depositService.getLastDepositBySystemType(ProjectConstant.DEPOSIT.BB);
		try {
			List<BankBot> bankBotList = new ArrayList<BankBot>();
			if(lastDeposit != null) {
				bankBotList = bankBotDao.getTypeAfterDate("D",lastDeposit.getCreatedDate());
			}else {
				Calendar date = Calendar.getInstance();
				date.setTime(new Date());
				date.add(Calendar.MINUTE,-1);
				bankBotList = bankBotDao.getTypeAfterDate("D",date.getTime());
			}
			if(bankBotList != null && bankBotList.size() > 0) {
				Deposit depositNew = null;
				for(BankBot temp :bankBotList){
					String fromBankId = temp.getTxFromBankId().trim();
					String fromAccountNo = temp.getTxFromAccountNo().trim();
					String toBankId = temp.getTxToBankId().trim();
					String toAccountNo = temp.getTxToAccountNo().trim();
					String bankRef = temp.getBankRef().trim();
					
					log.info("== Start Bank ref == :: "+bankRef);
					Customer cus = customerService.getByBankCodeAndBankAccount(convertToBankCode(fromBankId),fromAccountNo);
					if(cus !=null) {
						log.info("== found account ==");
						log.info("== username == :: "+cus.getUsername());
						GroupLevel group = groupLevelService.getByGroupCode(cus.getGroupCode());
						BigDecimal wallet = walletService.findBalanceWithoutBonus(cus.getUsername());
						//found
						if(ProjectConstant.STATUS.APPROVE.equals(cus.getBankStatus())) {
							log.info("== account has verify ==");
							log.info("== toBankId == :: "+toBankId);
							log.info("== toAccountNo == :: "+toAccountNo);
							CompanyAccount companyAccount = companyAccountService.getCompanyByBankIdAndBankAccount(toBankId,toAccountNo);
							if(companyAccount != null) {
								log.info("== set value ==");
								depositNew = new Deposit();
								depositNew.setDepositOrder(bankRef);
								depositNew.setUsername(cus.getUsername());
								depositNew.setAmount(temp.getTxAmount());
								depositNew.setCompanyAccountCode(companyAccount.getCompanyAccountCode());
						        depositNew.setDepositRemark("SUCCESS");
						        depositNew.setStatus(ProjectConstant.STATUS.APPROVE);
						        log.info("== value =="+depositNew.toString());
						        depositNew.setCreatedBy("_bankbot");
						        depositNew.setAuditor("_bankbot");
						        depositNew.setDepositDate(temp.getTxDateTime());
						        depositNew.setAuditorDate(new Date());
						        depositNew.setSystemType(ProjectConstant.DEPOSIT.BB);
						        depositNew.setBeforeBalance(wallet);
						        depositNew.setAfterBalance(wallet.add(temp.getTxAmount()));
						        depositService.saveEntity(depositNew);
						        
						        log.info("== set withdraw condition && transaction ==");
								depositSuccess(temp.getTxAmount(),cus.getUsername(),group);
								
								companyAccount.setCurrDepositDaily(companyAccount.getCurrDepositDaily().add(temp.getTxAmount()));
								companyAccount.setBalance(temp.getTxEndingBalance());
								
								companyAccountService.saveCompanyAccount(companyAccount);
							}else {
								log.info("== companyAccount is null ==");
							}
					        
						}else {
							log.info("== account not verify ==");
							//not verify
							depositNew = new Deposit();
							depositNew.setDepositOrder(bankRef);
							depositNew.setUsername(cus.getUsername());
							depositNew.setAmount(temp.getTxAmount());
							depositNew.setCompanyAccountCode(toBankId+"-"+toAccountNo);
							depositNew.setDepositDate(temp.getTxDateTime());
					        depositNew.setDepositRemark("not verify bank");
					        depositNew.setStatus(ProjectConstant.STATUS.PENDING);
					        depositNew.setCreatedBy("_bankbot");
					        depositNew.setSystemType(ProjectConstant.DEPOSIT.BB);
					        depositNew.setBeforeBalance(wallet);
					        depositNew.setAfterBalance(wallet.add(temp.getTxAmount()));
					        depositService.saveEntity(depositNew);
						}
					    cus.setDepositCount(cus.getDepositCount()+1);
					    customerService.saveCustomer(cus);
					}else {
						log.info("== not found account ==");
						//not found
						depositNew = new Deposit();
						depositNew.setDepositOrder(bankRef);
						depositNew.setUsername(convertToBankCode(fromBankId)+"-"+fromAccountNo);
						depositNew.setAmount(temp.getTxAmount());
						depositNew.setCompanyAccountCode(fromAccountNo);
						depositNew.setDepositDate(temp.getTxDateTime());
				        depositNew.setDepositRemark("not found account");
				        depositNew.setStatus(ProjectConstant.STATUS.PENDING);
				        depositNew.setCreatedBy("_bankbot");
				        depositNew.setSystemType(ProjectConstant.DEPOSIT.BB);
				        depositNew.setBeforeBalance(BigDecimal.ZERO);
				        depositNew.setAfterBalance(BigDecimal.ZERO);
				        
				        depositService.saveEntity(depositNew);
					}

//					log.info(temp.toString());
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void depositSuccess(BigDecimal amount,String username,GroupLevel group) {
		Wallet data = walletService.findWalletData(username);

		TransactionList transaction = new TransactionList();
		transaction.setTransactionDate(new Date());
		transaction.setTransactionId(GenerateRandomString.generateUUID());
		transaction.setUsername(username);
		transaction.setTransactionType(TRANSACTION_TYPE.DEPOSIT);
		transaction.setAddBalance(amount);
		transaction.setFromSender("-");
		transaction.setToRecive(WALLET.MAIN_WALLET);
		transaction.setTotalBalance(data.getBalance().add(amount));
		transaction.setStatus(STATUS.SUCCESS);
		transaction.setCreatedBy("_bankBot");
		transaction.setTransactionAmount(amount);
		transaction.setBeforeBalance(data.getBalance());
		transaction.setAfterBalance(data.getBalance().add(amount));
		allTransactionService.createTransaction(transaction);
		
		walletService.addBalanceWallet(username, amount);
		depositInformationService.addOrUpdateDepositInformation(username, amount);

		// Create turnover amount
		WithdrawCondition withdraw = withdrawConditionService.getWithdrawConditionByUsernameAndConditionType(username,
				WITHDRAW_CONDITION.GENERAL, WITHDRAW_CONDITION.NOT_PASS);
		
		if (withdraw != null) { 
			withdraw = withdrawConditionService.getWithdrawConditionByUsernameAndConditionType(username,
					WITHDRAW_CONDITION.PROMOTION, WITHDRAW_CONDITION.NOT_PASS);
		}
		if (withdraw != null) {
			BigDecimal newTurn = amount.multiply(group.getGeneralCondition().divide(new BigDecimal(100)));
			withdraw.setTargetTurnover(withdraw.getTargetTurnover().add(newTurn));
			withdraw.setAmount(withdraw.getAmount().add(amount));
			withdraw.setUpdatedDate(new Date());
		} else {
			withdraw = new WithdrawCondition();
			withdraw.setUsername(username);
			withdraw.setConditionType(WITHDRAW_CONDITION.GENERAL);
			withdraw.setConditionStatus(WITHDRAW_CONDITION.NOT_PASS);
			withdraw.setCurrentTurnover(BigDecimal.ZERO);
			withdraw.setOperatorValue(group.getGeneralCondition());
			withdraw.setCreatedBy(UserLoginUtil.getUsername());
			withdraw.setAmount(amount);
			withdraw.setCalculateType(WITHDRAW_CONDITION.MULTIPLIER);
			withdraw.setTargetTurnover(
					amount.multiply(group.getGeneralCondition().divide(new BigDecimal(100))));
		}
		withdrawConditionService.saveWithdrawCondition(withdraw);

		// create update cashback balance
		cashbackBalanceService.updateCashbackBalance(username, amount);

		// trigger websocket for recommendPromotion();
		ObjectString responseString = new ObjectString();
		responseString.setStatus(true);
		responseString.setMessage("เครดิตจำนวน " + amount
				+ " บาท ถูกเติมเข้ากระเป๋าตังค์ของคุณแล้วขอบคุณที่เลือกไว้ใจ finbet ค่ะ");
		this.simpMessagingTemplate.convertAndSend("/recommend/" + username,
				promotionService.recommendPromotion(amount));
		this.simpMessagingTemplate.convertAndSend("/deposit/" + username, responseString);
	}
	
	private String convertToBankCode(String bankId) {
		if(ProjectConstant.BANKBOT_BANK_ID.KTB.equals(bankId)) {
			return "KTB";
		}else if(ProjectConstant.BANKBOT_BANK_ID.SCB.equals(bankId)) {
			return "SCB";
		}else if(ProjectConstant.BANKBOT_BANK_ID.KBANK.equals(bankId)) {
			return "KBANK";
		}
		return bankId;
	}
}
