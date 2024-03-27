package coffee.backoffice.finance.controller;

import coffee.backoffice.finance.service.CompanyAccountService;
import coffee.backoffice.finance.vo.req.CompanyAccountReq;
import coffee.backoffice.finance.vo.res.CompanyAccountRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/company-account")
@Slf4j
public class CompanyAccountController {


    @Autowired
    private CompanyAccountService companyAccountService;

    //    GET ONE
    @GetMapping("get-by-code/{code}")
    @ResponseBody
    public ResponseData<CompanyAccountRes> getId(@PathVariable("code") String code) {
        ResponseData<CompanyAccountRes> response = new ResponseData<CompanyAccountRes>();
        try {
            response.setData(companyAccountService.getOne(code));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CompanyAccountController.getId()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => CompanyAccountController.getId() : " + e);
        }
        return response;
    }

    //    GET ALL
    @GetMapping
    @ResponseBody
    public ResponseData<List<CompanyAccountRes>> getAll() {
        ResponseData<List<CompanyAccountRes>> response = new ResponseData<List<CompanyAccountRes>>();
        try {
            response.setData(companyAccountService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CompanyAccountController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CompanyAccountController.getAll() :" + e);
        }
        return response;
    }

    //  GET PAGINATE
    @PostMapping("/paginate")
    @ResponseBody
    public ResponseData<DataTableResponse<CompanyAccountRes>> getDataPaginate(@RequestBody DatatableRequest param) {
        ResponseData<DataTableResponse<CompanyAccountRes>> response = new ResponseData<DataTableResponse<CompanyAccountRes>>();
        try {
            response.setData(companyAccountService.getPaginateModel(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CompanyAccountController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CompanyAccountController.getDataPaginate() :" + e);
        }
        return response;
    }

    //  INSERT
    @PostMapping
    @ResponseBody
    public ResponseData<?> insert(@RequestBody CompanyAccountReq param) {
        ResponseData<?> response = new ResponseData<>();
        try {
            companyAccountService.insertOne(param);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CompanyAccountController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CompanyAccountController.insert() :" + e);
        }
        return response;
    }

    //    UPDATE
    @PutMapping
    @ResponseBody
    public ResponseData<?> update(@RequestBody CompanyAccountReq param) {
        ResponseData<?> response = new ResponseData<>();
        try {
            companyAccountService.updateOne(param);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CompanyAccountController.update()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CompanyAccountController.update() :" + e);
        }
        return response;
    }

    //    DELETE
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<CompanyAccountRes> delete(@PathVariable("id") Long id) {
        ResponseData<CompanyAccountRes> response = new ResponseData<CompanyAccountRes>();
        try {
            response.setData(companyAccountService.delete(id));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => CompanyAccountController.delete()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API CompanyAccountController.delete() :" + e);
        }
        return response;
    }

}
