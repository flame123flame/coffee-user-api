package coffee.backoffice.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.report.service.PromotionReportService;
import coffee.backoffice.report.vo.res.PromotionReportDatatableRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/promotion-report/")
@Slf4j
public class PromotionReportController {
    @Autowired
    PromotionReportService promotionReportService;
    // GET PAGINATE
    @PostMapping("paginate")
    public ResponseData<DataTableResponse<PromotionReportDatatableRes>> getDataPaginateGameGroup(@RequestBody DatatableRequest req) {
        ResponseData<DataTableResponse<PromotionReportDatatableRes>> response = new ResponseData<DataTableResponse<PromotionReportDatatableRes>>();
        try {
            response.setData(promotionReportService.getPaginate(req));
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
