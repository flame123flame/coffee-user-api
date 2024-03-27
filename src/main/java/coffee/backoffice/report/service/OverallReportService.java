package coffee.backoffice.report.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.model.AffiliateNetwork;
import coffee.backoffice.affiliate.service.AffiliateService;
import coffee.backoffice.casino.model.GameProductTypeNoIcon;
import coffee.backoffice.casino.model.GameProviderNoIcon;
import coffee.backoffice.casino.service.GameProductTypeService;
import coffee.backoffice.casino.service.GameProviderService;
import coffee.backoffice.casino.service.ProductMappingProviderService;
import coffee.backoffice.casino.vo.res.ProductMapProviderRes;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.report.vo.res.CompanySurplusRes;
import coffee.backoffice.report.vo.res.DepositWithdrawRes;
import coffee.backoffice.report.vo.res.OverallCompanyProfitLossRes;
import coffee.backoffice.report.vo.res.OverallGameWinLossRes;
import coffee.backoffice.report.vo.res.OverallGameWinLossRes.DataListRes;
import coffee.backoffice.report.vo.res.OverallReportRes;
import coffee.backoffice.report.vo.res.RegDepositRes;
import coffee.backoffice.report.vo.res.ValidBestsWinLossRes;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.ConvertDateUtils;

@Service
public class OverallReportService {

	@Autowired
	private AllTransactionService allTransactionService;

	@Autowired
	private AffiliateService affiliateService;

	@Autowired
	private ProductMappingProviderService productMappingProviderService;

	@Autowired
	private GameProviderService gameProviderService;

	@Autowired
	private GameProductTypeService gameProductTypeService;

	@SuppressWarnings("deprecation")
	public OverallReportRes getTotalSummary(Date firstDayDate, Date lastDayDate) {
		OverallReportRes res = new OverallReportRes();
		Integer lastDay = lastDayDate.getDate();
		Calendar time = Calendar.getInstance();
		time.setTime(lastDayDate);
		time.set(Calendar.DAY_OF_MONTH, lastDay + 1);
		Date ed = time.getTime();

		String startDate = ConvertDateUtils.formatDateToStringEn(firstDayDate, ConvertDateUtils.DD_MM_YYYY_HHMMSS);
		String endDate = ConvertDateUtils.formatDateToStringEn(ed, ConvertDateUtils.DD_MM_YYYY);
		String yearMonth = ConvertDateUtils.formatDateToStringEn(firstDayDate, ConvertDateUtils.MM_YYYY_SLASH);

		res.setCompanytotal(allTransactionService.getCompanyProfitLoss(firstDayDate, ed));
		res.setCompanytotalgr(BigDecimal.ZERO);
		res.setDeposit(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.DEPOSIT, firstDayDate, ed)
				.setScale(2, RoundingMode.HALF_UP));
		res.setDepositgr(BigDecimal.ZERO);
		res.setRegistercount(affiliateService.getTotalRegPlayers(firstDayDate, ed));
		res.setRegistercountgr(BigDecimal.ZERO);
		res.setSummarydate(yearMonth);
		res.setTotalwinloss(allTransactionService.getTotalWinLoss(firstDayDate, ed).setScale(2, RoundingMode.HALF_UP));
		res.setTotalwinlossgr(BigDecimal.ZERO);
		res.setValidbet(allTransactionService.getTotalValidBets(firstDayDate, ed).setScale(2, RoundingMode.HALF_UP));
		res.setValidbetgr(BigDecimal.ZERO);
		res.setWithdraw(allTransactionService.totalSubBalanceByType(TRANSACTION_TYPE.WITHDRAW, firstDayDate, ed)
				.setScale(2, RoundingMode.HALF_UP));
		res.setWithdrawgr(BigDecimal.ZERO);
		return res;
	}

	@SuppressWarnings("deprecation")
	public List<RegDepositRes> getRegDeposit(Date firstDayDate, Date lastDayDate) {
		List<RegDepositRes> res = new ArrayList<RegDepositRes>();
		Integer lastDay = lastDayDate.getDate();
		for (int i = 1; i <= lastDay; i++) {
			Calendar time = Calendar.getInstance();
			time.setTime(firstDayDate);
			time.set(Calendar.DAY_OF_MONTH, i);
			Date sd = time.getTime();
			time.set(Calendar.DAY_OF_MONTH, i + 1);
			Date ed = time.getTime();
			Integer tempDeposit = 0;
			List<AffiliateNetwork> networkDataList = affiliateService.getAffiliateNetworkBetween(sd, ed);
			for (AffiliateNetwork networkDataObj : networkDataList) {
				List<TransactionList> data = allTransactionService.getTransactionByUsernameAndTypeBetween(
						networkDataObj.getUsername(), TRANSACTION_TYPE.DEPOSIT, sd, ed);
				if (data.size() > 0) {
					tempDeposit += 1;
				}
			}
			RegDepositRes newDateRes = new RegDepositRes();
			newDateRes.setDepositcount(tempDeposit);
			newDateRes.setRegistercount(networkDataList.size());
			newDateRes.setSummarydate(ConvertDateUtils.formatDateToStringEn(sd, ConvertDateUtils.DD_MM_YYYY));
			res.add(newDateRes);
		}
		return res;
	}

