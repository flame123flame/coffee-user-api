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

import coffee.backoffice.casino.model.GameProvider;
import coffee.backoffice.casino.model.GameProviderNoIcon;
import coffee.backoffice.casino.service.GameProviderService;
import coffee.backoffice.casino.vo.req.GameProviderReq;
import coffee.backoffice.casino.vo.res.GameProviderRes;
import framework.constant.ResponseConstant;
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
@RequestMapping("api/game-provider/")
@Slf4j
public class GameProviderController {
	@Autowired
	private GameProviderService gameProviderService;


	// GET PAGINATE
	@GetMapping("/{code}")
	public ResponseData<GameProviderRes> getProviderById(
			@PathVariable String code) {
		ResponseData<GameProviderRes> response = new ResponseData<GameProviderRes>();
		try {
			response.setData(gameProviderService.getProviderByCode(code));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getDataPaginateProvider()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getDataPaginateProvider() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("get-paginate-provider")
	public ResponseData<DataTableResponse<GameProviderRes>> getDataPaginateProvider(
			@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<GameProviderRes>> response = new ResponseData<DataTableResponse<GameProviderRes>>();
		try {
			response.setData(gameProviderService.getPaginateProvider(param));
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getDataPaginateProvider()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getDataPaginateProvider() :" + e);
		}
		return response;
	}

	// DELETE
	@DeleteMapping("delete-provider/{code}")
	public ResponseData<?> delete(@PathVariable("code") String code) {
		ResponseData<?> response = new ResponseData<>();
		try {
			gameProviderService.deleteByCode(code);
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.delete()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.delete() :" + e);
		}
		return response;
	}

	// SAVE
	@PostMapping("save-provider")
	public ResponseData<String> saveProvider(@RequestBody GameProviderReq param) {
		ResponseData<String> response = new ResponseData<>();
		try {
			response.setData(gameProviderService.saveProvider(param));
			response.setMessage(response.getData());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.saveProvider()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.saveProvider() :" + e);
		}
		return response;
	}

//	EDIT
	@PutMapping("edit-provider")
	public ResponseData<?> editProvider(@RequestBody GameProviderReq param) {
		ResponseData<?> response = new ResponseData<>();
		try {
			gameProviderService.editProvider(param);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.editProvider()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.editProvider() :" + e);
		}
		return response;
	}

//	EDIT
	@PutMapping("change-status-view")
	public ResponseData<?> changeStatusView(@RequestBody GameProviderReq param) {
		ResponseData<?> response = new ResponseData<>();
		try {
			gameProviderService.changeStatusView(param);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.changeStatusView()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.changeStatusView() :" + e);
		}
		return response;
	}

//	GET ALL
	@GetMapping("get-all-provider")
	public ResponseData<List<GameProvider>> getAllProvider() {
		ResponseData<List<GameProvider>> response = new ResponseData<List<GameProvider>>();
		try {
			response.setData(gameProviderService.getAll());
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getAllProvider()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getAllProvider() :" + e);
		}
		return response;
	}

//	GET ALL DROPDOWN
	@GetMapping("get-provider-list")
	public ResponseData<List<GameProviderNoIcon>> getDropdownProvider() {
		ResponseData<List<GameProviderNoIcon>> response = new ResponseData<List<GameProviderNoIcon>>();
		try {
			response.setData(gameProviderService.getDropdownProvider());
			response.setMessage(GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameProviderController.getDropdownProvider()");
		} catch (Exception e) {
			response.setMessage(GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GameProviderController.getDropdownProvider() :" + e);
		}
		return response;
	}

}
