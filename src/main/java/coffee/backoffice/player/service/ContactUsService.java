package coffee.backoffice.player.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import coffee.backoffice.player.model.ContactUs;
import coffee.backoffice.player.repository.dao.ContactUsDao;
import coffee.backoffice.player.repository.jpa.ContactUsRepository;
import framework.model.Column;
import framework.model.ColumnValueModel;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ExcelModel;
import framework.utils.ConvertDateUtils;
import framework.utils.ExcelUtils;

@Service
public class ContactUsService {

	@Autowired
	private ContactUsRepository contactUsRepository;

	@Autowired
	private ContactUsDao contactUsDao;

	public void saveContact(ContactUs data) {
		contactUsRepository.save(data);
	}

	public DataTableResponse<ContactUs> getContactUs(DatatableRequest req) {
		DataTableResponse<ContactUs> paginateData = contactUsDao.contactUsPaginate(req);
		DataTableResponse<ContactUs> dataTable = new DataTableResponse<>();
		List<ContactUs> data = paginateData.getData();
		dataTable.setRecordsTotal(paginateData.getRecordsTotal());
		dataTable.setDraw(paginateData.getDraw());
		dataTable.setData(data);
		return paginateData;
	}

	@SuppressWarnings("deprecation")
	public ByteArrayOutputStream exportContactList() throws IOException {
		List<ContactUs> list = contactUsRepository.findAll();
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
		CellStyle cellHeaderStyle = ExcelUtils.createThColorStyle(workbook, new XSSFColor(new Color(254, 230, 188)));
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
		cellHeaderStyle.setFont(fontSizeBold);

		row = sheet.createRow(rowNum++);
		cell = row.createCell(cellNum++);
		cell.setCellValue("Player ID");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Real Name");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Level Group");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Currency");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Tag Name");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Agent (old)");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Agent team");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Affiliate Upline");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Email");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Mobile");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("SMS Validate");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("IM1");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("IM2");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("City");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Bank Account Number");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Registered Date");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Total Balance");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Total Deposit");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Total Deposit Count");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Total Withdraw");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Total Withdraw Count");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Last Login");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);

		cell = row.createCell(cellNum++);
		cell.setCellValue("Status");
		cell.setCellStyle(TopicCenter);
		cell.setCellStyle(cellHeaderStyle);
		cellNum = 0;

		// for row by list

		for (ContactUs data : list) {
			cellNum = 0;
			// set data to cell
			row = sheet.createRow(rowNum++);
			cell = row.createCell(cellNum++);
			cell.setCellValue(StringUtils.isBlank(data.getUsername()) ? "" : data.getUsername());
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(StringUtils.isBlank(data.getRealName()) ? "" : data.getRealName());
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(StringUtils.isBlank(data.getGroupName()) ? "" : data.getGroupName());
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(StringUtils.isBlank(data.getEmail()) ? "" : data.getEmail());
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(StringUtils.isBlank(data.getMobilePhone()) ? "" : data.getMobilePhone());
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(StringUtils.isBlank(data.getBankCode()) ? "" : data.getBankCode());
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(
					data.getRegisteredDate() != null
							? ConvertDateUtils.formatDateToStringEn(data.getRegisteredDate(),
									ConvertDateUtils.DD_MM_YYYY_HHMMSS)
							: "");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(0);
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(0);
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(data.getDepositCount() != null ? data.getDepositCount() : 0);
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(0);
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(data.getWithdrawCount() == null ? 0 : data.getWithdrawCount());
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(
					data.getLastLogin() != null
							? ConvertDateUtils.formatDateToStringEn(data.getLastLogin(),
									ConvertDateUtils.DD_MM_YYYY_HHMMSS)
							: "");
			cell.setCellStyle(tdCenter);

			cell = row.createCell(cellNum++);
			cell.setCellValue(StringUtils.isBlank(data.getStatus()) ? "" : data.getStatus());
			cell.setCellStyle(tdCenter);

			// set data to cell
		}

		// set width to all Column
		int width = 70;
		sheet.setColumnWidth(0, width * 20);
		for (int i = 0; i <= 23; i++) {
			sheet.setColumnWidth(i, width * 80);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
}
