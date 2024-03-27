package coffee.backoffice.rebate.controller;

import java.util.List;

import coffee.backoffice.promotion.vo.res.PromotionDatatableRes;
import coffee.backoffice.rebate.vo.res.RebateHistoryDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import coffee.backoffice.rebate.model.Rebate;
import coffee.backoffice.rebate.service.CalculateRebateService;
import coffee.backoffice.rebate.service.RebateHistoryService;
import coffee.backoffice.rebate.vo.res.RebateHistoryRes;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/rebate-history/")
@Slf4j
public class RebateHistoryController {

	@Autowired
	private RebateHistoryService rebateHistoryService;

	//  GET PAGINATE
	@PostMapping("/paginate")
	@ResponseBody
	public ResponseData<DataTableResponse<RebateHistoryDatatableRes>> getDataPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<RebateHistoryDatatableRes>> response = new ResponseData<DataTableResponse<RebateHistoryDatatableRes>>();
		try {
			response.setData(rebateHistoryService.getPaginate(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashBackConditionController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashBackConditionController.getDataPaginate() :" + e);
		}
		return response;
	}
}
