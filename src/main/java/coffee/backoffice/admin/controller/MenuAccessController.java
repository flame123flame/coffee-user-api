package coffee.backoffice.admin.controller;

import coffee.backoffice.admin.service.MenuAccessService;
import coffee.backoffice.admin.vo.res.FwRoleRes;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/menu-access")
@Slf4j
public class MenuAccessController {

    @Autowired
    MenuAccessService menuAccessService;

    @GetMapping("get-menu-access-by-token")
    public ResponseData<FwRoleRes> getAll() {
        ResponseData<FwRoleRes> response = new ResponseData<FwRoleRes>();
        try {
            response.setData(menuAccessService.getMenuAccess());
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
