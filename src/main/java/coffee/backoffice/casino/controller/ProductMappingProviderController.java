package coffee.backoffice.casino.controller;

import coffee.backoffice.casino.model.ProductMappingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.casino.service.ProductMappingProviderService;
import coffee.backoffice.casino.vo.req.ProductMapProviderReq;
import coffee.backoffice.casino.vo.res.ProductMapProviderRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/product-mapping-provider")
@Slf4j
public class ProductMappingProviderController {
	@Autowired
	private ProductMappingProviderService productMappingProviderService;

	// SAVE
	@PostMapping("save-mapping")
	public ResponseData<?> saveMapping(@RequestBody ProductMapProviderReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			productMappingProviderService.saveMapping(req);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => ProductMappingProviderController.saveMapping()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ProductMappingProviderController.saveMapping() :" + e);
		}
		return response;
	}

	// EDIT
	@PutMapping("edit-mapping")
	public ResponseData<?> editMapping(@RequestBody ProductMapProviderReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			productMappingProviderService.editMapping(req);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => ProductMappingProviderController.editMapping()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ProductMappingProviderController.editMapping() :" + e);
		}
		return response;
	}

	// GET BY PRODUCT CODE
	@GetMapping("get-mapping/{productCode}")
	public ResponseData<ProductMapProviderRes> getMapping(@PathVariable("productCode") String pdCode) {
		ResponseData<ProductMapProviderRes> response = new ResponseData<ProductMapProviderRes>();
		try {
			response.setData(productMappingProviderService.getByProductCode(pdCode));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => ProductMappingProviderController.getMapping()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ProductMappingProviderController.getMapping() :" + e);
		}
		return response;
	}

	// GET BY PRODUCT CODE
	@GetMapping
	public ResponseData<List<ProductMappingProvider>> getAll() {
		ResponseData<List<ProductMappingProvider>> response = new ResponseData<List<ProductMappingProvider>>();
		try {
			response.setData(productMappingProviderService.getAll());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => ProductMappingProviderController.getMapping()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API ProductMappingProviderController.getMapping() :" + e);
		}
		return response;
	}
}
