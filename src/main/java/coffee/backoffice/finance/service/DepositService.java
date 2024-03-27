package coffee.backoffice.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffee.backoffice.cashback.service.CashbackBalanceService;
import coffee.backoffice.finance.model.BankBot;
import coffee.backoffice.finance.model.CompanyAccount;
import coffee.backoffice.finance.model.Deposit;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.repository.dao.BankBotDao;
import coffee.backoffice.finance.repository.dao.DepositDao;
import coffee.backoffice.finance.repository.jpa.DepositRepository;
import coffee.backoffice.finance.vo.model.ObjectString;
import coffee.backoffice.finance.vo.req.DepositReq;
import coffee.backoffice.finance.vo.res.CompanyAccountRes;
import coffee.backoffice.finance.vo.res.DepositDetailRes;
import coffee.backoffice.finance.vo.res.DepositListRes;
import coffee.backoffice.finance.vo.res.DepositMainRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.service.DepositInformationService;
import coffee.backoffice.player.service.GroupLevelService;
import coffee.backoffice.promotion.service.PromotionService;
import coffee.backoffice.report.vo.res.PlayerReportRes;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.STATUS;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.constant.ProjectConstant.WALLET;
import framework.constant.ProjectConstant.WITHDRAW_CONDITION;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepositService {

	@Autowired
	private DepositDao depositDao;

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private WalletService walletService;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private GroupLevelService groupLevelService;

	@Autowired
	private CompanyAccountService companyAccountService;

	@Autowired
	private AllTransactionService allTransactionService;

	@Autowired
	private WithdrawConditionService withdrawConditionService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private CashbackBalanceService cashbackBalanceService;

	@Autowired
	private DepositInformationService depositInformationService;

	public DepositDetailRes getDetail(String depositOrder) throws Exception {
		DepositDetailRes dataRes = new DepositDetailRes();
		Deposit deposit = depositRepository.findByDepositOrder(depositOrder);

		dataRes.setCustomerRes(customerService.getCustomerByUsername(deposit.getUsername()));
		dataRes.setDepositOrder(deposit.getDepositOrder());

		return dataRes;

	}

	public List<DepositMainRes> getAllList() throws Exception {
		log.info("=== DepositService => getAllList() on Started ===");
		log.info("Calling depositDao.findAll()");
		List<Deposit> resultList = depositDao.findAll();
		log.info("depositDao.findAll() : " + resultList.size());
		List<DepositMainRes> res = new ArrayList<DepositMainRes>();
		DepositMainRes temp = null;
		for (Deposit result : resultList) {

			log.info("DepositOrder : " + result.getDepositOrder());
			CompanyAccountRes company = companyAccountService.getOne(result.getCompanyAccountCode());
			temp = new DepositMainRes();
			temp.setDepositOrder(result.getDepositOrder());
			temp.setDepositType(result.getDepositType());
			temp.setDepositDate(result.getDepositDate());
			temp.setDepositRemark(result.getDepositRemark());
			temp.setAmount(result.getAmount().toString());
			temp.setCompanyBankCode(company.getBankCode());
			temp.setCompanyAccountName(company.getAccountName());
			temp.setStatus(result.getStatus());
			temp.setAuditor(result.getAuditor());
			temp.setAuditorDate(result.getAuditorDate());

			Customer customer = customerService.getByUsername(result.getUsername());
			if (customer != null) {
				log.info("customerService.getByUsername() : Not null");
				temp.setUsername(customer.getUsername());
				temp.setRealname(customer.getRealName());
			} else {
				log.info("customerService.getByUsername() : Null");
			}

			res.add(temp);
		}

		log.info("=== DepositService => getAllList() Ending ===");
		return res;
	}

	public DepositListRes getDepositListPaginate(DatatableRequest req) {
		DepositListRes res = new DepositListRes();
		DataTableResponse<DepositListRes.DataLisrRes> dataTable = new DataTableResponse<>();
		DataTableResponse<DepositListRes.DataLisrRes> pg = depositDao.getDepositResPaginate(req);
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		res.setDataList(dataTable);
		List<DepositListRes.DataLisrRes> dataListRes = pg.getData();
		DepositListRes.DataSummaryRes dataSummaryRes = new DepositListRes.DataSummaryRes();
		for (DepositListRes.DataLisrRes dataRes : dataListRes) {
			dataSummaryRes.setSubTotalDeposit(dataSummaryRes.getSubTotalDeposit().add(dataRes.getDeposit()));
		}
		DepositListRes.DataSummaryRes dataSummaryTotalRes = depositDao.getDepositTotalRes(req);
		dataSummaryRes.setTotalDeposit(dataSummaryTotalRes.getTotalDeposit());
		res.setSummary(dataSummaryRes);
		return res;
	}

	public List<DepositMainRes> getDepositByUsernameDesc(String username) {
		log.info("=== DepositService => getDepositByUsernameDesc() on Started ===");
		log.info("Calling depositRepository.findByUsernameOrderByDepositDateDesc()");
		List<Deposit> resultList = depositRepository.findByUsernameAndStatusOrderByCreatedDateDesc(username,
				STATUS.APPROVE);
		log.info("depositRepository.findByUsernameOrderByDepositDateDesc() : " + resultList.size());
		List<DepositMainRes> res = new ArrayList<DepositMainRes>();
		DepositMainRes temp = null;
		for (Deposit result : resultList) {

			log.info("DepositOrder : " + result.getDepositOrder());
			CompanyAccountRes company = companyAccountService.getOne(result.getCompanyAccountCode());
			temp = new DepositMainRes();
			temp.setDepositOrder(result.getDepositOrder());
			temp.setDepositType(result.getDepositType());
			temp.setDepositDate(result.getDepositDate());
			temp.setCreatedDate(result.getCreatedDate());
			temp.setDepositRemark(result.getDepositRemark());
			temp.setAmount(result.getAmount().toString());
			temp.setCompanyBankCode(company.getBankCode());
			temp.setCompanyAccountName(company.getAccountName());
			temp.setStatus(result.getStatus());
			temp.setAuditor(result.getAuditor());
			temp.setAuditorDate(result.getAuditorDate());

			res.add(temp);
		}

		log.info("=== DepositService => getDepositByUsernameDesc() Ending ===");
		return res;
	}

	public void saveEntity(Deposit entity) {
		try {
			depositRepository.save(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Deposit getLastDepositBySystemType(String system) {
		return  depositRepository.findFirstBySystemTypeOrderByCreatedDateDesc(system);

	}

	public List<Deposit> getDepositByUsernameAfter(String username, Date after) {
		return depositRepository.findByUsernameAndCreatedDateAfter(username, after);

	}

	public List<Deposit> getDepositByUsernameBefore(String username, Date before) {
		return depositRepository.findByUsernameAndCreatedDateBefore(username, before);

	}

	public List<Deposit> getDepositByUsernameBetween(String username, Date start, Date end) {
		return depositRepository.findByUsernameAndCreatedDateBetween(username, start, end);

	}

	@Transactional
	public void changeStatus(DepositReq form) {
		log.info("=== DepositService => changeStatus() on Started ===");
		log.info("Calling depositRepository.findByDepositOrderAndUsername()");
		Deposit temp = depositRepository.findByDepositOrderAndUsername(form.getDepositOrder(), form.getUsername());
		Customer customer = customerService.getByUsername(form.getUsername());
		GroupLevel group = groupLevelService.getByGroupCode(customer.getGroupCode());
		ObjectString responseString = new ObjectString();
		log.info("Check [ db.status ] => PENDING ?");
		if ("PENDING".contains(temp.getStatus())) {
			log.info("[ status ] is PENDING => Set new [ status] && Save");
			temp.setStatus(form.getStatus());
			temp.setAuditor(UserLoginUtil.getUsername());
			temp.setAuditorDate(new Date());
			depositRepository.save(temp);

			log.info("Check [ req.status ] ");
			if ("APPROVE".contains(form.getStatus())) {
				log.info("[ req.status ] is APPROVE => Allow deposit amount tranfer to Wallet");
				BigDecimal amount = temp.getAmount();
				Wallet data = walletService.findWalletData(form.getUsername());

				TransactionList transaction = new TransactionList();
				transaction.setTransactionDate(new Date());
				transaction.setTransactionId(GenerateRandomString.generateUUID());
				transaction.setUsername(form.getUsername());
				transaction.setTransactionType(TRANSACTION_TYPE.DEPOSIT);
				transaction.setAddBalance(amount);
				transaction.setFromSender("-");
				transaction.setToRecive(WALLET.MAIN_WALLET);
				transaction.setTotalBalance(data.getBalance().add(amount));
				transaction.setStatus(STATUS.SUCCESS);
				transaction.setCreatedBy(UserLoginUtil.getUsername());
				transaction.setTransactionAmount(amount);
				transaction.setBeforeBalance(data.getBalance());
				transaction.setAfterBalance(transaction.getTotalBalance());
				walletService.addBalanceWallet(form.getUsername(), amount);
				allTransactionService.createTransaction(transaction);
				depositInformationService.addOrUpdateDepositInformation(form.getUsername(), amount);

				// Create turnover amount
				WithdrawCondition withdraw = null;
				withdraw = withdrawConditionService.getWithdrawConditionByUsernameAndConditionType(form.getUsername(),
						WITHDRAW_CONDITION.GENERAL, WITHDRAW_CONDITION.NOT_PASS);
				if (withdraw != null) {
					BigDecimal newTurn = amount.multiply(group.getGeneralCondition().divide(new BigDecimal(100)));
					withdraw.setTargetTurnover(withdraw.getTargetTurnover().add(newTurn));
					withdraw.setAmount(withdraw.getAmount().add(amount));
					withdraw.setUpdatedDate(new Date());
				} else {
					withdraw = new WithdrawCondition();
					withdraw.setUsername(form.getUsername());
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
				cashbackBalanceService.updateCashbackBalance(form.getUsername(), amount);
				
				CompanyAccount companyAccount = companyAccountService.getCompanyByCode(temp.getCompanyAccountCode());
				companyAccount.setCurrDepositDaily(companyAccount.getCurrDepositDaily().add(amount));
				companyAccount.setBalance(companyAccount.getBalance().add(amount));
				companyAccountService.saveCompanyAccount(companyAccount);

				// trigger websocket for recommendPromotion();

				responseString.setStatus(true);
				responseString.setMessage("เครดิตจำนวน " + amount
						+ " บาท ถูกเติมเข้ากระเป๋าตังค์ของคุณแล้วขอบคุณที่เลือกไว้ใจ finbet ค่ะ");
				this.simpMessagingTemplate.convertAndSend("/recommend/" + form.getUsername(),
						promotionService.recommendPromotion(amount));
				this.simpMessagingTemplate.convertAndSend("/deposit/" + form.getUsername(), responseString);
			} else {
				log.info("[ req.status ] is REJECT => End");
				responseString.setStatus(false);
				responseString.setMessage("ทำรายการไม่สำเร็จกรุณาติดต่อเจ้าหน้าที่");
				this.simpMessagingTemplate.convertAndSend("/deposit/" + form.getUsername(), responseString);
			}
		} else {
			log.info("[ db.status ] is not PENDING => End ");
		}
		log.info("=== DepositService => changeStatus() Ending ===");
	}
	
	
}
