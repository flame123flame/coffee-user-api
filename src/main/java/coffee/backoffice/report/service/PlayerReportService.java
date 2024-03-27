package coffee.backoffice.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.report.repository.dao.PlayerReportDao;
import coffee.backoffice.report.vo.res.PlayerReportRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;

@Service
public class PlayerReportService {
	@Autowired
	private PlayerReportDao playerReportDao;

	public PlayerReportRes getPlayerReport(DatatableRequest req) {
		PlayerReportRes res = new PlayerReportRes();
		DataTableResponse<PlayerReportRes.DataLisrRes> pg = playerReportDao.getPlayerReportResPaginate(req);
		DataTableResponse<PlayerReportRes.DataLisrRes> dataTable = new DataTableResponse<>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		res.setDataList(dataTable);
		List<PlayerReportRes.DataLisrRes> dataListRes = pg.getData();
		PlayerReportRes.DataSummaryRes dataSummaryTotalRes = playerReportDao.getPlayerReportTotalRes(req);
		PlayerReportRes.DataSummaryRes dataSummaryRes = new PlayerReportRes.DataSummaryRes();
		for (PlayerReportRes.DataLisrRes dataRes : dataListRes) {
			dataSummaryRes.setSubTotalBets(dataSummaryRes.getSubTotalBets().add(dataRes.getTotalBets()));
			dataSummaryRes.setSubTotalCashback(dataSummaryRes.getSubTotalCashback().add(dataRes.getCashback()));
			dataSummaryRes.setSubTotalDepositAmt(dataSummaryRes.getSubTotalDepositAmt().add(dataRes.getDepositAmt()));
			dataSummaryRes
					.setSubTotalDepositCount(dataSummaryRes.getSubTotalDepositCount() + dataRes.getDepositCount());
			dataSummaryRes.setSubTotalRebate(dataSummaryRes.getSubTotalRebate().add(dataRes.getRebate()));
			dataSummaryRes.setSubTotalValidBets(dataSummaryRes.getSubTotalValidBets().add(dataRes.getValidBets()));
			dataSummaryRes.setSubTotalWinLoss(dataSummaryRes.getSubTotalWinLoss().add(dataRes.getWinLoss()));
			dataSummaryRes
					.setSubTotalWithdrawAmt(dataSummaryRes.getSubTotalWithdrawAmt().add(dataRes.getWithdrawAmt()));
			dataSummaryRes
					.setSubTotalWithdrawCount(dataSummaryRes.getSubTotalWithdrawCount() + dataRes.getWithdrawCount());
		}
		dataSummaryRes.setTotalBets(dataSummaryTotalRes.getTotalBets());
		dataSummaryRes.setTotalCashback(dataSummaryTotalRes.getTotalCashback());
		dataSummaryRes.setTotalDepositAmt(dataSummaryTotalRes.getTotalDepositAmt());
		dataSummaryRes.setTotalDepositCount(dataSummaryTotalRes.getTotalDepositCount());
		dataSummaryRes.setTotalRebate(dataSummaryTotalRes.getTotalRebate());
		dataSummaryRes.setTotalValidBets(dataSummaryTotalRes.getTotalValidBets());
		dataSummaryRes.setTotalWinLoss(dataSummaryTotalRes.getTotalWinLoss());
		dataSummaryRes.setTotalWithdrawAmt(dataSummaryTotalRes.getTotalWithdrawAmt());
		dataSummaryRes.setTotalWithdrawCount(dataSummaryTotalRes.getTotalWithdrawCount());

		res.setSummary(dataSummaryRes);
		return res;
	}
}
