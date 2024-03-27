package coffee.backoffice.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import coffee.backoffice.cashback.service.CashbackBalanceService;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.Withdraw;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.repository.dao.WithdrawDao;
import coffee.backoffice.finance.repository.jpa.WithdrawRepository;
import coffee.backoffice.finance.vo.model.ObjectString;
import coffee.backoffice.finance.vo.req.WithdrawReq;
import coffee.backoffice.finance.vo.res.WithdrawListRes;
import coffee.backoffice.finance.vo.res.WithdrawRes;
import coffee.backoffice.frontend.service.InboxMessageService;
import coffee.backoffice.frontend.vo.req.MessageReq;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.service.WithdrawalInformationService;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.STATUS;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.constant.ProjectConstant.WALLET;
import framework.constant.ProjectConstant.WITHDRAW;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WithdrawService {

	@Autowired
	private WithdrawRepository withdrawRepository;

	@Autowired
	private WithdrawDao withdrawDao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private InboxMessageService inboxMessageService;

	@Autowired
	private WithdrawConditionService withdrawConditionService;

	@Autowired
	private AllTransactionService allTransactionService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private CashbackBalanceService cashbackBalanceService;
	
	@Autowired
	private WithdrawalInformationService withdrawalInformationService;

	private ModelMapper modelMapper = new ModelMapper();

	public DataTableResponse<WithdrawRes> getPaginate(DatatableRequest param) {
		DataTableResponse<WithdrawRes> paginateData = withdrawDao.WithdrawResponsePaginate(param);
		DataTableResponse<WithdrawRes> dataTable = new DataTableResponse<>();
		List<WithdrawRes> data = paginateData.getData();

		dataTable.setRecordsTotal(paginateData.getRecordsTotal());
		dataTable.setDraw(paginateData.getDraw());
		dataTable.setData(data);
		dataTable.setPage(param.getPage());
		return paginateData;
	}

	public void newAdminRequest(WithdrawReq req) throws Exception {
		List<WithdrawCondition> list = withdrawConditionService.getWithdrawConditionByUsername(req.getUsername(),
				ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);
		Withdraw entity = new Withdraw();
		entity.setOrderWithdraw(GenerateRandomString.generateUUID());
		entity.setUsername(req.getUsername());
		entity.setAmount(req.getAmount());
		entity.setAdminRemark(req.getAdminRemark());
//				BO ACCOUNT
		entity.setAccountName(req.getAccountName());
		entity.setBankAccount(req.getBankAccount());
		entity.setBankName(req.getBankName());
//				CUSTOMMER ACCOUNT
		Customer customer = customerService.getByUsername(req.getUsername());
		entity.setToAccountName(customer.getRealName());
		entity.setToBankAccount(customer.getBankAccount());
		entity.setToBankName(customer.getBankCode());

		entity.setWithdrawDate(new Date());
		entity.setCreatedBy(UserLoginUtil.getUsername());
		entity.setCreatedDate(new Date());
		if (list.size() == 0) {
			walletService.reduceBalance(req.getUsername(), req.getAmount());
			walletService.addPendingBalance(req.getUsername(), req.getAmount());
			entity.setWithdrawStatus(ProjectConstant.WITHDRAW.PENDING);
			withdrawRepository.save(entity);
		} else {
			entity.setWithdrawStatus(ProjectConstant.WITHDRAW.REJECT);
			withdrawRepository.save(entity);
			throw new Exception("NOT PASS WITHDRAW CONDITION");
		}
	}

	public String changeStatusWithdraw(Long id, String status) throws Exception {
		ObjectString responseString = new ObjectString();
		Withdraw withdraw = withdrawRepository.findById(id).get();
		withdraw.setWithdrawStatus(status);
		withdraw.setUpdatedBy(UserLoginUtil.getUsername());
		withdraw.setUpdatedDate(new Date());
		withdrawRepository.save(withdraw);
		if (status.equals(ProjectConstant.WITHDRAW.WITHDRAW_APPROVED)) {
			sendSuccessMessage(withdraw.getUsername(), withdraw.getAmount());

			// create update cashback balance
			cashbackBalanceService.updateCashbackBalanceWithdraw(withdraw.getUsername(), withdraw.getAmount());

			responseString.setStatus(true);
			responseString.setMessage("ยอดเงินจำนวน " + withdraw.getAmount() + " บาทได้ถูกโอนไปยังบัญชีธนาคาร "
					+ withdraw.getToBankAccount() + " เป็นที่เรียบร้อยแล้วขอบคุณที่เลือกไว้ใจ finbet ค่ะ ");
			this.simpMessagingTemplate.convertAndSend("/withdraw/" + withdraw.getUsername(), responseString);

			Wallet data = walletService.findWalletData(withdraw.getUsername());
			TransactionList transaction = new TransactionList();
			transaction.setTransactionDate(new Date());
			transaction.setTransactionId(GenerateRandomString.generateUUID());
			transaction.setUsername(withdraw.getUsername());
			transaction.setTransactionType(TRANSACTION_TYPE.WITHDRAW);
			transaction.setSubBalance(withdraw.getAmount().negate());
			transaction.setFromSender(WALLET.MAIN_WALLET);
			transaction.setToRecive("-");
			transaction.setTotalBalance((data.getBalance().add(withdraw.getAmount())).subtract(withdraw.getAmount()));
			transaction.setStatus(STATUS.SUCCESS);
			transaction.setCreatedBy(UserLoginUtil.getUsername());
			transaction.setTransactionAmount(withdraw.getAmount());
			transaction.setBeforeBalance(data.getBalance().add(withdraw.getAmount()));
			transaction.setAfterBalance(transaction.getTotalBalance());
			allTransactionService.createTransaction(transaction);
			walletService.reducePendingBalance(withdraw.getUsername(), withdraw.getAmount());
			withdrawalInformationService.addOrUpdateWithdrawalInformation(withdraw.getUsername(), withdraw.getAmount());
			return WITHDRAW.WITHDRAW_APPROVED;
		}
		if (status.equals(ProjectConstant.WITHDRAW.REJECT)) {
			sendFailedMessage(withdraw.getUsername(), withdraw.getAmount());
			responseString.setStatus(false);
			responseString.setMessage("ทำรายการไม่สำเร็จกรุณาติดต่อเจ้าหน้าที่");
			this.simpMessagingTemplate.convertAndSend("/withdraw/" + withdraw.getUsername(), responseString);
			walletService.addBalance(withdraw.getUsername(), withdraw.getAmount());
			walletService.reducePendingBalance(withdraw.getUsername(), withdraw.getAmount());
			return WITHDRAW.REJECT;
		}
		return WITHDRAW.BANK_APPROVED;
	}

	private void sendSuccessMessage(String username, BigDecimal amount) {

		MessageReq messageReq = new MessageReq();
		List<String> userList = new ArrayList<>();
		userList.add(username);
		messageReq.setUsernames(userList);
		messageReq.setMessageType(ProjectConstant.INBOX_MESSAGE.AUTO);
		messageReq.setPromoCode("");
		messageReq.setSubject("แจ้งเตือนถอนเงินสำเร็จ");
		messageReq.setWebMessage(
				"<p>ระบบดำเนินการถอนเงิน<p><p> จำนวน " + String.format("%,.2f", amount) + " บาท เสร็จสิ้น</p>");
		inboxMessageService.saveMessage(messageReq);
	}

	private void sendFailedMessage(String username, BigDecimal amount) {
		MessageReq messageReq = new MessageReq();
		List<String> userList = new ArrayList<>();
		userList.add(username);
		messageReq.setUsernames(userList);
		messageReq.setMessageType(ProjectConstant.INBOX_MESSAGE.AUTO);
		messageReq.setPromoCode("");
		messageReq.setSubject("แจ้งเตือนถอนเงินไม่สำเร็จ");
		messageReq.setWebMessage("<p>ระบบดำเนินการถอนเงินไม่สำเร็จ<p><p>ได้รับเงินคืน จำนวน "
				+ String.format("%,.2f", amount) + " บาท </p>");
		inboxMessageService.saveMessage(messageReq);
	}

	public void updateRemark(Long id, WithdrawReq req) {
		Withdraw withdraw = withdrawRepository.findById(id).get();
		withdraw.setAdminRemark(req.getAdminRemark());
		withdraw.setUpdatedBy(UserLoginUtil.getUsername());
		withdraw.setUpdatedDate(new Date());
		withdrawRepository.save(withdraw);
	}

	public List<WithdrawRes> getAllByToken() {
		List<Withdraw> withdraws = withdrawRepository
				.findAllByUsernameOrderByCreatedDateDesc(UserLoginUtil.getUsername());
		List<WithdrawRes> withdrawRes = modelMapper.map(withdraws, new TypeToken<List<WithdrawRes>>() {
		}.getType());

		return withdrawRes;
	}

	public WithdrawListRes getWithdrawListPaginate(DatatableRequest req) {
		WithdrawListRes res = new WithdrawListRes();
		DataTableResponse<WithdrawListRes.DataLisrRes> dataTable = new DataTableResponse<>();
		DataTableResponse<WithdrawListRes.DataLisrRes> pg = withdrawDao.getWithdrawResPaginate(req);
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		res.setDataList(dataTable);
		List<WithdrawListRes.DataLisrRes> dataListRes = pg.getData();
		WithdrawListRes.DataSummaryRes dataSummaryRes = new WithdrawListRes.DataSummaryRes();
		for (WithdrawListRes.DataLisrRes dataRes : dataListRes) {
			dataSummaryRes.setSubTotalWithdraw(dataSummaryRes.getSubTotalWithdraw().add(dataRes.getAmount()));
		}
		WithdrawListRes.DataSummaryRes dataSummaryTotalRes = withdrawDao.getWithdrawTotalRes(req);
		dataSummaryRes.setTotalWithdraw(dataSummaryTotalRes.getTotalWithdraw());
		res.setSummary(dataSummaryRes);
		return res;
	}
}
