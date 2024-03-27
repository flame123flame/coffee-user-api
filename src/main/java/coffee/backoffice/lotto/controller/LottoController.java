package coffee.backoffice.lotto.controller;

import coffee.backoffice.lotto.model.LottoHistory;
import coffee.backoffice.lotto.vo.req.LottoBuyReq;
import coffee.backoffice.lotto.vo.res.BuyLottoStatusRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import coffee.backoffice.lotto.service.LottoService;
import coffee.backoffice.lotto.vo.req.LottoTimeDetailReq;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix = "controller-config.enable", name = "bo", havingValue = "true")
@RequestMapping("api/lotto/")
@Slf4j
public class LottoController {

    @Autowired
    private LottoService lottoService;

    @PostMapping("add-lotto-time")
    @ResponseBody
    public ResponseData<?> addLottoTime(@RequestBody LottoTimeDetailReq req) {
        ResponseData<?> response = new ResponseData<>();
        try {
            lottoService.addConfigLottoTime(req);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoController.addLottoTime()");

        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Failed Calling API => LottoController.addLottoTime() : " + e);
        }
        return response;
    }

    @PostMapping("buy-lotto")
    @ResponseBody
    public ResponseData<BuyLottoStatusRes> buyLotto(@RequestBody LottoBuyReq req) {
        ResponseData<BuyLottoStatusRes> response = new ResponseData<BuyLottoStatusRes>();
        try {
            response.setData(lottoService.filterAndCreateTransaction(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoController.buyLotto()");

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Failed Calling API => LottoController.buyLotto() : " + e);
        }
        return response;
    }

    @GetMapping("refund-lotto/{groupTransCode}/{classCode}")
    @ResponseBody
    public ResponseData<String> refundLotto(@PathVariable String groupTransCode, @PathVariable String classCode, @RequestParam(required = false) String roundYeekee) {
        ResponseData<String> response = new ResponseData<String>();
        try {
//            lottoService.refundLotto(groupTransCode,classCode);
            response.setData(lottoService.refundLotto(groupTransCode, classCode, roundYeekee));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoController.refundLotto()");

        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            if (e.getMessage().equals("REQ_FAIL"))
                response.setMessage(e.getMessage());
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Failed Calling API => LottoController.refundLotto() : " + e);
        }
        return response;
    }

    @GetMapping("get-by-username")
    @ResponseBody
    public ResponseData<List<LottoHistory>> getByUsername(@RequestParam String username) {
        ResponseData<List<LottoHistory>> response = new ResponseData<>();
        try {
            response.setData(lottoService.getByUsername(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoController.getByUsername()");

        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Failed Calling API => LottoController.getByUsername() : " + e);
        }
        return response;
    }

    @GetMapping("get-all")
    @ResponseBody
    public ResponseData<List<LottoHistory>> getAll() {
        ResponseData<List<LottoHistory>> response = new ResponseData<>();
        try {
            response.setData(lottoService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoController.getAll()");

        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Failed Calling API => LottoController.getAll() : " + e);
        }
        return response;
    }


}
