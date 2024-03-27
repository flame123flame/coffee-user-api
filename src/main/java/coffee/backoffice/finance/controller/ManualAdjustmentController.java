package coffee.backoffice.finance.controller;

import coffee.backoffice.finance.service.ManualAdjustmentService;
import coffee.backoffice.finance.vo.res.ManualAdjustmentRes;
import framework.constant.ResponseConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/manual-adjustment/")
@Slf4j
public class ManualAdjustmentController {
    @Autowired
    ManualAdjustmentService manualAdjustmentService;

    @PostMapping("get-paginate")
    public ResponseData<DataTableResponse<ManualAdjustmentRes>> getGameTransactionPaginate(@RequestBody DatatableRequest param) {
        ResponseData<DataTableResponse<ManualAdjustmentRes>> response = new ResponseData<DataTableResponse<ManualAdjustmentRes>>();
        try {
            response.setData(manualAdjustmentService.getAllPaginate(param));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API GameReportController => getGameTransactionPaginate");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameReportController => getGameTransactionPaginate :" + e);
        }
        return response;
    }
}
