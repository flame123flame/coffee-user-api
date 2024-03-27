package coffee.backoffice.player.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.model.AffiliateNetwork;
import coffee.backoffice.affiliate.service.AffiliateService;
import coffee.backoffice.cashback.service.CashbackBalanceService;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.finance.vo.res.DepositMainRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.model.TagManagement;
import coffee.backoffice.player.repository.dao.CustomerDao;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.player.repository.jpa.TagManagementJpa;
import coffee.backoffice.player.vo.req.BankVerifyReq;
import coffee.backoffice.player.vo.req.CustomerChangPasswordReq;
import coffee.backoffice.player.vo.req.CustomerChangeAccountNameReq;
import coffee.backoffice.player.vo.req.CustomerChangeBankAccountReq;
import coffee.backoffice.player.vo.req.CustomerChangeBankReq;
import coffee.backoffice.player.vo.req.CustomerChangeEnableReq;
import coffee.backoffice.player.vo.req.CustomerChangeGroupReq;
import coffee.backoffice.player.vo.req.CustomerChangeTagReq;
import coffee.backoffice.player.vo.req.CustomerReq;
import coffee.backoffice.player.vo.res.BankVerifyRes;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.rebate.service.CalculateRebateService;
import coffee.provider.joker.service.JokerProviderService;
import coffee.provider.sa.service.SaGamingService;
import coffee.provider.sbobet.service.SboBetService;
import coffee.provider.sexy.service.MxProviderService;
import framework.constant.ProjectConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.WebSocketNotificationRes;
import framework.utils.UserLoginUtil;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private WalletService walletService;

	@Autowired
	private AffiliateService affiliateService;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WithdrawConditionService withdrawConditionService;

	@Autowired
	private TagManagementJpa tagManagementJpa;

	@Autowired
	private JokerProviderService jokerProviderService;

	@Autowired
	private MxProviderService mxProviderService;

	@Autowired
	private SboBetService sboBetService;

	@Autowired
	private SaGamingService saGamingService;

	@Autowired
	private ProviderSummaryService providerSummaryService;

	@Autowired
	private CalculateRebateService calculateRebateService;

	@Autowired
	private CashbackBalanceService cashbackBalanceService;
	
	@Autowired
	private GroupLevelService groupLevelService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	public List<CustomerRes> getAll() {
		List<CustomerRes> dataRes = customerDao.listFindCustomer();
		return dataRes;
	}

	public DataTableResponse<CustomerRes> getCustomerPaginate(DatatableRequest req) {
		DataTableResponse<CustomerRes> dataTable = new DataTableResponse<>();
		DataTableResponse<CustomerRes> tag = customerDao.getCustomerPaginate(req);
		List<CustomerRes> data = tag.getData();
		for (CustomerRes item : data) {
			if (item.getTagCode() != null) {
				List<String> listTagCode = Arrays.asList(item.getTagCode().split(","));
				List<String> tempList = new ArrayList<String>();
				for (String code : listTagCode) {
					Optional<TagManagement> tagdata = tagManagementJpa.findByTagCode(code);
					tempList.add(tagdata.get().getName());
				}
				item.setTagName(String.join(",", tempList));
			}
		}
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public String saveCustomerBo(CustomerReq form) {
		Customer dataUser = customerRepository.findByUsername(form.getUsername());
		if (dataUser == null) {
			Customer dataMobile = customerRepository.findByMobilePhone(form.getMobilePhone());
			if (dataMobile == null) {
				Customer dataSave = new Customer();
				String username = form.getUsername().toLowerCase();
				form.setUsername(username.replaceAll("\\s", ""));
				dataSave.setUsername(form.getUsername());
				dataSave.setPassword(passwordEncoder.encode(form.getPassword()));
				dataSave.setMobilePhone(form.getMobilePhone());
				dataSave.setRealName(form.getRealName());
				dataSave.setBankCode(form.getBankCode());
				dataSave.setBankAccount(form.getBankAccount());
				dataSave.setGroupCode(form.getGroupCode());
				dataSave.setTagCode(form.getTagCode());
				dataSave.setCreatedBy(UserLoginUtil.getUsername());
				dataSave.setEnable(true);
				dataSave.setRegisterStatus((long) 2);
				dataSave.setBankStatus(ProjectConstant.STATUS.PENDING);
				customerRepository.save(dataSave);
				// create Wallet
				walletService.createWallet(dataSave.getUsername(), ProjectConstant.WALLET.MAIN_WALLET,
						ProjectConstant.WALLET.MAIN_WALLET);

				// create affiliate
				affiliateService.create(form.getUsername(), form.getAffiliateId());

				// create cashback balance user
				cashbackBalanceService.createCashbackBalance(form.getUsername());

				// create provider user
				createUserProvider(dataSave);
			} else {
				return SAVE.DUPLICATE_MOBILE_DATA;
			}

		} else {
			return SAVE.DUPLICATE_USERNAME_DATA;
		}
		return SAVE.SUCCESS;

	}

	@Transactional
	public CustomerRes saveCustomerFromFront(CustomerReq form) throws Exception {
		String username = form.getUsername().toLowerCase();
		form.setUsername(username.replaceAll("\\s", ""));
		if (customerRepository.existsByMobilePhoneAndRegisterStatus(form.getMobilePhone(), (long) 2)
				|| customerRepository.existsByUsernameAndRegisterStatus(form.getUsername(), (long) 2)) {
			return null;
		}
		Customer dataSave = new Customer();
		dataSave = customerRepository.findByMobilePhone(form.getMobilePhone());
		dataSave.setUsername(form.getUsername());
		dataSave.setPassword(passwordEncoder.encode(form.getPassword()));
		dataSave.setMobilePhone(form.getMobilePhone());
		dataSave.setRealName(form.getRealName());
		dataSave.setBankCode(form.getBankCode());
		dataSave.setBankAccount(form.getBankAccount());
		GroupLevel group = groupLevelService.getDefaultGroup();
		dataSave.setGroupCode(group.getGroupCode());
		dataSave.setTagCode(form.getTagCode());
		dataSave.setCreatedBy("Website");
		dataSave.setEnable(true);
		dataSave.setRegisterStatus((long) 2);
		dataSave.setBankStatus(ProjectConstant.STATUS.PENDING);
		dataSave.setLastProvider("");
		dataSave = customerRepository.save(dataSave);
//      create Wallet
		walletService.createWallet(form.getUsername(), ProjectConstant.WALLET.MAIN_WALLET,
				ProjectConstant.WALLET.MAIN_WALLET);

//		create affiliate
		affiliateService.create(form.getUsername(), form.getAffiliateId());

		// create cashback balance user
		cashbackBalanceService.createCashbackBalance(form.getUsername());

//		create provider user
		createUserProvider(dataSave);

		CustomerRes dataRes = new CustomerRes();
		dataRes.setEntityToRes(dataSave);
//		register notification
		WebSocketNotificationRes responseString = new WebSocketNotificationRes();
		responseString.setType("NEW_REGISTER");
		responseString.setMessage(form.getUsername());
		responseString.setUsername(form.getUsername());
		this.simpMessagingTemplate.convertAndSend("/admin/notification/register", responseString);

		return dataRes;
	}

	private void createUserProvider(Customer data) {
//		jokerProviderService.createUser(data.getUsername());
		mxProviderService.createMember(data.getUsername());
		sboBetService.registerPlayer(data.getUsername(), "testAgent2");
		saGamingService.createMember(data.getUsername());
	}

	@Transactional
	public void lockUser(String username, String phone) {
		Customer dataSave = new Customer();
		dataSave.setUsername(username);
		dataSave.setMobilePhone(phone);
		dataSave.setRegisterStatus((long) 1);
		dataSave = customerRepository.save(dataSave);
		CustomerRes dataRes = new CustomerRes();
		dataRes.setEntityToRes(dataSave);
	}

	public void editCustomer(CustomerReq form) {
		Customer dataSave = customerRepository.findByUsername(form.getUsername());
		dataSave.setId(form.getId());
		dataSave.setUsername(form.getUsername());
		dataSave.setPassword(passwordEncoder.encode(form.getPassword()));
		dataSave.setMobilePhone(form.getMobilePhone());
		dataSave.setRealName(form.getRealName());
		dataSave.setBankCode(form.getBankCode());
		dataSave.setBankAccount(form.getBankAccount());
		dataSave.setGroupCode(form.getGroupCode());
		dataSave.setTagCode(form.getTagCode());
		dataSave.setUpdatedBy(UserLoginUtil.getUsername());
		dataSave.setUpdatedDate(new Date());
//        dataSave.setEnable(form.getEnable());
		customerRepository.save(dataSave);
	}

	@Transactional
	public void deleteCustomer(String username) {
		Customer dataSave = customerRepository.findByUsername(username);
		dataSave.setEnable(false);
		customerRepository.save(dataSave);
//		customerRepository.deleteByUsername(username);
	}

	public CustomerRes getCustomerByUsername(String username) {
		CustomerRes res = customerDao.findCustomerByUsername(username);
		if (res.getTagCode() != null) {
			List<String> listTagCode = Arrays.asList(res.getTagCode().split(","));
			List<String> tempList = new ArrayList<String>();
			for (String code : listTagCode) {
				Optional<TagManagement> data = tagManagementJpa.findByTagCode(code);
				tempList.add(data.get().getName());
			}
			res.setTagName(String.join(",", tempList));
		}
		return res;
	}

	public String changePasswordCustomer(CustomerChangPasswordReq form) {
		String checkPassword = "";
		Customer data = customerRepository.findByUsername(form.getUsername());
		if (StringUtils.isBlank(form.getOldPassword()) && StringUtils.isBlank(form.getNewPassword())) {
			data.setRealName(form.getFirstName() + " " + form.getLastName());
			data.setNickname(form.getNickName());
			data.setBirthday(form.getBirthday());
			data.setEmail(form.getEmail());
			checkPassword = RESPONSE_STATUS.SUCCESS.toString();
			customerRepository.save(data);
		} else if (passwordEncoder.matches(form.getOldPassword(), data.getPassword())) {
			data.setPassword(passwordEncoder.encode(form.getNewPassword()));
			data.setRealName(form.getFirstName() + " " + form.getLastName());
			data.setNickname(form.getNickName());
			data.setBirthday(form.getBirthday());
			data.setEmail(form.getEmail());
			checkPassword = RESPONSE_STATUS.SUCCESS.toString();
			customerRepository.save(data);
		} else {
			checkPassword = RESPONSE_STATUS.FAILED.toString();
		}
		return checkPassword;
	}

	public List<Customer> getDropdownCustomer() {
		List<Customer> dataRes = customerRepository.findAllByEnable(true);
		return dataRes;
	}

	public Customer getByUsername(String username) {
		return customerRepository.findByUsername(username);
	}
	
	public Customer getByBankCodeAndBankAccount(String bankCode,String bankAccount) {
		return customerRepository.findByBankCodeAndBankAccount(bankCode,bankAccount);
	}

	public void saveCustomer(Customer entity) {
		customerRepository.save(entity);
	}

	public Customer forgotPasswordCustomer(String phoneNumber) {
		return customerRepository.findByMobilePhone(phoneNumber);
	}

	public List<BankVerifyRes> getBankVerify() {
		List<BankVerifyRes> dataRes = customerDao.listBankVerify();
		System.out.println(dataRes);
		return dataRes;
	}

	public BigDecimal findTurOverDeposit(List<DepositMainRes> depositList, Date after, BigDecimal condition) {
		BigDecimal sum = BigDecimal.ZERO;
		for (DepositMainRes deposit : depositList) {
			BigDecimal amount = new BigDecimal(deposit.getAmount());
			BigDecimal divisor = condition.divide(new BigDecimal(100));
			if (deposit.getAuditorDate().compareTo(after) > 0) {
				sum = sum.add(amount.divide(divisor));
			}
		}
		return sum;
	}

	public void changeBankStatus(BankVerifyReq req) {
		Customer cus = customerRepository.findByUsername(req.getUsername());
		CustomerChangeEnableReq data = new CustomerChangeEnableReq();
		if (cus != null) {
			if (ProjectConstant.STATUS.REJECT.equals(req.getBankStatus())) {
				data.setUsername(cus.getUsername());
				data.setEnable(false);
				changeEnable(data);
			}
			cus.setBankStatus(req.getBankStatus());
			customerRepository.save(cus);
		}
	}

	public void changeGroup(CustomerChangeGroupReq req) {
		Customer dataFind = customerRepository.findByUsername(req.getUsername());
		if (dataFind != null) {
			dataFind.setGroupCode(req.getGroupCode());
			dataFind.setUpdatedBy(UserLoginUtil.getUsername());
			dataFind.setUpdatedDate(new Date());
			customerRepository.save(dataFind);
		}
	}

	public void changeTag(CustomerChangeTagReq req) {
		Customer dataFind = customerRepository.findByUsername(req.getUsername());
		if (dataFind != null) {
			dataFind.setTagCode(String.join(",", req.getTagCode()));
			dataFind.setUpdatedBy(UserLoginUtil.getUsername());
			dataFind.setUpdatedDate(new Date());
			customerRepository.save(dataFind);
		}
	}

	public void changeEnable(CustomerChangeEnableReq req) {
		Customer dataFind = customerRepository.findByUsername(req.getUsername());
		if (dataFind != null) {
			dataFind.setEnable(req.getEnable());
			dataFind.setUpdatedBy(UserLoginUtil.getUsername());
			dataFind.setUpdatedDate(new Date());
			customerRepository.save(dataFind);
		}
	}

	public void changeBank(CustomerChangeBankReq req) {
		Customer dataFind = customerRepository.findByUsername(req.getUsername());
		if (dataFind != null) {
			dataFind.setBankCode(req.getBankCode());
			dataFind.setUpdatedBy(UserLoginUtil.getUsername());
			dataFind.setUpdatedDate(new Date());
			customerRepository.save(dataFind);
		}
	}

	public void changeBankAccount(CustomerChangeBankAccountReq req) {
		Customer dataFind = customerRepository.findByUsername(req.getUsername());
		if (dataFind != null) {
			dataFind.setBankAccount(req.getBankAccount());
			dataFind.setUpdatedBy(UserLoginUtil.getUsername());
			dataFind.setUpdatedDate(new Date());
			customerRepository.save(dataFind);
		}
	}
	
	public void changeAccountName(CustomerChangeAccountNameReq req) {
		Customer dataFind = customerRepository.findByUsername(req.getUsername());
		if (dataFind != null) {
			dataFind.setRealName(req.getAccountName());
			dataFind.setUpdatedBy(UserLoginUtil.getUsername());
			dataFind.setUpdatedDate(new Date());
			customerRepository.save(dataFind);
		}
	}

	public Integer getTotalRegPlayers(String startDate, String endDate) {
		Integer temp = customerRepository.findTotalRegPlayers(startDate, endDate);
		if (temp == null)
			temp = 0;
		return temp;

	}

	public List<String> getUsername(String username) {
		List<AffiliateNetwork> dataUser = affiliateService.getUsernameNetwork(username);
		// Java 8
		List<String> userList = dataUser.stream().map(x -> x.getUsername()).collect(Collectors.toList());
		System.out.println(userList); // [mkyong, jack, lawrence]
		return userList;
	}

	public Boolean checkUserDummy(String username) {
		Customer dataFind = customerRepository.findByUsername(username);

		return dataFind != null ? true : false;

	}

	public CustomerRes saveUserDummyOnCustomer(CustomerReq req) {
		Customer saveData = new Customer();
		saveData.setUsername(req.getUsername());
		saveData.setMark(req.getMark());
		saveData.setMobilePhone(req.getMobilePhone());
		customerRepository.save(saveData);
		CustomerRes dataRes = new CustomerRes();
		dataRes.setEntityToRes(saveData);
		return dataRes;

	}

	@Transactional
	public void deleteUserDummyOnCustomerByUsername(String username) {
		customerRepository.deleteByUsername(username);
	}

	public DataTableResponse<BankVerifyRes> getBankVerifyPaginate(DatatableRequest req) {
		DataTableResponse<BankVerifyRes> pg = customerDao.getBankVerifyPaginate(req);
		DataTableResponse<BankVerifyRes> dataTable = new DataTableResponse<>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		return dataTable;
	}

	public DataTableResponse<BankVerifyRes> getBankVerifyDuplicateAcPaginate(DatatableRequest req) {
		DataTableResponse<BankVerifyRes> pg = customerDao.getBankVerifyDuplicateAcPaginate(req);
		DataTableResponse<BankVerifyRes> dataTable = new DataTableResponse<>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		return dataTable;
	}

	public DataTableResponse<BankVerifyRes> getBankVerifyDuplicateRlPaginate(DatatableRequest req) {
		DataTableResponse<BankVerifyRes> pg = customerDao.getBankVerifyDuplicateRlPaginate(req);
		DataTableResponse<BankVerifyRes> dataTable = new DataTableResponse<>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		return dataTable;
	}
}
