package coffee.backoffice.admin.controller;

import coffee.backoffice.admin.service.FwUserService;
import coffee.backoffice.admin.vo.req.FwUserReq;
import coffee.backoffice.admin.vo.res.FwUserRes;
import coffee.backoffice.admin.vo.res.FwUserRes;
import coffee.backoffice.rebate.vo.res.RebateHistoryRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/fw-user")
@Slf4j
public class FwUserController {
    @Autowired
    FwUserService fwUserService;

    @PostMapping("paginate")
    public ResponseData<DataTableResponse<FwUserRes>> paginate(@RequestBody DatatableRequest req) {
        ResponseData<DataTableResponse<FwUserRes>> response = new ResponseData<DataTableResponse<FwUserRes>>();
        try {
            response.setData(fwUserService.paginate(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => fwUserController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API fwUserController.getAll() :" + e);
        }
        return response;
    }
    @PostMapping()
    public ResponseData<FwUserRes> insert(@RequestBody FwUserReq req) {
        ResponseData<FwUserRes> response = new ResponseData<>();
        try {
            response.setData(fwUserService.insertOne(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => fwUserController.getAll()");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API fwUserController.getAll() :" + e);
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseData<FwUserRes> update(@PathVariable("id") Long id,@RequestBody FwUserReq req) {
        ResponseData<FwUserRes> response = new ResponseData<>();
        try {
            response.setData(fwUserService.update(id,req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => fwUserController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API fwUserController.getAll() :" + e);
        }
        return response;
    }

    @PutMapping("/{id}/password")
    public ResponseData<FwUserRes> updatePassword(@PathVariable("id") Long id,@RequestBody FwUserReq req) {
        ResponseData<FwUserRes> response = new ResponseData<>();
        try {
            response.setData(fwUserService.updatePassword(id,req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => fwUserController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API fwUserController.getAll() :" + e);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> delete(@PathVariable("id") Long id) {
        ResponseData<?> response = new ResponseData<>();
        try {
            fwUserService.deleteOne(id);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => fwUserController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API fwUserController.getAll() :" + e);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseData<FwUserRes> update(@PathVariable("id") Long id) {
        ResponseData<FwUserRes> response = new ResponseData<>();
        try {
            response.setData(fwUserService.getById(id));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => fwUserController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API fwUserController.getAll() :" + e);
        }
        return response;
    }
    
    @GetMapping("/get-user-role")
    public ResponseData<FwUserRes> getRole() {
        ResponseData<FwUserRes> response = new ResponseData<>();
        try {
            response.setData(fwUserService.getUserRole());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => fwUserController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API fwUserController.getAll() :" + e);
        }
        return response;
    }

}
