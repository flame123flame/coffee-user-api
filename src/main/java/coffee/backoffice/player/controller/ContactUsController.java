package coffee.backoffice.player.controller;

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

import coffee.backoffice.player.model.ContactUs;
import coffee.backoffice.player.service.ContactUsService;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/contact-us/")
@Slf4j
public class ContactUsController {

	@Autowired
	private ContactUsService contactUsService;

	@PostMapping("get-contact-us")
	public ResponseData<DataTableResponse<ContactUs>> getContactUsPaginate(@RequestBody DatatableRequest req) {
		ResponseData<DataTableResponse<ContactUs>> response = new ResponseData<DataTableResponse<ContactUs>>();
		try {
			response.setData(contactUsService.getContactUs(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API ContactUsController => getContactUsPaginate");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ContactUsController => getContactUsPaginate :" + e);
		}
		return response;
	}

	@GetMapping("export-data")
	public void exportData(HttpServletResponse response) {
		ResponseData<?> responseMethod = new ResponseData<>();
		try {
			// set fileName
			String fileName = URLEncoder.encode("Contact_Us_Report", "UTF-8");
			// write it as an excel attachment
			ByteArrayOutputStream outByteStream = contactUsService.exportContactList();

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
			log.info("Success Calling API ContactUsController => exportData");
		} catch (Exception e) {
			responseMethod.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseMethod.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ContactUsController => exportData :" + e);
		}
	}

}
