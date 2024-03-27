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
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.finance.service.WithdrawService;
import coffee.backoffice.finance.vo.req.WithdrawReq;
import coffee.backoffice.finance.vo.res.WithdrawListRes;
import coffee.backoffice.finance.vo.res.WithdrawRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/withdraw")
@Slf4j
public class WithdrawController {
	
	@Autowired
	private WithdrawService withdrawService;

	@PostMapping("admin-request")
	public ResponseData<?> adminRequest(@RequestBody WithdrawReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			withdrawService.newAdminRequest(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawController => playerRequest");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawController => playerRequest :");
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("remark/{id}")
	public ResponseData<?> updateRemark(@PathVariable("id")Long id,@RequestBody WithdrawReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			withdrawService.updateRemark(id,req);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawController => playerRequest");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawController => playerRequest :");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("change-status/{id}/{status}")
	public ResponseData<?> adminRequest(@PathVariable(name = "id")Long id,@PathVariable(name = "status")String  status) {
		ResponseData<?> response = new ResponseData<>();
		try {
			String request = withdrawService.changeStatusWithdraw(id,status);
			response.setMessage(request);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawController => playerRequest");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawController => playerRequest :");
			e.printStackTrace();
		}
		return response;
	}

	@GetMapping("get-withdraw")
	public ResponseData<List<WithdrawRes>> getWithdraw() {
		ResponseData<List<WithdrawRes>> response = new ResponseData<>();
		try {
			response.setData(withdrawService.getAllByToken());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawController => playerRequest");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawController => playerRequest :");
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("paginate")
	public ResponseData<DataTableResponse<WithdrawRes>> getWithdraw(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<WithdrawRes>> response = new ResponseData<DataTableResponse<WithdrawRes>>();
		try {
			response.setData(withdrawService.getPaginate(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawConditionController => getWithdrawCondition");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawConditionController => getWithdrawCondition :" + e);
		}
		return response;
	}

	@PostMapping("get-withdrawal-list-paginate")
	public ResponseData<WithdrawListRes> getWithdrawListPaginate(@RequestBody DatatableRequest req) {
		ResponseData<WithdrawListRes> response = new ResponseData<WithdrawListRes>();
		try {
			response.setData(withdrawService.getWithdrawListPaginate(req));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawConditionController => getWithdrawListPaginate");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawConditionController => getWithdrawListPaginate :" + e);
		}
		return response;
	}
}
