package coffee.backoffice.player.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.player.service.NewRegistrantService;
import coffee.backoffice.player.vo.req.NewRegistrantReq;
import coffee.backoffice.player.vo.res.NewRegistrantRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/new-registrant/")
@Slf4j
public class NewRegistrantController {

	@Autowired
	private NewRegistrantService newRegistrantService;

	@GetMapping("get-count-to-day")
	public ResponseData<List<NewRegistrantRes>> getCountToDay() {
		ResponseData<List<NewRegistrantRes>> response = new ResponseData<List<NewRegistrantRes>>();
		try {
			response.setData(newRegistrantService.getCountToDay());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API RegistrantController => getCountToDay");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RegistrantController => getCountToDay :" + e);
		}
		return response;
	}
	
	
	@GetMapping("get-count-to-week")
	public ResponseData<List<NewRegistrantRes>> getCountToWeek() {
		ResponseData<List<NewRegistrantRes>> response = new ResponseData<List<NewRegistrantRes>>();
		try {
			response.setData(newRegistrantService.getCountToWeek());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API RegistrantController => getCountToWeek");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RegistrantController => getCountToWeek :" + e);
		}
		return response;
	}
	
	@GetMapping("get-count-to-Lastweek")
	public ResponseData<List<NewRegistrantRes>> getCountToLastweek() {
		ResponseData<List<NewRegistrantRes>> response = new ResponseData<List<NewRegistrantRes>>();
		try {
			response.setData(newRegistrantService.getCountToLastweek());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API RegistrantController => getCountToLastweek");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RegistrantController => getCountToLastweek :" + e);
		}
		return response;
	}
	
	@PostMapping("get-paginate")
	public ResponseData<DataTableResponse<NewRegistrantRes>> getPaginate(@RequestBody DatatableRequest req) {
		ResponseData<DataTableResponse<NewRegistrantRes>> response = new ResponseData<DataTableResponse<NewRegistrantRes>>();
		try {
			response.setData(newRegistrantService.getPaginate(req));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API RegistrantController => getPaginate");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RegistrantController => getPaginate :" + e);
		}
		return response;
		
	}
	
	@PutMapping("edit-enable")
	public ResponseData<?> editEnable(@RequestBody NewRegistrantReq req) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			newRegistrantService.editEnable(req);
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API RegistrantController => editEnable");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RegistrantController => editEnable :" + e);
		}
		return responseData;
	}
}
