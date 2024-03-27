package coffee.backoffice.casino.controller;

import coffee.backoffice.casino.model.GameTag;
import coffee.backoffice.casino.service.GameTagService;
import coffee.backoffice.casino.vo.req.GameTagReorderReq;
import coffee.backoffice.casino.vo.req.GameTagReq;
import coffee.backoffice.casino.vo.res.GameTagRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/game-tag")
@Slf4j
public class GameTagController {
    @Autowired
    private GameTagService gameTagService;

    // GET PAGINATE
    @GetMapping()
    public ResponseData<List<GameTagRes>> getAll() {
        ResponseData<List<GameTagRes>> response = new ResponseData<List<GameTagRes>>();
        try {
            response.setData(gameTagService.getAllRes());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.getDataPaginate() :" + e);
        }
        return response;
    }

    // GET PAGINATE
    @GetMapping("get-by-id/{id}")
    public ResponseData<GameTagRes> getAll(@PathVariable Long id) {
        ResponseData<GameTagRes> response = new ResponseData<GameTagRes>();
        try {
            response.setData(gameTagService.getById(id));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.getDataPaginate() :" + e);
        }
        return response;
    }

   // GET PAGINATE
    @GetMapping("get-all-sort")
    public ResponseData<List<GameTag>> getAllSort() {
        ResponseData<List<GameTag>> response = new ResponseData<List<GameTag>>();
        try {
            response.setData(gameTagService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.getDataPaginate() :" + e);
        }
        return response;
    }


    // SAVE
    @PostMapping
    public ResponseData<?> saveGroup(@RequestBody GameTagReq param) {
        ResponseData<?> response = new ResponseData<>();
        try {
            gameTagService.insertOne(param);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.saveGroup()");
        } catch (DataIntegrityViolationException e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.DUPLICATE_DATA);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.saveGroup() :" + e);
        }catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.saveGroup() :" + e);
        }
        return response;
    }

    // SAVE
    @PostMapping("re-order")
    public ResponseData<?> ReOrder(@RequestBody List<GameTagReorderReq> param) {
        ResponseData<?> response = new ResponseData<>();
        try {
            gameTagService.reOrder(param);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.saveGroup()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SAVE.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.saveGroup() :" + e);
        }
        return response;
    }

    // EDIT
    @PutMapping("/{id}")
    public ResponseData<?> editGroup(@PathVariable Long id,@RequestBody GameTagReq param) {
        ResponseData<?> response = new ResponseData<>();
        try {
            gameTagService.editOne(id,param);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.EDIT.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.saveGroup()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.EDIT.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.saveGroup() :" + e);
        }
        return response;
    }

    // GET BY CODE
    @GetMapping("get-tag-by-code/{code}")
    public ResponseData<GameTagRes> getGroupByCode(@PathVariable("code") String code) {
        ResponseData<GameTagRes> response = new ResponseData<GameTagRes>();
        try {
            response.setData(gameTagService.getByCode(code));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.getGroupByCode()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.info("Error Calling API => GameTagController.getGroupByCode() : " + e);
        }
        return response;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseData<?> deleteGroup(@PathVariable("id") Long id) {
        ResponseData<?> response = new ResponseData<GameTagRes>();
        try {
            gameTagService.deleteOne(id);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.DELETE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.deleteGroup()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.DELETE.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.deleteGroup() :" + e);
        }
        return response;
    }

    // GET PAGINATE
    @PostMapping("paginate")
    public ResponseData<DataTableResponse<GameTagRes>> getDataPaginateGameGroup(@RequestBody DatatableRequest req) {
        ResponseData<DataTableResponse<GameTagRes>> response = new ResponseData<DataTableResponse<GameTagRes>>();
        try {
            response.setData(gameTagService.getPaginate(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameTagController.getDataPaginateGameGroup()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameTagController.getDataPaginateGameGroup() :" + e);
        }
        return response;
    }
}
