package coffee.backoffice.lotto.controller;
import coffee.backoffice.lotto.model.LottoGroupNumber;
import coffee.backoffice.lotto.service.LottoGroupNumberService;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberReq;
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
@RequestMapping("api/lotto-group-number")
@Slf4j
public class LottoGroupNumberController {
    @Autowired
    private LottoGroupNumberService lottoGroupNumberService;

    //    GET ONE
    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    public ResponseData<LottoGroupNumberRes> getId(@PathVariable("id") Long id) {
        ResponseData<LottoGroupNumberRes> response = new ResponseData<LottoGroupNumberRes>();
        try {
            response.setData(lottoGroupNumberService.getOne(id));
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

    @GetMapping("/get-by-username/{username}")
    @ResponseBody
    public ResponseData<List<LottoGroupNumberRes>> getId(@PathVariable("username") String username) {
        ResponseData<List<LottoGroupNumberRes>> response = new ResponseData<List<LottoGroupNumberRes>>();
        try {
            response.setData(lottoGroupNumberService.getAllByUsernameOwner(username));
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

    //    GET ALL
    @GetMapping
    @ResponseBody
    public ResponseData<List<LottoGroupNumberRes>> getAll() {
        ResponseData<List<LottoGroupNumberRes>> response = new ResponseData<List<LottoGroupNumberRes>>();
        try {
            response.setData(lottoGroupNumberService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoGroupNumberController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API LottoGroupNumberController.getAll() :" + e);
        }
        return response;
    }

    //  INSERT
    @PostMapping
    @ResponseBody
    public ResponseData<LottoGroupNumberRes> insert(@RequestBody LottoGroupNumberReq param) {
        ResponseData<LottoGroupNumberRes> response = new ResponseData<LottoGroupNumberRes>();
        try {
            response.setData(lottoGroupNumberService.insertOne(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoGroupNumberController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API LottoGroupNumberController.insert() :" + e);
        }
        return response;
    }
    //  INSERT
    @PutMapping
    @ResponseBody
    public ResponseData<LottoGroupNumberRes> update(@RequestBody LottoGroupNumberReq param) {
        ResponseData<LottoGroupNumberRes> response = new ResponseData<LottoGroupNumberRes>();
        try {
            response.setData(lottoGroupNumberService.updateOne(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoGroupNumberController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API LottoGroupNumberController.insert() :" + e);
        }
        return response;
    }

    //    DELETE
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<LottoGroupNumberRes> delete(@PathVariable("id") Long id) {
        ResponseData<LottoGroupNumberRes> response = new ResponseData<LottoGroupNumberRes>();
        try {
            response.setData(lottoGroupNumberService.delete(id));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => LottoGroupNumberController.delete()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API LottoGroupNumberController.delete() :" + e);
        }
        return response;
    }

}
