package coffee.backoffice.casino.controller;

import coffee.backoffice.casino.model.ProviderSummary;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.casino.vo.res.ProductMapProviderRes;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/provider-summary")
@Slf4j
public class ProviderSummaryController {
    @Autowired
    ProviderSummaryService providerSummaryService;
    // GET BY PRODUCT CODE
    @GetMapping("get-by-username/{username}")
    public ResponseData<List<ProviderSummary>> getMapping(@PathVariable("username") String username) {
        ResponseData<List<ProviderSummary>> response = new ResponseData<List<ProviderSummary>>();
        try {
            response.setData(providerSummaryService.getProviderSummary(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => ProductMappingProviderController.getMapping()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API ProductMappingProviderController.getMapping() :" + e);
        }
        return response;
    }
}