//	public Date addDays(Date date, int days) {
//		GregorianCalendar cal = new GregorianCalendar();
//		cal.setTime(date);
//		cal.add(Calendar.DATE, days);
//		return cal.getTime();
//	}

	@SuppressWarnings("deprecation")
	public List<DepositWithdrawRes> getDepositWithdraw(Date firstDayDate, Date lastDayDate) {
		List<DepositWithdrawRes> res = new ArrayList<DepositWithdrawRes>();
		Integer lastDay = lastDayDate.getDate();
		for (int i = 1; i <= lastDay; i++) {
			Calendar time = Calendar.getInstance();
			time.setTime(firstDayDate);
			time.set(Calendar.DAY_OF_MONTH, i);
			Date sd = time.getTime();
			time.set(Calendar.DAY_OF_MONTH, i + 1);
			Date ed = time.getTime();
			DepositWithdrawRes newDataRes = new DepositWithdrawRes();
			newDataRes.setDeposit(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.DEPOSIT, sd, ed));
			newDataRes.setWithdraw(allTransactionService.totalSubBalanceByType(TRANSACTION_TYPE.WITHDRAW, sd, ed));
			newDataRes.setSummaryDate(ConvertDateUtils.formatDateToStringEn(sd, ConvertDateUtils.DD_MM_YYYY));
			res.add(newDataRes);
		}
		return res;
	}

	@SuppressWarnings("deprecation")
	public List<CompanySurplusRes> getCompanyTotal(Date firstDayDate, Date lastDayDate) {
		List<CompanySurplusRes> res = new ArrayList<CompanySurplusRes>();
		Integer lastDay = lastDayDate.getDate();
		for (int i = 1; i <= lastDay; i++) {
			Calendar time = Calendar.getInstance();
			time.setTime(firstDayDate);
			time.set(Calendar.DAY_OF_MONTH, i);
			Date sd = time.getTime();
			time.set(Calendar.DAY_OF_MONTH, i + 1);
			Date ed = time.getTime();

			CompanySurplusRes newDataRes = new CompanySurplusRes();
			newDataRes.setSummaryDate(ConvertDateUtils.formatDateToStringEn(sd, ConvertDateUtils.DD_MM_YYYY));
			newDataRes.setCompanyTotal(allTransactionService.getCompanyProfitLoss(sd, ed));
			res.add(newDataRes);
		}
		return res;
	}

	@SuppressWarnings("deprecation")
	public List<ValidBestsWinLossRes> getValidBestsWinLoss(Date firstDayDate, Date lastDayDate, String productCode) {
		List<ValidBestsWinLossRes> res = new ArrayList<ValidBestsWinLossRes>();
		Integer lastDay = lastDayDate.getDate();
		Calendar time = Calendar.getInstance();
		time.setTime(lastDayDate);
		time.set(Calendar.DAY_OF_MONTH, lastDay + 1);
		Date ed = time.getTime();
		if ("ALL".equals(productCode)) {
			List<GameProductTypeNoIcon> product = gameProductTypeService.getDropdownGameProductType();
			for (GameProductTypeNoIcon pdData : product) {
				ProductMapProviderRes providerRes = productMappingProviderService.getByProductCode(pdData.getCode());
				for (String providerCode : providerRes.getProviderCode()) {
					ValidBestsWinLossRes newData = new ValidBestsWinLossRes();
					GameProviderNoIcon dataPV = gameProviderService.getGameProviderByCodeNoIcon(providerCode);
					ValidBestsWinLossRes data = allTransactionService.getValidBestsWinLoss(firstDayDate, ed,
							providerCode);
					newData.setName(dataPV.getNameEn());
					newData.setTotalwinloss(data.getTotalwinloss());
					newData.setValidbet(data.getValidbet());
					res.add(newData);
				}
			}
		} else {
			ValidBestsWinLossRes newData = new ValidBestsWinLossRes();
			newData.setName("");
			newData.setTotalwinloss(BigDecimal.ZERO);
			newData.setValidbet(BigDecimal.ZERO);
			res.add(newData);
		}
		return res;
	}

	@SuppressWarnings("deprecation")
	public OverallGameWinLossRes getOverallGameWinLoss(Date firstDayDate, Date lastDayDate, String type) {
		Integer lastDay = lastDayDate.getDate();
		Calendar time = Calendar.getInstance();
		time.setTime(lastDayDate);
		time.set(Calendar.DAY_OF_MONTH, lastDay + 1);
		Date ed = time.getTime();

		OverallGameWinLossRes res = new OverallGameWinLossRes();
		List<OverallGameWinLossRes.DataListRes> dataList = new ArrayList<OverallGameWinLossRes.DataListRes>();
		if (type.equals("product")) {
			List<GameProductTypeNoIcon> product = gameProductTypeService.getDropdownGameProductType();
			for (GameProductTypeNoIcon pdData : product) {
				OverallGameWinLossRes.DataListRes newRes = new OverallGameWinLossRes.DataListRes();
				BigDecimal totalwinloss = BigDecimal.ZERO;
				BigDecimal totalvalidbet = BigDecimal.ZERO;
				Integer totalPlayer = 0;
				Integer totalTxn = 0;
				ProductMapProviderRes providerRes = productMappingProviderService.getByProductCode(pdData.getCode());
				for (String providerCode : providerRes.getProviderCode()) {
					ValidBestsWinLossRes data = allTransactionService.getValidBestsWinLoss(firstDayDate, ed,
							providerCode);
					Integer countPlayer = allTransactionService.getCountPlayer(firstDayDate, ed, providerCode);
					Integer countTxn = allTransactionService.getCountTxn(firstDayDate, ed, providerCode);

					totalwinloss = totalwinloss.add(data.getTotalwinloss());
					totalvalidbet = totalvalidbet.add(data.getValidbet());
					totalPlayer += countPlayer;
					totalTxn += countTxn;
				}
				newRes.setProductType(pdData.getCode());
				newRes.setProductTypeName(pdData.getNameEn());
				newRes.setTotalBet(totalvalidbet);
				newRes.setTotalPlayer(totalPlayer);
				newRes.setTotalTxn(totalTxn);
				newRes.setTotalWinLoss(totalwinloss);
				newRes.setValidBet(totalvalidbet);
				dataList.add(newRes);
			}
		} else {
			List<GameProviderNoIcon> providerData = gameProviderService.getDropdownProvider();
			for (GameProviderNoIcon pvData : providerData) {
				OverallGameWinLossRes.DataListRes newRes = new OverallGameWinLossRes.DataListRes();
				ValidBestsWinLossRes data = allTransactionService.getValidBestsWinLoss(firstDayDate, ed,
						pvData.getCode());
				Integer countPlayer = allTransactionService.getCountPlayer(firstDayDate, ed, pvData.getCode());
				Integer countTxn = allTransactionService.getCountTxn(firstDayDate, ed, pvData.getCode());
				newRes.setProviderCode(pvData.getCode());
				newRes.setProviderName(pvData.getNameEn());
				newRes.setTotalBet(data.getValidbet());
				newRes.setTotalPlayer(countPlayer);
				newRes.setTotalTxn(countTxn);
				newRes.setTotalWinLoss(data.getTotalwinloss());
				newRes.setValidBet(data.getValidbet());
				dataList.add(newRes);
			}
		}

		OverallGameWinLossRes.DataSummaryRes dataSummaryRes = new OverallGameWinLossRes.DataSummaryRes();
		res.setDataList(dataList);
		res.setSummary(dataSummaryRes);
		return res;
	}

	@SuppressWarnings("deprecation")
	public OverallCompanyProfitLossRes getCompanyProfitLossTable(Date firstDayDate, Date lastDayDate) {
		OverallCompanyProfitLossRes res = new OverallCompanyProfitLossRes();
		Integer lastDay = lastDayDate.getDate();
		Calendar time = Calendar.getInstance();
		time.setTime(lastDayDate);
		time.set(Calendar.DAY_OF_MONTH, lastDay + 1);
		Date ed = time.getTime();
		Integer tempDeposit = 0;
		List<AffiliateNetwork> networkDataList = affiliateService.getAffiliateNetworkBetween(firstDayDate, ed);
		for (AffiliateNetwork networkDataObj : networkDataList) {
			List<TransactionList> data = allTransactionService.getTransactionByUsernameAndTypeBetween(
					networkDataObj.getUsername(), TRANSACTION_TYPE.DEPOSIT, firstDayDate, ed);
			if (data.size() > 0) {
				tempDeposit += 1;
			}
		}
		res.setRegisterCount(tempDeposit);
		res.setFirstDeposit(networkDataList.size());
		res.setDepositPeople(
				allTransactionService.countUsernameByTypeGroupByUsername(firstDayDate, ed, TRANSACTION_TYPE.DEPOSIT));
		res.setDepositCount(allTransactionService.countTransactionTypeByType(firstDayDate, ed, TRANSACTION_TYPE.DEPOSIT));
		res.setDeposit(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.DEPOSIT, firstDayDate, ed));
		res.setWithdraw(allTransactionService.totalSubBalanceByType(TRANSACTION_TYPE.WITHDRAW, firstDayDate, ed)
				.setScale(2, RoundingMode.HALF_UP));
		res.setAdjustment(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.MANUAL_ADD, firstDayDate, ed));
		res.setValidBet(allTransactionService.getTotalValidBets(firstDayDate, ed).setScale(2, RoundingMode.HALF_UP));
		res.setTotalWinLoss(allTransactionService.getTotalWinLoss(firstDayDate, ed).setScale(2, RoundingMode.HALF_UP));
		res.setRebate(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.REBATE, firstDayDate, ed));
		res.setCashback(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.CASHBACK, firstDayDate, ed));
		res.setBonus(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.PROMOTION_BONUS, firstDayDate, ed));

		BigDecimal compabyLoss = res.getRebate().add(res.getCashback()).add(res.getBonus());
		res.setCompanyTotal(res.getTotalWinLoss().multiply(new BigDecimal(-1)).subtract(compabyLoss));
		return res;
	}
}
