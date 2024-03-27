package coffee.backoffice.cashback.controller;

import java.util.List;

import coffee.backoffice.cashback.model.CashbackBatchWaiting;
import coffee.backoffice.cashback.vo.res.CashbackStatMonthDailyRes;
import coffee.backoffice.cashback.vo.res.CashbackStatTitleRes;
import coffee.backoffice.rebate.service.CalculateRebateService;
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

import coffee.backoffice.cashback.service.CalculateCashbackService;
import coffee.backoffice.cashback.service.CashbackService;
import coffee.backoffice.cashback.vo.req.CashbackReq;
import coffee.backoffice.cashback.vo.res.CashbackRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
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
@RequestMapping("api/cashback")
@Slf4j
public class CashbackController {
	@Autowired
	private CashbackService cashbackService;
	@Autowired
	CalculateCashbackService calculateCashbackService;
	@Autowired
	CalculateRebateService calculateRebateService;


	// GET ONE
	@GetMapping("/test-cashback")
	@ResponseBody
	public ResponseData<?> testCashBack() {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateCashbackService.findCashbackDaily(true);
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}// GET ONE
	@GetMapping("/test-cashback2")
	@ResponseBody
	public ResponseData<?> testCashBack2() {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateCashbackService.doCashback(true);
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}
	// GET ONE
	@GetMapping("/get-by-id/{code}")
	@ResponseBody
	public ResponseData<CashbackRes> getId(@PathVariable("code") String code) {
		ResponseData<CashbackRes> response = new ResponseData<CashbackRes>();
		try {
			response.setData(cashbackService.getOne(code));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}

	// GET ALL
	@GetMapping
	@ResponseBody
	public ResponseData<List<CashbackRes>> getAll() {
		ResponseData<List<CashbackRes>> response = new ResponseData<List<CashbackRes>>();
		try {
			response.setData(cashbackService.getAll());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getAll()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.getAll() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/paginate")
	@ResponseBody
	public ResponseData<DataTableResponse<CashbackRes>> getDataPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<CashbackRes>> response = new ResponseData<DataTableResponse<CashbackRes>>();
		try {
			response.setData(cashbackService.getPaginateModel(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.getDataPaginate() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("/pending-paginate")
	@ResponseBody
	public ResponseData<DataTableResponse<CashbackBatchWaiting>> getDataPendingPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<CashbackBatchWaiting>> response = new ResponseData<DataTableResponse<CashbackBatchWaiting>>();
		try {
			response.setData(calculateCashbackService.getPendingPaginate(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.getDataPaginate() :" + e);
		}
		return response;
	}

	@PostMapping("/stat-paginate-title")
	@ResponseBody
	public ResponseData<DataTableResponse<CashbackStatTitleRes>> getDataStatPaginateTitlePaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<CashbackStatTitleRes>> response = new ResponseData<DataTableResponse<CashbackStatTitleRes>>();
		try {
			response.setData(cashbackService.getPaginateModelCashbackStatTitle(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.getDataPaginate() :" + e);
		}
		return response;
	}

	@PostMapping("/stat-paginate-month")
	@ResponseBody
	public ResponseData<DataTableResponse<CashbackStatMonthDailyRes>> getDataStatPaginateMonthPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<CashbackStatMonthDailyRes>> response = new ResponseData<DataTableResponse<CashbackStatMonthDailyRes>>();
		try {
			response.setData(cashbackService.getPaginateModelCashbackStatMonth(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.getDataPaginate() :" + e);
		}
		return response;
	}

	@PostMapping("/stat-paginate-daily")
	@ResponseBody
	public ResponseData<DataTableResponse<CashbackStatMonthDailyRes>> getDataStatPaginateDailyPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<CashbackStatMonthDailyRes>> response = new ResponseData<DataTableResponse<CashbackStatMonthDailyRes>>();
		try {
			response.setData(cashbackService.getPaginateModelCashbackStatDaily(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.getDataPaginate() :" + e);
		}
		return response;
	}

	// INSERT
	@PostMapping
	@ResponseBody
	public ResponseData<CashbackRes> insert(@RequestBody CashbackReq param) {
		ResponseData<CashbackRes> response = new ResponseData<CashbackRes>();
		try {
			cashbackService.insertOne(param);
			response.setMessage(SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.insert()");
		} catch (Exception e) {
			response.setMessage(SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.insert() :" + e);
		}
		return response;
	}

	// UPDATE
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseData<CashbackRes> update(@PathVariable("id")Long id,@RequestBody CashbackReq param) {
		ResponseData<CashbackRes> response = new ResponseData<CashbackRes>();
		try {
			cashbackService.updateOne(id,param);
			response.setMessage(EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.update()");
		} catch (Exception e) {
			response.setMessage(EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.update() :" + e);
		}
		return response;
	}

	// DELETE
	@DeleteMapping("/{code}")
	@ResponseBody
	public ResponseData<CashbackRes> delete(@PathVariable("code") String code) {
		ResponseData<CashbackRes> response = new ResponseData<CashbackRes>();
		try {
			cashbackService.delete(code);
			response.setMessage(DELETE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.delete()");
		} catch (Exception e) {
			response.setMessage(DELETE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.delete() :" + e);
		}
		return response;
	}

	// DELETE
	@GetMapping("get-detail/{code}")
	@ResponseBody
	public ResponseData<CashbackRes> getDetail(@PathVariable("code") String code) {
		ResponseData<CashbackRes> response = new ResponseData<CashbackRes>();
		try {
			response.setData(cashbackService.getDetail(code));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.delete()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CashbackController.delete() :" + e);
		}
		return response;
	}


	// GET ONE
	@GetMapping("/test-rebate")
	@ResponseBody
	public ResponseData<?> testRebate() {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateRebateService.findRebateDaily(true);
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}


	// GET ONE
	@GetMapping("/test-rebate2")
	@ResponseBody
	public ResponseData<?> testRebate2() {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateRebateService.doRebateWaiting();
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}

	// GET ONE
	@GetMapping("/approve-cashback/{code}")
	@ResponseBody
	public ResponseData<?> approveByCode(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateCashbackService.approveByCode(code);
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}

	// GET ONE
	@GetMapping("/approve-all-cashback/{code}")
	@ResponseBody
	public ResponseData<?> approveAllByCashbackCode(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateCashbackService.approveAllByCashbackCode(code);
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}

	// GET ONE
	@GetMapping("/reject-cashback/{code}")
	@ResponseBody
	public ResponseData<?> rejectCashback(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateCashbackService.rejectCashback(code);
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}

	// GET ONE
	@GetMapping("/reject-all-cashback/{code}")
	@ResponseBody
	public ResponseData<?> rejectAllCashback(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			calculateCashbackService.rejectAllCashback(code);
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CashbackController.getId()");
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => CashbackController.getId() : " + e);
		}
		return response;
	}
}
