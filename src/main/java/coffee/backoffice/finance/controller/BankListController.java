package coffee.backoffice.finance.controller;

import java.util.List;

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

import coffee.backoffice.finance.model.Bank;
import coffee.backoffice.finance.service.BankService;
import coffee.backoffice.finance.vo.req.BankReq;
import coffee.backoffice.finance.vo.res.BankRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/bank/")
@Slf4j
public class BankListController {

	@Autowired
	private BankService bankService;

	@GetMapping("get-bank-all")
	public ResponseData<List<Bank>> getBankAll() {
		ResponseData<List<Bank>> response = new ResponseData<List<Bank>>();
		try {
			response.setData(bankService.getBankAll());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BankListController => getBankAll");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API BankListController => getBankAll :" + e);
		}
		return response;
	}

	@PostMapping("get-bank-all-paginate")
	@ResponseBody
	public ResponseData<DataTableResponse<BankRes>> getBankAllPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<BankRes>> response = new ResponseData<DataTableResponse<BankRes>>();
		try {
			response.setData(bankService.getPaginateModel(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BankListController => getBankAll");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API BankListController => getBankAll :" + e);
		}
		return response;
	}

	@PostMapping("save-bank")
	@ResponseBody
	public ResponseData<?> saveBank(@RequestBody BankReq form) {
		ResponseData<?> response = new ResponseData<>();
		try {
			bankService.saveBank(form);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BankListController => saveBank");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API BankListController => saveBank :" + e);
		}
		return response;
	}

	@PutMapping("edit-bank")
	public ResponseData<?> editBank(@RequestBody BankReq form) {
		ResponseData<?> response = new ResponseData<>();
		try {
			bankService.editBank(form);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BankListController => editBank");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API BankListController => editBank :" + e);
		}
		return response;
	}

	@GetMapping("get-bank-by-code/{bankCode}")
	public ResponseData<BankRes> getBankByCode(@PathVariable("bankCode") String bankCode) {
		ResponseData<BankRes> responseData = new ResponseData<BankRes>();
		try {
			responseData.setData(bankService.getBankByCode(bankCode));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BankListController => getBankByCode");
		} catch (Exception e) {
			log.error("Error Calling API BankListController =>  getBankByCode ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@DeleteMapping("delete-bank/{bankCode}")
	public ResponseData<?> deleteBankByCode(@PathVariable("bankCode") String bankCode) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			bankService.deleteBankByCode(bankCode);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API BankListController => deleteBankByCode");
		} catch (Exception e) {
			log.error("Error Calling API BankListController =>  deleteBankByCode ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
