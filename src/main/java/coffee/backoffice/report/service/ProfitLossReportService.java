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
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.report.repository.dao.ProfitLossReportDao;
import coffee.backoffice.report.vo.res.ProfitLossReportRes;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.utils.ConvertDateUtils;

@Service
public class ProfitLossReportService {

	@Autowired
	private AffiliateService affiliateService;

	@Autowired
	private AllTransactionService allTransactionService;
	
	@Autowired
	private ProfitLossReportDao profitLossReportDao;

	public ProfitLossReportRes getProfitLossReport(String firstDayDate, String lastDayDate) {
		ProfitLossReportRes res = new ProfitLossReportRes();
		Date first = ConvertDateUtils.parseStringToDate(firstDayDate, ConvertDateUtils.YYYY_MM_DD_HHMMSS_SLASH,ConvertDateUtils.LOCAL_EN);
		Date last =ConvertDateUtils.parseStringToDate(lastDayDate, ConvertDateUtils.YYYY_MM_DD_HHMMSS_SLASH,ConvertDateUtils.LOCAL_EN);
		List<ProfitLossReportRes.DataListRes> dataList = new ArrayList<ProfitLossReportRes.DataListRes>();
		ProfitLossReportRes.DataSummaryRes summary = new ProfitLossReportRes.DataSummaryRes();
		Long difference = last.getTime() - first.getTime();
		Long daysBetween = (difference / (1000 * 60 * 60 * 24));
		for (int i = 0; i <= daysBetween; i++) {
			Date sd = addDays(first, i);
			Calendar timeSD = Calendar.getInstance();
			timeSD.setTime(sd);
			Date ed = addDays(first, i + 1);
			Calendar timeED = Calendar.getInstance();
			timeED.setTime(ed);
			Integer tempDeposit = 0;
			List<AffiliateNetwork> networkDataList = affiliateService.getAffiliateNetworkBetween(sd, ed);
			for (AffiliateNetwork networkDataObj : networkDataList) {
				List<TransactionList> data = allTransactionService.getTransactionByUsernameAndTypeBetween(
						networkDataObj.getUsername(), TRANSACTION_TYPE.DEPOSIT, sd, ed);
				if (data.size() > 0) {
					tempDeposit += 1;
				}
			}
			ProfitLossReportRes.DataListRes dataObj = new ProfitLossReportRes.DataListRes();
			dataObj.setSummarydate(ConvertDateUtils.formatDateToStringEn(sd, ConvertDateUtils.DD_MM_YYYY));
			dataObj.setRegistercount(networkDataList.size());
			dataObj.setFirstdepositcount(tempDeposit);
			dataObj.setDepositcount(allTransactionService.countTransactionTypeByType(sd, ed, TRANSACTION_TYPE.DEPOSIT));
			dataObj.setDeposit(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.DEPOSIT, sd, ed));
			dataObj.setWithdrawcount(
					allTransactionService.countTransactionTypeByType(sd, ed, TRANSACTION_TYPE.WITHDRAW));
			dataObj.setWithdraw(allTransactionService.totalSubBalanceByType(TRANSACTION_TYPE.WITHDRAW, sd, ed)
					.setScale(2, RoundingMode.HALF_UP));
			dataObj.setAdjustment(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.MANUAL_ADD, sd, ed));
			dataObj.setValidbet(allTransactionService.getTotalValidBets(sd, ed).setScale(2, RoundingMode.HALF_UP));
			dataObj.setCompanywinloss(allTransactionService.getTotalWinLoss(sd, ed).setScale(2, RoundingMode.HALF_UP));
			dataObj.setDepositbonus(BigDecimal.ZERO);
			dataObj.setBonus(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.PROMOTION_BONUS, sd, ed));
			dataObj.setRebate(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.REBATE, sd, ed));
			dataObj.setCashback(allTransactionService.totalAddBalanceByType(TRANSACTION_TYPE.CASHBACK, sd, ed));
			dataList.add(dataObj);

			summary.setRegistercount(summary.getRegistercount() + dataObj.getRegistercount());
			summary.setFirstdepositcount(summary.getFirstdepositcount() + dataObj.getFirstdepositcount());
			summary.setDepositcount(summary.getDepositcount() + dataObj.getDepositcount());
			summary.setDeposit(summary.getDeposit().add(dataObj.getDeposit()));
			summary.setWithdrawcount(summary.getWithdrawcount() + dataObj.getWithdrawcount());
			summary.setWithdraw(summary.getWithdraw().add(dataObj.getWithdraw()));
			summary.setAdjustment(summary.getAdjustment().add(dataObj.getAdjustment()));
			summary.setValidbet(summary.getValidbet().add(dataObj.getValidbet()));
			summary.setCompanywinloss(summary.getCompanywinloss().add(dataObj.getCompanywinloss()));
			summary.setDepositbonus(summary.getDepositbonus().add(dataObj.getDepositbonus()));
			summary.setBonus(summary.getBonus().add(dataObj.getBonus()));
			summary.setRebate(summary.getRebate().add(dataObj.getRebate()));
			summary.setCashback(summary.getCashback().add(dataObj.getCashback()));
		}
		res.setDataList(dataList);
		res.setSummary(summary);
		return res;
	}

	// fucntion add day
	public Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	
	public ProfitLossReportRes getProfitLossMonthlyReport(String firstDayDate, String lastDayDate) {
		ProfitLossReportRes res = new ProfitLossReportRes();
		List<ProfitLossReportRes.DataListRes> dataList = profitLossReportDao.getProfitLossReportList(firstDayDate,lastDayDate);
		
		ProfitLossReportRes.DataSummaryRes summary = new ProfitLossReportRes.DataSummaryRes();
		for(ProfitLossReportRes.DataListRes dataListRes :dataList) {
			summary.setRegistercount(summary.getRegistercount() + dataListRes.getRegistercount());
			summary.setFirstdepositcount(summary.getFirstdepositcount() + dataListRes.getFirstdepositcount());
			summary.setDepositcount(summary.getDepositcount() + dataListRes.getDepositcount());
			summary.setDeposit(summary.getDeposit().add(dataListRes.getDeposit()));
			summary.setWithdrawcount(summary.getWithdrawcount() + dataListRes.getWithdrawcount());
			summary.setWithdraw(summary.getWithdraw().add(dataListRes.getWithdraw()));
			summary.setAdjustment(summary.getAdjustment().add(dataListRes.getAdjustment()));
			summary.setValidbet(summary.getValidbet().add(dataListRes.getValidbet()));
			summary.setCompanywinloss(summary.getCompanywinloss().add(dataListRes.getCompanywinloss()));
			summary.setDepositbonus(summary.getDepositbonus().add(dataListRes.getDepositbonus()));
			summary.setBonus(summary.getBonus().add(dataListRes.getBonus()));
			summary.setRebate(summary.getRebate().add(dataListRes.getRebate()));
			summary.setCashback(summary.getCashback().add(dataListRes.getCashback()));
		}
		res.setDataList(dataList);
		res.setSummary(summary);
		return res;
	}
}
