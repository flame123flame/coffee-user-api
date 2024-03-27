package coffee.backoffice.rebate.controller;

import java.util.List;

import coffee.backoffice.rebate.model.RebateBatchWaiting;
import coffee.backoffice.rebate.service.CalculateRebateService;
import coffee.backoffice.rebate.vo.res.RebateStatMonthDailyRes;
import coffee.backoffice.rebate.vo.res.RebateStatTitleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.rebate.service.RebateService;
import coffee.backoffice.rebate.vo.req.RebateReq;
import coffee.backoffice.rebate.vo.res.RebateRes;
import framework.constant.ResponseConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.DELETE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.EDIT;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/rebate")
@Slf4j
public class RebateController {

	@Autowired
	private RebateService rebateService;
	@Autowired
	private CalculateRebateService calculateRebateService;
	
	// GET ONE
	@GetMapping("get-by-id/{code}")
	@ResponseBody
	public ResponseData<RebateRes> getId(@PathVariable("code") String code) {
		ResponseData<RebateRes> response = new ResponseData<RebateRes>();
		try {
			response.setData(rebateService.getOne(code));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => RebateController.getId() : " + e);
		}
		return response;
	}

	// GET ALL
	@GetMapping
	@ResponseBody
	public ResponseData<List<RebateRes>> getAll() {
		ResponseData<List<RebateRes>> response = new ResponseData<List<RebateRes>>();
		try {
			response.setData(rebateService.getAll());
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getAll()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.getAll() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/paginate")
	@ResponseBody
	public ResponseData<DataTableResponse<RebateRes>> getDataPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<RebateRes>> response = new ResponseData<DataTableResponse<RebateRes>>();
		try {
			response.setData(rebateService.getPaginateModel(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.getDataPaginate() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/pending-paginate")
	@ResponseBody
	public ResponseData<DataTableResponse<RebateBatchWaiting>> getDataPendingPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<RebateBatchWaiting>> response = new ResponseData<DataTableResponse<RebateBatchWaiting>>();
		try {
			response.setData(calculateRebateService.getPendingPaginate(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.getDataPaginate() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/stat-paginate-title")
	@ResponseBody
	public ResponseData<DataTableResponse<RebateStatTitleRes>> getDataStatPaginateTitlePaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<RebateStatTitleRes>> response = new ResponseData<DataTableResponse<RebateStatTitleRes>>();
		try {
			response.setData(rebateService.getPaginateModelCashbackStatTitle(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.getDataPaginate() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/stat-paginate-month")
	@ResponseBody
	public ResponseData<DataTableResponse<RebateStatMonthDailyRes>> getDataStatPaginateMonthPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<RebateStatMonthDailyRes>> response = new ResponseData<DataTableResponse<RebateStatMonthDailyRes>>();
		try {
			response.setData(rebateService.getPaginateModelCashbackStatMonth(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.getDataPaginate() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/stat-paginate-daily")
	@ResponseBody
	public ResponseData<DataTableResponse<RebateStatMonthDailyRes>> getDataStatPaginateDailyPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<RebateStatMonthDailyRes>> response = new ResponseData<DataTableResponse<RebateStatMonthDailyRes>>();
		try {
			response.setData(rebateService.getPaginateModelCashbackStatDaily(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.getDataPaginate() :" + e);
		}
		return response;
	}

	// INSERT
	@PostMapping
	@ResponseBody
	public ResponseData<RebateRes> insert(@RequestBody RebateReq param) {
		ResponseData<RebateRes> response = new ResponseData<RebateRes>();
		try {
			rebateService.insertOne(param);
			response.setMessage(SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.insert()");
		} catch (Exception e) {
			response.setMessage(SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.insert() :" + e);
		}
		return response;
	}

	// UPDATE
	@PutMapping
	@ResponseBody
	public ResponseData<RebateRes> update(@RequestBody RebateReq param) {
		ResponseData<RebateRes> response = new ResponseData<RebateRes>();
		try {
			rebateService.updateOne(param);
			response.setMessage(EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.update()");
		} catch (Exception e) {
			response.setMessage(EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.update() :" + e);
		}
		return response;
	}

	// DELETE
	@DeleteMapping("/{code}")
	@ResponseBody
	public ResponseData<RebateRes> delete(@PathVariable("code") String code) {
		ResponseData<RebateRes> response = new ResponseData<RebateRes>();
		try {
			rebateService.delete(code);
			response.setMessage(DELETE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.delete()");
		} catch (Exception e) {
			response.setMessage(DELETE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API RebateController.delete() :" + e);
		}
		return response;
	}


	// GET ONE
	@GetMapping("/approve-rebate/{code}")
	@ResponseBody
	public ResponseData<?> approveByCode(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateRebateService.approveRebateByCode(code);
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => RebateController.getId() : " + e);
		}
		return response;
	}

	// GET ONE
	@GetMapping("/approve-all-rebate/{code}")
	@ResponseBody
	public ResponseData<?> approveAllByCashbackCode(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateRebateService.approveAllRebateByRebateCode(code);
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => RebateController.getId() : " + e);
		}
		return response;
	}

	// GET ONE
	@GetMapping("/reject-rebate/{code}")
	@ResponseBody
	public ResponseData<?> rejectCashback(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateRebateService.rejectRebateByCode(code);
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => RebateController.getId() : " + e);
		}
		return response;
	}

	// GET ONE
	@GetMapping("/reject-all-rebate/{code}")
	@ResponseBody
	public ResponseData<?> rejectAllCashback(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateRebateService.rejectAllRebateByRebateCode(code);
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => RebateController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => RebateController.getId() : " + e);
		}
		return response;
	}
}
