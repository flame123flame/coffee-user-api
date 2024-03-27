package coffee.backoffice.player.controller;

import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import coffee.backoffice.player.service.TagManagementService;
import coffee.backoffice.player.vo.req.TagManagementReq;
import coffee.backoffice.player.vo.res.TagManagementRes;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/tag-management")
@Slf4j
public class TagManagementController {

    @Autowired
    private TagManagementService tagManagementService;

    @GetMapping("get-by-id/{id}")
    @ResponseBody
    public ResponseData<TagManagementRes> getId(@PathVariable("id") Long id) {
        ResponseData<TagManagementRes> response = new ResponseData<TagManagementRes>();
        try {
            response.setData(tagManagementService.getOne(id));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => TagManagementController.getId()");

        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => TagManagementController.getId() : "+e);
        }
        return response;
    }

    @GetMapping
    @ResponseBody
    public ResponseData<List<TagManagementRes>> getAll() {
        ResponseData<List<TagManagementRes>> response = new ResponseData<List<TagManagementRes>>();
        try {
            response.setData(tagManagementService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => TagManagementController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => TagManagementController.getAll() : "+e);
        }
        return response;
    }


    @PostMapping("/paginate")
    @ResponseBody
    public ResponseData<DataTableResponse<TagManagementRes>> getDataPaginate(@RequestBody DatatableRequest param){
        ResponseData<DataTableResponse<TagManagementRes>> response = new ResponseData<DataTableResponse<TagManagementRes>>();
        try {
            response.setData(tagManagementService.getPaginateModel(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => TagManagementController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => TagManagementController.getDataPaginate() : "+e);
        }
        return response;
    }

    @PostMapping
    @ResponseBody
    public ResponseData<TagManagementRes> insert(@RequestBody TagManagementReq param){
        ResponseData<TagManagementRes> response = new ResponseData<TagManagementRes>();
        try {
            response.setData(tagManagementService.insertOne(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => TagManagementController.insert()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => TagManagementController.insert() : "+e);
        }
        return response;
    }

    @PutMapping
    @ResponseBody
    public ResponseData<TagManagementRes> update(@RequestBody TagManagementReq param){
        ResponseData<TagManagementRes> response = new ResponseData<TagManagementRes>();
        try {
            response.setData(tagManagementService.updateOne(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => TagManagementController.update()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => TagManagementController.update() : "+e);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<TagManagementRes> delete(@PathVariable("id") Long id){
        ResponseData<TagManagementRes> response = new ResponseData<TagManagementRes>();
        try {
            response.setData(tagManagementService.delete(id));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => TagManagementController.delete()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Failed Calling API => TagManagementController.delete() : "+e);
        }
        return response;
    }
}
