package coffee.backoffice.casino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.casino.service.GameGroupService;
import coffee.backoffice.casino.vo.req.GameGroupReq;
import coffee.backoffice.casino.vo.res.GameGroupRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.DELETE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.EDIT;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/game-group")
@Slf4j
public class GameGroupController {
    @Autowired
    private GameGroupService gameGroupService;

    // SAVE
    @PostMapping("save-game-group")
    public ResponseData<String> saveGroup(@RequestBody GameGroupReq param) {
        ResponseData<String> response = new ResponseData<>();
        try {
            response.setData(gameGroupService.saveGroup(param));
            response.setMessage(response.getData());
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameGroupController.saveGroup()");
        } catch (Exception e) {
            response.setMessage(SAVE.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameGroupController.saveGroup() :" + e);
        }
        return response;
    }

    // EDIT
    @PutMapping("edit-game-group")
    public ResponseData<String> editGroup(@RequestBody GameGroupReq param) {
        ResponseData<String> response = new ResponseData<>();
        try {
            gameGroupService.editGroup(param);
            response.setMessage(EDIT.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameGroupController.saveGroup()");
        } catch (Exception e) {
            response.setMessage(EDIT.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameGroupController.saveGroup() :" + e);
        }
        return response;
    }

    // GET BY CODE
    @GetMapping("get-group-by-code/{code}")
    public ResponseData<GameGroupRes> getGroupByCode(@PathVariable("code") String code) {
        ResponseData<GameGroupRes> response = new ResponseData<GameGroupRes>();
        try {
            response.setData(gameGroupService.getGameGroupByCode(code));
            response.setMessage(GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameGroupController.getGroupByCode()");
        } catch (Exception e) {
            response.setMessage(GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => GameGroupController.getGroupByCode() : " + e);
        }
        return response;
    }

    // GET BY CODE
    @GetMapping("get-all")
    public ResponseData<List<GameGroupRes>> getAll() {
        ResponseData<List<GameGroupRes>> response = new ResponseData<List<GameGroupRes>>();
        try {
            response.setData(gameGroupService.getAll());
            response.setMessage(GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameGroupController.getGroupByCode()");
        } catch (Exception e) {
            response.setMessage(GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => GameGroupController.getGroupByCode() : " + e);
        }
        return response;
    }


    // DELETE
    @DeleteMapping("delete-group/{code}")
    public ResponseData<GameGroupRes> deleteGroup(@PathVariable("code") String code) {
        ResponseData<GameGroupRes> response = new ResponseData<GameGroupRes>();
        try {
            gameGroupService.deleteGroup(code);
            response.setMessage(DELETE.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameGroupController.deleteGroup()");
        } catch (Exception e) {
            response.setMessage(DELETE.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameGroupController.deleteGroup() :" + e);
        }
        return response;
    }

    // GET PAGINATE
    @PostMapping("get-paginate-group")
    public ResponseData<DataTableResponse<GameGroupRes>> getDataPaginateGameGroup(@RequestBody DatatableRequest req) {
        ResponseData<DataTableResponse<GameGroupRes>> response = new ResponseData<DataTableResponse<GameGroupRes>>();
        try {
            response.setData(gameGroupService.getPaginateGroup(req));
            response.setMessage(GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameGroupController.getDataPaginateGameGroup()");
        } catch (Exception e) {
            response.setMessage(GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameGroupController.getDataPaginateGameGroup() :" + e);
        }
        return response;
    }
}
