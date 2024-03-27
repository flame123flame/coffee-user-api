package coffee.backoffice.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.finance.vo.req.WithdrawConditionStausReq;
import coffee.backoffice.finance.vo.res.WithdrawConditionRes;
import framework.constant.ProjectConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/withdraw-condition/")
@Slf4j
public class WithdrawConditionController {

	@Autowired
	private WithdrawConditionService conditionService;

	@PostMapping("get-withdraw-condition")
	public ResponseData<DataTableResponse<WithdrawConditionRes>> getWithdrawCondition(
			@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<WithdrawConditionRes>> response = new ResponseData<DataTableResponse<WithdrawConditionRes>>();
		try {
			response.setData(conditionService.getWithdrawCondition(param));
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

	@GetMapping("player-withdraw-condition/{username}")
	public ResponseData<List<WithdrawCondition>> playerWithdrawCondition(@PathVariable("username") String username) {
		ResponseData<List<WithdrawCondition>> response = new ResponseData<List<WithdrawCondition>>();
		try {
			response.setData(conditionService.getWithdrawConditionByUsername(username,
					ProjectConstant.WITHDRAW_CONDITION.NOT_PASS));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawConditionController => playerWithdrawCondition");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawConditionController => playerWithdrawCondition :" + e);
		}
		return response;
	}

	@PutMapping("change-status-withdraw-condition")
	public ResponseData<?> changeStatusWithdrawCondition(@RequestBody WithdrawConditionStausReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			conditionService.changeStatusWithdrawCondition(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API WithdrawConditionController => changeStatusWithdrawCondition");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API WithdrawConditionController => changeStatusWithdrawCondition :" + e);
		}
		return response;
	}
}
