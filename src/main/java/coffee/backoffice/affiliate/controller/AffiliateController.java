package coffee.backoffice.affiliate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.affiliate.service.AffiliateService;
import coffee.backoffice.affiliate.vo.req.AffiliateReq;
import coffee.backoffice.affiliate.vo.req.WithdrawReq;
import coffee.backoffice.affiliate.vo.res.AffiliatePlayAmountRes;
import coffee.backoffice.affiliate.vo.res.AffiliateRes;
import framework.constant.ResponseConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/affiliate")
@Slf4j
public class AffiliateController {

    @Autowired
    private AffiliateService affiliateService;

    @GetMapping("/get-all")
    @ResponseBody
    public ResponseData<List<AffiliateRes>> getAll() {
        ResponseData<List<AffiliateRes>> response = new ResponseData<List<AffiliateRes>>();
        try {
        	response.setData(affiliateService.getAll());
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliateListController.getAll()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliateListController.getAll() :" + e);
        }
        return response;
    }
    
    @PostMapping("/get-all-pagniate")
    @ResponseBody
	public ResponseData<DataTableResponse<AffiliateRes>> getBankAllPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<AffiliateRes>> response = new ResponseData<DataTableResponse<AffiliateRes>>();
		try {
			response.setData(affiliateService.getAllPaginate(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => AffiliateListController.getBankAllPaginate()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API => AffiliateListController.getBankAllPaginate() :" + e);
		}
		return response;
	}

    @PutMapping
    @ResponseBody
    public ResponseData<?> getDetailByUsername(@RequestBody AffiliateReq form) {
        ResponseData<?> response = new ResponseData<>();
        try {
            affiliateService.update(form);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => AffiliateListController.getByAffiliateCode()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API AffiliateListController.getByAffiliateCode() :" + e);
        }
        return response;
    }

}
