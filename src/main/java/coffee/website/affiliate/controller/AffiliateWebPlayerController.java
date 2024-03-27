package coffee.website.affiliate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.affiliate.vo.req.WithdrawReq;
import coffee.website.affiliate.service.AffiliateWebPlayerService;
import coffee.website.affiliate.vo.model.WithdrawDetail;
import coffee.website.affiliate.vo.res.AffliateDetailResponse;
import coffee.website.affiliate.vo.res.AffliateDownlineResponse;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/affiliate-player")
@Slf4j
public class AffiliateWebPlayerController {

    @Autowired
    private AffiliateWebPlayerService affiliateWebPlayerService;

    @GetMapping("/trigger-affiliate/{affiliateCode}")
    @ResponseBody
    public ResponseData<?> trigger(@PathVariable("affiliateCode") String affiliateCode) {
        ResponseData<?> response = new ResponseData<>();
        try {
            affiliateWebPlayerService.setTrigger(affiliateCode);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliateListController.trigger()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliateListController.trigger() :" + e);
        }
        return response;
    }

    @PostMapping("/withdraw-affiliate")
    @ResponseBody
    public ResponseData<?> withdraw(@RequestBody WithdrawReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            affiliateWebPlayerService.withdrawAffiliate(form);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliateListController.withdraw()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliateListController.withdraw() :" + e);
        }
        return response;
    }
    
    @GetMapping("/recommand-code/{username}")
    @ResponseBody
    public ResponseData<String> recommandCode(@PathVariable("username") String username) {
        ResponseData<String> response = new ResponseData<String>();
        try {
            response.setData(affiliateWebPlayerService.codeAffiliate(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliatePlayerController.getDetail()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliatePlayerController.getDetail() :" + e);
        }
        return response;
    }
    
    @GetMapping("/withdraw-detail/{username}")
    @ResponseBody
    public ResponseData<WithdrawDetail> withdrawDetail(@PathVariable("username") String username) {
        ResponseData<WithdrawDetail> response = new ResponseData<WithdrawDetail>();
        try {
            response.setData(affiliateWebPlayerService.witdrawDetailAffiliate(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliatePlayerController.withdrawDetail()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliatePlayerController.withdrawDetail() :" + e);
        }
        return response;
    }
    
    @GetMapping("/get-detail/{username}")
    @ResponseBody
    public ResponseData<AffliateDetailResponse> getDetail(@PathVariable("username") String username) {
        ResponseData<AffliateDetailResponse> response = new ResponseData<AffliateDetailResponse>();
        try {
            response.setData(affiliateWebPlayerService.getAffiliateDetail(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliatePlayerController.getDetail()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliatePlayerController.getDetail() :" + e);
        }
        return response;
    }

    @GetMapping("/get-downline/{username}")
    @ResponseBody
    public ResponseData<List<AffliateDownlineResponse>> getDownline(@PathVariable("username") String username) {
        ResponseData<List<AffliateDownlineResponse>> response = new ResponseData<List<AffliateDownlineResponse>>();
        try {
            response.setData(affiliateWebPlayerService.getAffiliateDownline(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliatePlayerController.getDownline()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliatePlayerController.getDownline() :" + e);
        }
        return response;
    }

}
