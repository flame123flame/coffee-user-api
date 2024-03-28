package coffee.website.wallet.controller;

import coffee.backoffice.finance.vo.req.DepositReq;
import coffee.backoffice.finance.vo.res.DepositDetailRes;
import coffee.backoffice.finance.vo.res.DepositMainRes;
import coffee.website.wallet.service.DepositPlayerService;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import framework.utils.GenerateRandomString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/deposit/")
@Slf4j
public class DepositPlayerController {
    @Autowired
    DepositPlayerService depositPlayerService;


    @PostMapping("new-deposit")
    public ResponseData<?> saveDeposit(@RequestBody DepositReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            depositPlayerService.createDeposit(form);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API DepositListController => saveDeposit");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API DepositListController => saveDeposit :" + e);
        }
        return response;
    }

    @GetMapping("get-deposit-order")
    public ResponseData<String> getDepositOrder() {
        ResponseData<String> response = new ResponseData<String>();
        try {
            response.setData(GenerateRandomString.generateUUID());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API DepositListController => getDepositOrder");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API DepositListController => getDepositOrder :" + e);
        }
        return response;
    }


    @GetMapping("get-history/{username}")
    public ResponseData<List<DepositMainRes>> getDepositListByUsername(@PathVariable("username") String username) {
        ResponseData<List<DepositMainRes>> responseData = new ResponseData<List<DepositMainRes>>();
        try {
            responseData.setData(depositPlayerService.getDepositByUsername(username));
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API DepositListController => getDepositListByUsername");
        } catch (Exception e) {
            log.error("Error Calling API DepositListController =>  getDepositListByUsername ", e);
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }
}
