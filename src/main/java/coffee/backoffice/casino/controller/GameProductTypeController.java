package coffee.backoffice.casino.controller;

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

import coffee.backoffice.casino.model.GameProductType;
import coffee.backoffice.casino.model.GameProductTypeNoIcon;
import coffee.backoffice.casino.service.GameProductTypeService;
import coffee.backoffice.casino.vo.req.GameProductTypeReq;
import coffee.backoffice.casino.vo.res.GameProductTypeRes;
import coffee.backoffice.casino.vo.res.GameProductTypeWithProviderRes;
import framework.constant.ResponseConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.DELETE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/game-product-type/")
@Slf4j
public class GameProductTypeController {

	@Autowired
	private GameProductTypeService gameProductTypeService;

	// GET PAGINATE
	@PostMapping("get-paginate-product")
	public ResponseData<DataTableResponse<GameProductTypeRes>> getDataPaginateProduct(
			@RequestBody DatatableRequest req) {
		ResponseData<DataTableResponse<GameProductTypeRes>> response = new ResponseData<DataTableResponse<GameProductTypeRes>>();
		try {
			response.setData(gameProductTypeService.getPaginateProduct(req));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProductTypeController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProductTypeController.getDataPaginate() :" + e);
		}
		return response;
	}

	// DELETE
	@DeleteMapping("delete-product/{code}")
	public ResponseData<GameProductTypeRes> deleteProduct(@PathVariable("code") String code) {
		ResponseData<GameProductTypeRes> response = new ResponseData<GameProductTypeRes>();
		try {
			gameProductTypeService.deleteProduct(code);
			response.setMessage(DELETE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProductTypeController.deleteProduct()");
		} catch (Exception e) {
			response.setMessage(DELETE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProductTypeController.deleteProduct() :" + e);
		}
		return response;
	}

//	GET BY CODE
	@GetMapping("get-product-by-code/{code}")
	public ResponseData<GameProductType> getProductTypeByCode(@PathVariable("code") String code) {
		ResponseData<GameProductType> response = new ResponseData<GameProductType>();
		try {
			response.setData(gameProductTypeService.getGameProductTypeByCode(code));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProductTypeController.getProductTypeByCode()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => GameProductTypeController.getProductTypeByCode() : " + e);
		}
		return response;
	}
	
	@GetMapping("/product-list")
	public ResponseData<List<GameProductType>> productList(){
		ResponseData<List<GameProductType>> responseData = new ResponseData<List<GameProductType>>();
		try {
			responseData.setData(gameProductTypeService.getAll());
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("ProvidersController: productList SUCCESS CALLING");
		}catch (Exception e) {
			log.error("ProvidersController: productList ", e);
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

//	SAVE
	@PostMapping("save-product")
	public ResponseData<String> saveProdut(@RequestBody GameProductTypeReq param) {
		ResponseData<String> response = new ResponseData<>();
		try {
			response.setData(gameProductTypeService.saveProduct(param));
			response.setMessage(response.getData());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProductTypeController.saveProdut()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProductTypeController.saveProdut() :" + e);
		}
		return response;
	}

//	EDIT
	@PutMapping("edit-product")
	public ResponseData<String> editProdut(@RequestBody GameProductTypeReq param) {
		ResponseData<String> response = new ResponseData<>();
		try {
			response.setData(gameProductTypeService.editProduct(param));
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProductTypeController.editProdut()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProductTypeController.editProdut() :" + e);
		}
		return response;
	}

//	GET ALL
	@GetMapping("get-all-product")
	public ResponseData<List<GameProductType>> getAllProduct() {
		ResponseData<List<GameProductType>> response = new ResponseData<List<GameProductType>>();
		try {
			response.setData(gameProductTypeService.getAll());
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProductTypeController.getAllProduct()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProductTypeController.getAllProduct() :" + e);
		}
		return response;
	}

	// GET get-product-type-with-provider
	@GetMapping("get-product-type-with-provider")
	public ResponseData<List<GameProductTypeWithProviderRes>> getTree() {
		ResponseData<List<GameProductTypeWithProviderRes>> response = new ResponseData<List<GameProductTypeWithProviderRes>>();
		try {
			response.setData(gameProductTypeService.getTree());
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProductTypeController.getAllProduct()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProductTypeController.getAllProduct() :" + e);
		}
		return response;
	}

	// GET ALL DROPDOWN
	@GetMapping("get-product-type-list")
	public ResponseData<List<GameProductTypeNoIcon>> getDropdownProductType() {
		ResponseData<List<GameProductTypeNoIcon>> response = new ResponseData<List<GameProductTypeNoIcon>>();
		try {
			response.setData(gameProductTypeService.getDropdownGameProductType());
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getDropdownProductType()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getAllProvider() :" + e);
		}
		return response;
	}

}
