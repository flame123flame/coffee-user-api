package coffee.backoffice.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.report.repository.dao.GameReportDao;
import coffee.backoffice.report.vo.res.GameReportRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;

@Service
public class GameReportService {

	@Autowired
	private GameReportDao gameReportDao;

	public GameReportRes getGameReportDaily(DatatableRequest req) {
		GameReportRes res = new GameReportRes();
		DataTableResponse<GameReportRes.DataLisrRes> pg = gameReportDao.getGameReportDaily(req);
		DataTableResponse<GameReportRes.DataLisrRes> dataTable = new DataTableResponse<GameReportRes.DataLisrRes>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		res.setDataList(dataTable);
		GameReportRes.DataSummaryRes dataSummaryTotalRes = gameReportDao.getGameReportDailyTotalRes(req);
		GameReportRes.DataSummaryRes dataSummaryRes = new GameReportRes.DataSummaryRes();
		dataSummaryRes.setPlayercount(dataSummaryTotalRes.getPlayercount());
		dataSummaryRes.setTotaltxn(dataSummaryTotalRes.getTotaltxn());
		dataSummaryRes.setTotalbet(dataSummaryTotalRes.getTotalbet());
		dataSummaryRes.setValidbet(dataSummaryTotalRes.getValidbet());
		dataSummaryRes.setTotalwinloss(dataSummaryTotalRes.getTotalwinloss());
		dataSummaryRes.setTotalwin(dataSummaryTotalRes.getTotalwin());
		dataSummaryRes.setTotalloss(dataSummaryTotalRes.getTotalloss());
		dataSummaryRes.setTotalprizewon(dataSummaryTotalRes.getTotalprizewon());
		res.setSummary(dataSummaryRes);
		return res;
	}

	public GameReportRes getGameReportMonthly(DatatableRequest req) {
		GameReportRes res = new GameReportRes();
		DataTableResponse<GameReportRes.DataLisrRes> pg = gameReportDao.getGameReportMonthly(req);
		DataTableResponse<GameReportRes.DataLisrRes> dataTable = new DataTableResponse<GameReportRes.DataLisrRes>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		res.setDataList(dataTable);
		GameReportRes.DataSummaryRes dataSummaryTotalRes = gameReportDao.getGameReportMonthlyTotalRes(req);
		GameReportRes.DataSummaryRes dataSummaryRes = new GameReportRes.DataSummaryRes();
		dataSummaryRes.setPlayercount(dataSummaryTotalRes.getPlayercount());
		dataSummaryRes.setTotaltxn(dataSummaryTotalRes.getTotaltxn());
		dataSummaryRes.setTotalbet(dataSummaryTotalRes.getTotalbet());
		dataSummaryRes.setValidbet(dataSummaryTotalRes.getValidbet());
		dataSummaryRes.setTotalwinloss(dataSummaryTotalRes.getTotalwinloss());
		dataSummaryRes.setTotalwin(dataSummaryTotalRes.getTotalwin());
		dataSummaryRes.setTotalloss(dataSummaryTotalRes.getTotalloss());
		dataSummaryRes.setTotalprizewon(dataSummaryTotalRes.getTotalprizewon());
		res.setSummary(dataSummaryRes);
		return res;
	}

	public GameReportRes getGameReportGame(DatatableRequest req) {
		GameReportRes res = new GameReportRes();
		DataTableResponse<GameReportRes.DataLisrRes> pg = gameReportDao.getGameReportGames(req);
		DataTableResponse<GameReportRes.DataLisrRes> dataTable = new DataTableResponse<GameReportRes.DataLisrRes>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		res.setDataList(dataTable);
		GameReportRes.DataSummaryRes dataSummaryTotalRes = gameReportDao.getGameReportGamesTotalRes(req);
		GameReportRes.DataSummaryRes dataSummaryRes = new GameReportRes.DataSummaryRes();
		dataSummaryRes.setPlayercount(dataSummaryTotalRes.getPlayercount());
		dataSummaryRes.setTotaltxn(dataSummaryTotalRes.getTotaltxn());
		dataSummaryRes.setTotalbet(dataSummaryTotalRes.getTotalbet());
		dataSummaryRes.setValidbet(dataSummaryTotalRes.getValidbet());
		dataSummaryRes.setTotalwinloss(dataSummaryTotalRes.getTotalwinloss());
		dataSummaryRes.setTotalwin(dataSummaryTotalRes.getTotalwin());
		dataSummaryRes.setTotalloss(dataSummaryTotalRes.getTotalloss());
		dataSummaryRes.setTotalprizewon(dataSummaryTotalRes.getTotalprizewon());
		res.setSummary(dataSummaryRes);
		return res;
	}
}
