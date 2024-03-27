package coffee.backoffice.report.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.repository.dao.AllTransactionDao;
import coffee.backoffice.report.vo.res.BettingHistoriesRes;
import coffee.backoffice.report.vo.res.PlayerReportRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.ExcelUtils;

@Service
public class BettingHistoriesService {

	@Autowired
	private AllTransactionDao allTransactionDao;

	public BettingHistoriesRes getBettingHistories(DatatableRequest req) {
		BettingHistoriesRes res = new BettingHistoriesRes();
		DataTableResponse<BettingHistoriesRes.DataListRes> pg = allTransactionDao.getBettingHistoriesResPaginate(req);
		DataTableResponse<BettingHistoriesRes.DataListRes> dataTable = new DataTableResponse<>();
		dataTable.setRecordsTotal(pg.getRecordsTotal());
		dataTable.setDraw(pg.getDraw());
		dataTable.setData(pg.getData());
		res.setDataList(dataTable);
		List<BettingHistoriesRes.DataListRes> dataListRes = pg.getData();
		BettingHistoriesRes.DataSummaryRes dataSummaryRes = new BettingHistoriesRes.DataSummaryRes();
		BettingHistoriesRes.DataSummaryRes dataSummaryTotalRes = allTransactionDao.getBettingHistoriesTotalRes(req);
		for (BettingHistoriesRes.DataListRes dataRes : dataListRes) {
			dataSummaryRes.setSubtotalBetAmount(dataSummaryRes.getSubtotalBetAmount().add(dataRes.getValidBet()));
			dataSummaryRes.setSubtotalValidBet(dataSummaryRes.getSubtotalValidBet().add(dataRes.getValidBet()));
			dataSummaryRes.setSubtotalWinLoss(dataSummaryRes.getSubtotalWinLoss().add(dataRes.getWinLoss()));
		}
		dataSummaryRes.setTotalBetAmount(dataSummaryTotalRes.getTotalBetAmount());
		dataSummaryRes.setTotalValidBet(dataSummaryTotalRes.getTotalValidBet());
		dataSummaryRes.setTotalWinLoss(dataSummaryTotalRes.getTotalWinLoss());
		res.setSummary(dataSummaryRes);
		return res;
	}

	@SuppressWarnings("deprecation")
	public ByteArrayOutputStream exportExcelBetting() throws Exception {
//		Date sd = new Date(startDate);
//		Date ed = new Date(endDate);
//		System.out.println(sd);
//		System.out.println(ed);
//		System.out.println(username);
//		System.out.println(ticketId);
		XSSFWorkbook workbook = new XSSFWorkbook();
		CellStyle tdLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle tdRight = ExcelUtils.createRightCellStyle(workbook);
		CellStyle tdCenter = ExcelUtils.createCenterCellStyle(workbook);
		CellStyle TopicCenter = ExcelUtils.createTopicCenterStyle(workbook);

		// style sheet & font
		Sheet sheet = workbook.createSheet();
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		CellStyle cellHeaderStyle = ExcelUtils.createThColorStyle(workbook, new XSSFColor(Color.WHITE));
		cellHeaderStyle.setFont(headerFont);
		tdRight.setFont(headerFont);
		tdLeft.setFont(headerFont);
		tdCenter.setFont(headerFont);
		TopicCenter.setFont(headerFont);

		// create Row & Col
		int rowNum = 0;
		int cellNum = 0;
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);

		XSSFFont fontSize = workbook.createFont();
		XSSFFont fontSizeBold = workbook.createFont();
		XSSFCellStyle styleSize = workbook.createCellStyle();
		XSSFCellStyle styleBold = workbook.createCellStyle();
		fontSize.setFontHeightInPoints((short) 12);
		styleSize = workbook.createCellStyle();
		styleSize.setFont(fontSize);

		fontSizeBold.setFontHeightInPoints((short) 12);
		styleBold = workbook.createCellStyle();
		styleBold.setFont(fontSizeBold);
		fontSizeBold.setBold(true);

		row = sheet.createRow(rowNum++);
		cell = row.createCell(cellNum++);
		cell.setCellValue("Bet Time");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Ticket ID");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Provider");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Product Type");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Game Group");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Game");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Player ID");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Stake");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Valid Bet");
		cell.setCellStyle(styleBold);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Win/Loss");
		cell.setCellStyle(styleBold);
		cellNum = 0;
		// set width to all Column
		int width = 70;
		sheet.setColumnWidth(0, width * 20);
		for (int i = 1; i <= 10; i++) {
			sheet.setColumnWidth(i, width * 80);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
