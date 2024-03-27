package coffee.backoffice.player.controller;

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
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.vo.req.BankVerifyReq;
import coffee.backoffice.player.vo.req.CustomerChangPasswordReq;
import coffee.backoffice.player.vo.req.CustomerChangeAccountNameReq;
import coffee.backoffice.player.vo.req.CustomerChangeBankAccountReq;
import coffee.backoffice.player.vo.req.CustomerChangeBankReq;
import coffee.backoffice.player.vo.req.CustomerChangeEnableReq;
import coffee.backoffice.player.vo.req.CustomerChangeGroupReq;
import coffee.backoffice.player.vo.req.CustomerChangeTagReq;
import coffee.backoffice.player.vo.req.CustomerReq;
import coffee.backoffice.player.vo.res.BankVerifyRes;
import coffee.backoffice.player.vo.res.CustomerRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/customer/")
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("get-customer-all")
	public ResponseData<List<CustomerRes>> getCustomerAll() {
		ResponseData<List<CustomerRes>> response = new ResponseData<List<CustomerRes>>();
		try {
			response.setData(customerService.getAll());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => getCustomerAll");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => getCustomerAll :" + e);
		}
		return response;
	}

	@PostMapping("get-customer-paginate")
	public ResponseData<DataTableResponse<CustomerRes>> getCustomerPaginate(@RequestBody DatatableRequest req) {
		ResponseData<DataTableResponse<CustomerRes>> response = new ResponseData<DataTableResponse<CustomerRes>>();
		try {
			response.setData(customerService.getCustomerPaginate(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CustomerController.getCustomerPaginate()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Failed Calling API => CustomerController.getCustomerPaginate() : " + e);
		}
		return response;
	}

	@PostMapping("save-customer")
	public ResponseData<String> saveCustomerBo(@RequestBody CustomerReq form) {
		ResponseData<String> response = new ResponseData<>();
		try {
			response.setData(customerService.saveCustomerBo(form));
			response.setMessage(response.getData());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => saveCustomerBo");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => saveCustomerBo :" + e);
		}
		return response;
	}

	@PutMapping("edit-customer")
	public ResponseData<?> editCustomer(@RequestBody CustomerReq form) {
		ResponseData<?> response = new ResponseData<>();
		try {
			customerService.editCustomer(form);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => editCustomer");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => editCustomer :" + e);
		}
		return response;
	}

	@GetMapping("get-customer-by-id/{username}")
	public ResponseData<CustomerRes> getCustomerByCode(@PathVariable("username") String username) {
		ResponseData<CustomerRes> responseData = new ResponseData<CustomerRes>();
		try {
			responseData.setData(customerService.getCustomerByUsername(username));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => getCustomerByCode");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => getCustomerByCode :" + e);
		}
		return responseData;
	}

	@DeleteMapping("delete-customer/{username}")
	public ResponseData<?> deleteCustomer(@PathVariable("username") String username) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			customerService.deleteCustomer(username);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => deleteCustomer");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => deleteCustomer :" + e);
		}
		return responseData;
	}

	@PutMapping("change-password")
	public ResponseData<String> changePasswordCustomer(@RequestBody CustomerChangPasswordReq form) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			if (customerService.changePasswordCustomer(form).equals(RESPONSE_STATUS.SUCCESS.toString())) {
				response.setData(RESPONSE_MESSAGE.SUCCESS);
				response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
				response.setStatus(RESPONSE_STATUS.SUCCESS);
				log.info("Success Calling API CustomerController => changePasswordCustomer");
			} else {
				response.setData("PSSWORD NOT MATCH");
				response.setMessage("PSSWORD NOT MATCH");
				response.setStatus(RESPONSE_STATUS.FAILED);
				log.info("Success Calling API CustomerController => changePasswordCustomer");
			}

		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API => changePasswordCustomer :" + e);
		}
		return response;
	}

	@GetMapping("get-dropdown-customer")
	public ResponseData<List<Customer>> getDropdownCustomer() {
		ResponseData<List<Customer>> response = new ResponseData<List<Customer>>();
		try {
			response.setData(customerService.getDropdownCustomer());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => getDropdownCustomer");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => getDropdownCustomer :" + e);
		}
		return response;
	}

	@GetMapping("get-bank-verify")
	public ResponseData<List<BankVerifyRes>> getBankVerify() {
		ResponseData<List<BankVerifyRes>> response = new ResponseData<List<BankVerifyRes>>();
		try {
			response.setData(customerService.getBankVerify());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => getCustomerAll");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => getCustomerAll :" + e);
		}
		return response;
	}
	
	@PostMapping("bank-verify-paginate")
	public ResponseData<DataTableResponse<BankVerifyRes>> getBankVerifyPaginate(@RequestBody DatatableRequest req) {
		ResponseData<DataTableResponse<BankVerifyRes>> response = new ResponseData<DataTableResponse<BankVerifyRes>>();
		try {
			response.setData(customerService.getBankVerifyPaginate(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CustomerController.getBankVerifyPaginate()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Failed Calling API => CustomerController.getBankVerifyPaginate() : " + e);
		}
		return response;
	}

	@PostMapping("bank-verify-duplicate-ac-paginate")
	public ResponseData<DataTableResponse<BankVerifyRes>> getBankVerifyDuplicateAcPaginate(
			@RequestBody DatatableRequest req) {
		ResponseData<DataTableResponse<BankVerifyRes>> response = new ResponseData<DataTableResponse<BankVerifyRes>>();
		try {
			response.setData(customerService.getBankVerifyDuplicateAcPaginate(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CustomerController.getBankVerifyDuplicatePaginate()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Failed Calling API => CustomerController.getBankVerifyDuplicatePaginate() : " + e);
		}
		return response;
	}
	
	@PostMapping("bank-verify-duplicate-rl-paginate")
	public ResponseData<DataTableResponse<BankVerifyRes>> getBankVerifyDuplicateRlPaginate(
			@RequestBody DatatableRequest req) {
		ResponseData<DataTableResponse<BankVerifyRes>> response = new ResponseData<DataTableResponse<BankVerifyRes>>();
		try {
			response.setData(customerService.getBankVerifyDuplicateRlPaginate(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CustomerController.getBankVerifyDuplicatePaginate()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Failed Calling API => CustomerController.getBankVerifyDuplicatePaginate() : " + e);
		}
		return response;
	}
	
	@PutMapping("change-bank-status")
	public ResponseData<?> changeBankStatus(@RequestBody BankVerifyReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {

			customerService.changeBankStatus(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => changeBankStatus");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => changeBankStatus :" + e);
		}
		return response;
	}

	@PutMapping("change-group")
	public ResponseData<?> changeGroup(@RequestBody CustomerChangeGroupReq req) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			customerService.changeGroup(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => changeGroup");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => changeGroup :" + e);
		}
		return response;
	}

	@PutMapping("change-tag")
	public ResponseData<?> changeTag(@RequestBody CustomerChangeTagReq req) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			customerService.changeTag(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => changeTag");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => changeTag :" + e);
		}
		return response;
	}

	@PutMapping("change-enable")
	public ResponseData<?> changeEnable(@RequestBody CustomerChangeEnableReq req) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			customerService.changeEnable(req);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => changeEnable");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => changeEnable :" + e);
		}
		return response;
	}
	
	@PutMapping("change-bank")
	public ResponseData<?> changeBank(@RequestBody CustomerChangeBankReq req) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			customerService.changeBank(req);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => changeBank");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => changeBank :" + e);
		}
		return response;
	}
	
	@PutMapping("change-bank-account")
	public ResponseData<?> changeBankAccount(@RequestBody CustomerChangeBankAccountReq req) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			customerService.changeBankAccount(req);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => changeBankAccount");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => changeBankAccount :" + e);
		}
		return response;
	}
	
	@PutMapping("change-account-name")
	public ResponseData<?> changeAccountName(@RequestBody CustomerChangeAccountNameReq req) {
		ResponseData<String> response = new ResponseData<String>();
		try {
			customerService.changeAccountName(req);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => changeAccountName");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => changeAccountName :" + e);
		}
		return response;
	}

	@GetMapping("get-user/{username}")
	public ResponseData<List<String>> getUsername(@PathVariable("username") String username) {
		ResponseData<List<String>> response = new ResponseData<>();
		try {
			response.setData(customerService.getUsername(username));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CustomerController.getUsername()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController.getUsername() :" + e);
		}
		return response;
	}

	@GetMapping("check-user-dummy/{username}")
	public ResponseData<Boolean> checkUserDummy(@PathVariable("username") String username) {
		ResponseData<Boolean> response = new ResponseData<>();
		try {
			response.setData(customerService.checkUserDummy(username));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => CustomerController.checkUserDummy()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController.checkUserDummy() :" + e);
		}
		return response;
	}

	@PostMapping("save-user-dummy-on-customer")
	public ResponseData<?> saveUserDummyOnCustomer(@RequestBody CustomerReq form) {
		ResponseData<?> response = new ResponseData<>();
		try {
			customerService.saveUserDummyOnCustomer(form);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => saveUserDummyOnCustomer");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => saveUserDummyOnCustomer :" + e);
		}
		return response;
	}

	@DeleteMapping("delete-user-dummy-on-customer-by-username/{username}")
	public ResponseData<?> deleteUserDummyOnCustomerByUsername(@PathVariable("username") String username) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			customerService.deleteUserDummyOnCustomerByUsername(username);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API CustomerController => deleteCustomer");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API CustomerController => deleteCustomer :" + e);
		}
		return responseData;
	}
}
