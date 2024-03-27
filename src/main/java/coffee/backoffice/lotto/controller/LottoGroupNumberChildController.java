package coffee.backoffice.lotto.controller;

import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import coffee.backoffice.lotto.service.LottoGroupNumberChildService;
import coffee.backoffice.lotto.service.LottoGroupNumberService;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberChildReq;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberReq;
import coffee.backoffice.lotto.vo.res.LottoGroupNumberChildRes;
import coffee.backoffice.lotto.vo.res.LottoGroupNumberRes;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/lotto-group-number-child")
@Slf4j
public class LottoGroupNumberChildController {
    @Autowired
    private LottoGroupNumberChildService lottoGroupNumberChildService;

    @GetMapping("/get-by-group-code/{code}")
    @ResponseBody
    public ResponseData<List<LottoGroupNumberChild>> getId(@PathVariable("code") String code) {
        ResponseData< List<LottoGroupNumberChild>> response = new ResponseData< List<LottoGroupNumberChild>>();
        try {
            response.setData(lottoGroupNumberChildService.getOneByParentCode(code));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoGroupNumberController.getId()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => LottoGroupNumberController.getId() : " + e);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<?> getId(@PathVariable("id") Long id) {
        ResponseData<?> response = new ResponseData<>();
        try {
            lottoGroupNumberChildService.deleteById(id);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoGroupNumberController.getId()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => LottoGroupNumberController.getId() : " + e);
        }
        return response;
    }

    @PutMapping()
    @ResponseBody
    public ResponseData<LottoGroupNumberChildRes> updateOne(@RequestBody LottoGroupNumberChildReq req) {
        ResponseData<LottoGroupNumberChildRes> response = new ResponseData<LottoGroupNumberChildRes>();
        try {
            response.setData(lottoGroupNumberChildService.updateOne(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoGroupNumberController.getId()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => LottoGroupNumberController.getId() : " + e);
        }
        return response;
    }
}
