package coffee.backoffice.report.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.report.service.BettingHistoriesService;
import coffee.backoffice.report.vo.res.BettingHistoriesRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/betting-histories/")
@Slf4j
public class BettingHistoriesController {

	@Autowired
	private BettingHistoriesService bettingHistoriesService;

	@PostMapping("betting-report")
	public ResponseData<BettingHistoriesRes> getBettingReport(@RequestBody DatatableRequest req) {
		ResponseData<BettingHistoriesRes> response = new ResponseData<BettingHistoriesRes>();
		try {
			response.setData(bettingHistoriesService.getBettingHistories(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BettingHistoriesController.getBettingReport");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API BettingHistoriesController.getBettingReport :" + e);
		}
		return response;
	}

	@GetMapping("export-excel-betting")
	public void exportExcelBetting(HttpServletResponse response) {
		ResponseData<?> responseMethod = new ResponseData<>();
		try {
			String fileName = null;
			ByteArrayOutputStream outByteStream = null;

			// set fileName
			fileName = URLEncoder.encode("summary_report", "UTF-8");
			// write it as an excel attachment
			outByteStream = bettingHistoriesService.exportExcelBetting();

			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/octet-stream");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			outStream.close();
			responseMethod.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseMethod.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BettingHistoriesController => exportExcelBetting");
		} catch (Exception e) {
			responseMethod.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseMethod.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API BettingHistoriesController => exportExcelBetting :" + e);
		}
	}

}