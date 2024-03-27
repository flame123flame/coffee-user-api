package coffee.backoffice.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.finance.vo.res.DepositListRes;
import coffee.backoffice.finance.vo.res.WithdrawConditionRes;
import coffee.backoffice.frontend.service.InboxMessageService;
import coffee.backoffice.frontend.vo.req.MessageReq;
import coffee.backoffice.frontend.vo.res.InboxMessageRes;
import coffee.backoffice.frontend.vo.res.MessageRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/inbox-message/")
@Slf4j
public class InboxMessageController {

	@Autowired
	private InboxMessageService messageService;
	
	@PostMapping("get-send-message")
	public ResponseData<DataTableResponse<MessageRes>> getWithdrawCondition(
			@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<MessageRes>> response = new ResponseData<DataTableResponse<MessageRes>>();
		try {
			response.setData(messageService.GetmessageService(param));
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


	@GetMapping("get-send-message-all")
	public ResponseData<List<MessageRes>> getAllSendMessage() {
		ResponseData<List<MessageRes>> response = new ResponseData<List<MessageRes>>();
		try {
			response.setData(messageService.getAllSendMessage());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API InboxMessageController => getAllSendMessage");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API InboxMessageController => getAllSendMessage :" + e);
		}
		return response;
	}

	@PostMapping("save-send-message")
	public ResponseData<String> saveSendMessage(@RequestBody MessageReq form) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			messageService.saveMessage(form);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API InboxMessageController => saveCustomer");
		} catch (Exception e) {
			response.setData(e.toString());
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API InboxMessageController => saveCustomer :" + e);
		}
		return response;
	}

	@GetMapping("get-send-message-by-code/{messageCode}")
	public ResponseData<MessageRes> getMessageByCode(@PathVariable("messageCode") String code) {
		ResponseData<MessageRes> responseData = new ResponseData<MessageRes>();
		try {
			responseData.setData(messageService.getSendMessageByCode(code));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API InboxMessageController => getSendMessageByCode");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API InboxMessageController => getSendMessageByCode :" + e);
		}
		return responseData;
	}

	@DeleteMapping("delete-send-message/{messageCode}")
	public ResponseData<?> deleteSendMessage(@PathVariable("messageCode") String messageCode) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			messageService.deleteMessage(messageCode);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API InboxMessageController => deleteSendMessage");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API InboxMessageController => deleteSendMessage :" + e);
		}
		return responseData;
	}

}
