package coffee.backoffice.admin.controller;

import coffee.backoffice.admin.service.FwRoleService;
import coffee.backoffice.admin.vo.req.FwRoleReq;
import coffee.backoffice.admin.vo.res.FwRoleRes;
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
@RequestMapping("api/fw-user-role")
@Slf4j
public class FwRoleController {
    @Autowired
    FwRoleService fwRoleService;

    @PostMapping("paginate")
    public ResponseData<DataTableResponse<FwRoleRes>> paginate(@RequestBody DatatableRequest req) {
        ResponseData<DataTableResponse<FwRoleRes>> response = new ResponseData<DataTableResponse<FwRoleRes>>();
        try {
            response.setData(fwRoleService.paginate(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => MenuAccessController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API MenuAccessController.getAll() :" + e);
        }
        return response;
    }
    @PostMapping()
    public ResponseData<FwRoleRes> insert(@RequestBody FwRoleReq req) {
        ResponseData<FwRoleRes> response = new ResponseData<>();
        try {
            response.setData(fwRoleService.insertOne(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => MenuAccessController.getAll()");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API MenuAccessController.getAll() :" + e);
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseData<FwRoleRes> update(@PathVariable("id") Long id, @RequestBody FwRoleReq req) {
        ResponseData<FwRoleRes> response = new ResponseData<>();
        try {
            response.setData(fwRoleService.update(id,req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => MenuAccessController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API MenuAccessController.getAll() :" + e);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> delete(@PathVariable("id") Long id) {
        ResponseData<?> response = new ResponseData<>();
        try {
            fwRoleService.deleteOne(id);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => MenuAccessController.getAll()");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API MenuAccessController.getAll() :" + e);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseData<FwRoleRes> getById(@PathVariable("id") Long id) {
        ResponseData<FwRoleRes> response = new ResponseData<>();
        try {
            response.setData(fwRoleService.getById(id));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => MenuAccessController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API MenuAccessController.getAll() :" + e);
        }
        return response;
    }

    @GetMapping()
    public ResponseData<List<FwRoleRes>> getAll() {
        ResponseData<List<FwRoleRes>> response = new ResponseData<>();
        try {
            response.setData(fwRoleService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => MenuAccessController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API MenuAccessController.getAll() :" + e);
        }
        return response;
    }
}
