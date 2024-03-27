package coffee.website.inbox.controller;

import coffee.backoffice.frontend.vo.req.MessageReq;
import coffee.backoffice.frontend.vo.res.InboxMessageRes;
import coffee.website.inbox.service.InboxMessageWebPlayerService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/inbox-message/")
@Slf4j
public class InboxMessageWebPlayerController {

    @Autowired
    private InboxMessageWebPlayerService inboxMessageWebPlayerService;

    @PostMapping("trigger-message")
    public ResponseData<String> triggerMessage(@RequestBody MessageReq form) {
        ResponseData<String> response = new ResponseData<String>();
        try {
            inboxMessageWebPlayerService.triggerMessage(form);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => saveCustomer");
        } catch (Exception e) {
            response.setData(e.toString());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API => saveCustomer :" + e);
        }
        return response;
    }


    @GetMapping("get-send-message-by-username/{username}")
    public ResponseData<List<InboxMessageRes>> getMessageByUsername(@PathVariable("username") String username) {
        ResponseData<List<InboxMessageRes>> responseData = new ResponseData<List<InboxMessageRes>>();
        try {
            responseData.setData(inboxMessageWebPlayerService.getMessageByUsername(username));
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API InboxMessageController => getSendMessageByCode");
        } catch (Exception e) {
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API InboxMessageController => getSendMessageByCode :" + e);
        }
        return responseData;
    }

    @DeleteMapping("delete-message/{messageCode}/{username}")
    public ResponseData<?> deleteMessage(@PathVariable(name = "messageCode", required = false) String messageCode,
                                         @PathVariable(name = "username", required = false) String username) {
        ResponseData<?> responseData = new ResponseData<>();
        try {
            inboxMessageWebPlayerService.deletInboxMessage(messageCode, username);
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.DELETE.SUCCESS);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API InboxMessageController => deletInboxMessage");
        } catch (Exception e) {
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.DELETE.FAILED);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API InboxMessageController => deletInboxMessage :" + e);
        }
        return responseData;
    }

}
