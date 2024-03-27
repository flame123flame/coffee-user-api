package coffee.backoffice.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.player.service.FailedLoginService;
import coffee.backoffice.player.vo.res.FailedLoginRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/failed-login/")
@Slf4j
public class FailedLoginController {

	@Autowired
	FailedLoginService failedLoginService;
	
	@PostMapping("paginate")
    public ResponseData<DataTableResponse<FailedLoginRes>> paginate(@RequestBody DatatableRequest req) {
        ResponseData<DataTableResponse<FailedLoginRes>> response = new ResponseData<DataTableResponse<FailedLoginRes>>();
        try {
            response.setData(failedLoginService.paginate(req));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => FailedLoginController.paginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API FailedLoginController.paginate() :" + e);
        }
        return response;
    }
}
