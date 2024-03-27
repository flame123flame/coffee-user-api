package coffee.backoffice.affiliate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.model.Affiliate;
import coffee.backoffice.affiliate.model.AffiliateChannel;
import coffee.backoffice.affiliate.model.AffiliateGroup;
import coffee.backoffice.affiliate.model.AffiliateNetwork;
import coffee.backoffice.affiliate.repository.dao.AffiliateDao;
import coffee.backoffice.affiliate.repository.jpa.AffiliateChannelRepository;
import coffee.backoffice.affiliate.repository.jpa.AffiliateGroupRepository;
import coffee.backoffice.affiliate.repository.jpa.AffiliateNetworkRepository;
import coffee.backoffice.affiliate.repository.jpa.AffiliateRepository;
import coffee.backoffice.affiliate.vo.model.ChannelDetail;
import coffee.backoffice.affiliate.vo.req.AffiliateReq;
import coffee.backoffice.affiliate.vo.req.WithdrawReq;
import coffee.backoffice.affiliate.vo.res.AffiliateNetworkRes;
import coffee.backoffice.affiliate.vo.res.AffiliatePlayAmountRes;
import coffee.backoffice.affiliate.vo.res.AffiliateRes;
import coffee.backoffice.casino.model.ProviderSummary;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.vo.res.BankRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.player.repository.jpa.GroupLevelRepository;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.player.vo.res.GroupLevelRes;
import framework.constant.ProjectConstant;
import framework.utils.ConvertDateUtils;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AffiliateService {

	@Autowired
	private AffiliateRepository affiliateRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private GroupLevelRepository groupLevelRepository;

	@Autowired
	private AffiliateNetworkRepository affiliateNetworkRepository;

	@Autowired
	private AffiliateChannelRepository affiliateChannelRepository;

	@Autowired
	private AffiliateGroupRepository affiliateGroupRepository;

	@Autowired
	private AffiliateDao affiliateDao;

	@Autowired
	private WalletService walletService;

	@Autowired
	private AllTransactionService allTransactionService;

	@Autowired
	private ProviderSummaryService providerSummaryService;

	public List<AffiliateRes> getAll() {
		List<Affiliate> resultList = affiliateRepository.findAll();
		List<AffiliateRes> res = new ArrayList<AffiliateRes>();
		AffiliateRes data = null;
		for (Affiliate temp : resultList) {
			AffiliateNetwork network = affiliateNetworkRepository.findByAffiliateCode(temp.getAffiliateCode());
			data = new AffiliateRes();
			data.setEntityToRes(temp);
			data.setUsername(network.getUsername());
			data.setAffiliateCodeUp(network.getAffiliateCodeUp());
//    		ADD DOWNLINE
			List<AffiliateNetwork> affList = getAffiliateNetworkByUpline(data.getAffiliateCode());
			List<AffiliateNetworkRes> affResList = new ArrayList<>();
			for (AffiliateNetwork item : affList) {
				AffiliateNetworkRes affRes = new AffiliateNetworkRes();
				affRes.setEntityToRes(item);
				affRes.setCustomerDetail(customerRepository.findByUsername(affRes.getUsername()));
				affResList.add(affRes);
			}
			data.setAffiliateNetworkList(affResList);
//			ADD SELF DETAIL
			Customer self = customerRepository.findByUsername(network.getUsername());
			CustomerRes selfRes = new CustomerRes();
			selfRes.setEntityToRes(self);
			data.setSelfDetail(selfRes);
//			ADD UPLINE DETAIL
			AffiliateNetwork upLineNetwork = affiliateNetworkRepository.findByAffiliateCode(data.getAffiliateCodeUp());
			if (upLineNetwork != null) {
				Customer upLine = customerRepository.findByUsername(upLineNetwork.getUsername());
				CustomerRes upLineRes = new CustomerRes();
				upLineRes.setEntityToRes(upLine);
				data.setUpLineDetail(upLineRes);
			}
			res.add(data);
		}
		return res;
	}
	
	

	public DataTableResponse<AffiliateRes> getAllPaginate(DatatableRequest req) {
		DataTableResponse<Affiliate> affiliateTable = getPaginateModel(req);
		DataTableResponse<AffiliateRes> res = new DataTableResponse<AffiliateRes>();
		List<AffiliateRes> res2 = new ArrayList<AffiliateRes>();
		AffiliateRes data = null;
		for (Affiliate temp : affiliateTable.getData()) {
			AffiliateNetwork network = affiliateNetworkRepository.findByAffiliateCode(temp.getAffiliateCode());
			if(network !=null) {
				data = new AffiliateRes();
				data.setEntityToRes(temp);
				data.setUsername(network.getUsername());
				data.setAffiliateCodeUp(network.getAffiliateCodeUp());
//	    		ADD DOWNLINE
				List<AffiliateNetwork> affList = getAffiliateNetworkByUpline(data.getAffiliateCode());
				List<AffiliateNetworkRes> affResList = new ArrayList<>();
				for (AffiliateNetwork item : affList) {
					AffiliateNetworkRes affRes = new AffiliateNetworkRes();
					affRes.setEntityToRes(item);
					affRes.setCustomerDetail(customerRepository.findByUsername(affRes.getUsername()));
					affResList.add(affRes);
				}
				data.setAffiliateNetworkList(affResList);
//				ADD SELF DETAIL
				Customer self = customerRepository.findByUsername(network.getUsername());
				CustomerRes selfRes = new CustomerRes();
				selfRes.setEntityToRes(self);
				data.setSelfDetail(selfRes);
//				ADD UPLINE DETAIL
				AffiliateNetwork upLineNetwork = affiliateNetworkRepository.findByAffiliateCode(data.getAffiliateCodeUp());
				if (upLineNetwork != null) {
					Customer upLine = customerRepository.findByUsername(upLineNetwork.getUsername());
					CustomerRes upLineRes = new CustomerRes();
					upLineRes.setEntityToRes(upLine);
					data.setUpLineDetail(upLineRes);
				}
				res2.add(data);
				
			}
		}
		res.setData(res2);
		res.setDraw(affiliateTable.getDraw());
		res.setPage(affiliateTable.getPage());
		res.setRecordsTotal(affiliateTable.getRecordsTotal());

		return res;
	}

	public DataTableResponse<Affiliate> getPaginateModel(DatatableRequest req) {
		DataTableResponse<Affiliate> dataTable = new DataTableResponse<>();
		DataTableResponse<Affiliate> affiliate = new DataTableResponse<>();
		affiliate = affiliateDao.paginate(req);
		dataTable.setRecordsTotal(affiliate.getRecordsTotal());
		dataTable.setDraw(affiliate.getDraw());
		dataTable.setData(affiliate.getData());
		return dataTable;
	}

	public void create(String username, String affiliateCodeUp) {
		Affiliate data = new Affiliate();
		String code = genarateAffiliateId();
		data.setAffiliateCode(code);
		data.setCreatedBy(UserLoginUtil.getUsername());
		data.setTotalIncome(BigDecimal.ZERO);
		data.setIncomeOne(BigDecimal.ZERO);
		data.setIncomeTwo(BigDecimal.ZERO);
		data.setWithdraw(BigDecimal.ZERO);
		data.setAffiliateGroupCode("642148805352200czmsrtdkqk");
		affiliateRepository.save(data);

		walletService.createWallet(username, ProjectConstant.WALLET.AFFILIATE_WALLET,
				ProjectConstant.WALLET.AFFILIATE_WALLET);
		createNetwork(username, affiliateCodeUp, code);

	}

	public void createNetwork(String username, String affiliateCodeUp, String affiliateCode) {
		AffiliateNetwork data = affiliateNetworkRepository.findByUsername(username);
		if (data == null) {
			AffiliateNetwork network = new AffiliateNetwork();
			network.setUsername(username);
			network.setAffiliateCode(affiliateCode);
			Affiliate temp = affiliateRepository.findByAffiliateCode(affiliateCodeUp);
			if (temp != null) {
				network.setAffiliateCodeUp(affiliateCodeUp);
			}
			network.setRegisterDate(new Date());
			affiliateNetworkRepository.save(network);
		}

	}

	private static String genarateAffiliateId() {
		return String.valueOf(System.nanoTime());
	}

	public void update(AffiliateReq req) throws Exception {
		Affiliate data = affiliateRepository.findById(req.getId()).get();
		data.setReqToEntity(req);
		data.setUpdatedBy(UserLoginUtil.getUsername());
		data.setUpdatedDate(new Date());
		affiliateRepository.save(data);
	}
	
	public void update(Affiliate req) {
		req.setUpdatedBy(UserLoginUtil.getUsername());
		req.setUpdatedDate(new Date());
		affiliateRepository.save(req);
	}
	
	public Integer getCountAffiliateNetworkByUpline(String code) {
		return affiliateNetworkRepository.countByAffiliateCodeUp(code);
	}
	
	public List<AffiliateNetwork> getAffiliateNetworkByUpline(String code) {
		return affiliateNetworkRepository.findByAffiliateCodeUp(code);
	}

	public AffiliateNetwork getAffiliateNetworkByUsername(String username) {
		return affiliateNetworkRepository.findByUsername(username);
	}

	public List<AffiliateNetwork> getUsernameNetwork(String username) {
		return affiliateNetworkRepository.findByUsernameStartsWith(username);
	}

	public List<AffiliateNetwork> getAffiliateNetworkBetween(Date sd, Date ed) {
		return affiliateNetworkRepository.findByRegisterDateBetween(sd, ed);
	}

	public void tranferTransaction(String transactionType, String username, String from, String to,
			BigDecimal tranferAmount, BigDecimal totalBalance, BigDecimal before) {
		TransactionList transaction = new TransactionList();
		transaction.setTransactionDate(new Date());
		transaction.setUsername(username);
		transaction.setTransactionType(transactionType);
		transaction.setFromSender(from);
		transaction.setTranferAmount(tranferAmount);
		transaction.setToRecive(to);
		transaction.setTotalBalance(totalBalance);
		transaction.setStatus(ProjectConstant.STATUS.SUCCESS);
		transaction.setUpdatedBy(UserLoginUtil.getUsername());
		transaction.setBeforeBalance(before);
		transaction.setAfterBalance(BigDecimal.ZERO);
		transaction.setTransactionAmount(before.subtract(tranferAmount));

		allTransactionService.createTransaction(transaction);
	}

	public BigDecimal getCommissionPercentFromUsernameAndProductTypeCode(String username, String productTypeCode) {
		BigDecimal maxCommission = BigDecimal.valueOf(0);
		List<AffiliateChannel> affiliateChannels = affiliateChannelRepository.findAllByProductTypeCode(productTypeCode);
		AffiliateNetwork affiliateNetwork = affiliateNetworkRepository.findByUsername(username);
		Affiliate affiliate = new Affiliate();
		if (affiliateNetwork != null) {
			affiliate = affiliateRepository.findByAffiliateCode(affiliateNetwork.getAffiliateCode());
		}
		for (AffiliateChannel item : affiliateChannels) {
			if (item.getAffiliateGroupCode().equals(affiliate.getAffiliateGroupCode())) {
				System.out.println(maxCommission.compareTo(item.getShareRateOne()));
				if (maxCommission.compareTo(item.getShareRateOne()) == -1) {
					maxCommission = item.getShareRateOne();
				}
			}
			;
		}
		return maxCommission;
	}

	public Affiliate getDetailAffiliate(String affiliateCode) {
		return affiliateRepository.findByAffiliateCode(affiliateCode);
	}

	public Integer getTotalRegPlayers(Date startDate, Date endDate) {
		Integer temp = affiliateNetworkRepository.findTotalRegPlayers(startDate, endDate);
		if (temp == null)
			temp = 0;
		return temp;

	}

}
