package coffee.backoffice.casino.controller;

import coffee.backoffice.casino.model.GameGroupMappingProvider;
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

import coffee.backoffice.casino.service.GameGroupMappingProviderService;
import coffee.backoffice.casino.vo.req.GameGroupMappingProviderReq;
import coffee.backoffice.casino.vo.res.GameGroupMapProviderRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.EDIT;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.GET;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/group-mapping-provider")
@Slf4j
public class GameGroupMappingProviderController {
	@Autowired
	private GameGroupMappingProviderService gameGroupMappingProviderService;

	// SAVE
	@PostMapping("save-mapping")
	public ResponseData<?> saveMapping(@RequestBody GameGroupMappingProviderReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			gameGroupMappingProviderService.saveMapping(req);
			response.setMessage(SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GroupMappingProviderController.saveMapping()");
		} catch (Exception e) {
			response.setMessage(SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GroupMappingProviderController.saveMapping() :" + e);
		}
		return response;
	}

	// EDIT
	@PutMapping("edit-mapping")
	public ResponseData<?> editMapping(@RequestBody GameGroupMappingProviderReq req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			gameGroupMappingProviderService.editMapping(req);
			response.setMessage(EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GroupMappingProviderController.editMapping()");
		} catch (Exception e) {
			response.setMessage(EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GroupMappingProviderController.editMapping() :" + e);
		}
		return response;
	}

	// GET BY PRODUCT CODE
	@GetMapping("get-mapping/{groupCode}")
	public ResponseData<GameGroupMapProviderRes> getMapping(@PathVariable("groupCode") String ggCode) {
		ResponseData<GameGroupMapProviderRes> response = new ResponseData<GameGroupMapProviderRes>();
		try {
			response.setData(gameGroupMappingProviderService.getByGroupCode(ggCode));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GroupMappingProviderController.getMapping()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GroupMappingProviderController.getMapping() :" + e);
		}
		return response;
	}

	// GET BY PRODUCT CODE
	@GetMapping("get-all-by-provider-code/{providerCode}")
	public ResponseData<List<GameGroupMappingProvider>> getAllByProviderCode(@PathVariable("providerCode") String ggCode) {
		ResponseData<List<GameGroupMappingProvider>> response = new ResponseData<List<GameGroupMappingProvider>>();
		try {
			response.setData(gameGroupMappingProviderService.getAllByProviderCode(ggCode));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GroupMappingProviderController.getAllByProviderCode()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GroupMappingProviderController.getAllByProviderCode() :" + e);
		}
		return response;
	}

	@GetMapping
	public ResponseData<List<GameGroupMappingProvider>> getAll() {
		ResponseData<List<GameGroupMappingProvider>> response = new ResponseData<List<GameGroupMappingProvider>>();
		try {
			response.setData(gameGroupMappingProviderService.getAll());
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GroupMappingProviderController.getAll()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GroupMappingProviderController.getAll() :" + e);
		}
		return response;
	}
}
