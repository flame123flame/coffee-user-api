package coffee.website.finance.controller;

import coffee.backoffice.finance.vo.res.CompanyAccountRes;
import coffee.website.finance.service.CompanyAccountWebPlayerService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/company-account")
@Slf4j
public class CompanyAccountWebPlayerController {

    @Autowired
    private CompanyAccountWebPlayerService companyAccountWebPlayerService;

    @GetMapping("get-random-row/{username}")
    @ResponseBody
    public ResponseData<CompanyAccountRes> getRandRow(@PathVariable("username") String username) {
        ResponseData<CompanyAccountRes> responseData = new ResponseData<CompanyAccountRes>();
        try {
            responseData.setData(companyAccountWebPlayerService.getRand(username));
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API CompanyAccountController => getRandRow");
        } catch (Exception e) {
            log.error("Error Calling API CompanyAccountController =>  getRandRow ", e);
            responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            responseData.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
        }
        return responseData;
    }
}
