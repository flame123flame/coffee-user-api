package coffee.backoffice.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.finance.service.DepositService;
import coffee.backoffice.finance.vo.req.DepositReq;
import coffee.backoffice.finance.vo.res.DepositDetailRes;
import coffee.backoffice.finance.vo.res.DepositListRes;
import coffee.backoffice.finance.vo.res.DepositMainRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import framework.utils.GenerateRandomString;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/deposit/")
@Slf4j
public class DepositListController {
	
	@Autowired
	DepositService depositService;

	@GetMapping("get-deposit-list")
	public ResponseData<List<DepositMainRes>> getDepositList() {
		ResponseData<List<DepositMainRes>> response = new ResponseData<List<DepositMainRes>>();
		try {
			response.setData(depositService.getAllList());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API DepositListController => getDepositList");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API DepositListController => getDepositList :" + e);
		}
		return response;
	}
	
	@GetMapping("get-detail/{depositOrder}")
	public ResponseData<DepositDetailRes> getDetail(@PathVariable("depositOrder") String depositOrder) {
		ResponseData<DepositDetailRes> response = new ResponseData<DepositDetailRes>();
		try {
			response.setData(depositService.getDetail(depositOrder));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API DepositListController => getDepositList");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API DepositListController => getDepositList :" + e);
		}
		return response;
	}
	
	@PostMapping("get-deposit-list-paginate")
	@ResponseBody
	public ResponseData<DepositListRes> getDepositListPaginate(@RequestBody DatatableRequest req) {
		ResponseData<DepositListRes> response = new ResponseData<DepositListRes>();
		try {
			response.setData(depositService.getDepositListPaginate(req));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API DepositListController => getDepositListPaginate");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API DepositListController => getDepositListPaginate :" + e);
		}
		return response;
	}
	
	@PostMapping("change-status")
	public ResponseData<?> changeStatus(@RequestBody DepositReq form) {
		ResponseData<?> response = new ResponseData<>();
		try {
			depositService.changeStatus(form);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API DepositListController => changeStatus");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API DepositListController => changeStatus :" + e);
		}
		return response;
	}

}
