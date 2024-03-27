package coffee.backoffice.player.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.service.GroupLevelService;
import coffee.backoffice.player.vo.req.GroupLevelReq;
import coffee.backoffice.player.vo.res.GroupLevelRes;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/groupLevel")
@Slf4j
public class GroupLevelController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupLevelController.class);
	
	@Autowired
	private GroupLevelService groupLevelService;
		
	@GetMapping("/getAllGroupLevel")
	@ResponseBody
	public ResponseData<List<GroupLevelRes>> getAllGroupLevel() {
		ResponseData<List<GroupLevelRes>> response = new ResponseData<List<GroupLevelRes>>();
		try {
			response.setData(groupLevelService.getAll());
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => groupLevel.get()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => groupLevel.get() :" + e);
		}
		return response;
	}
	
	@PostMapping("/saveNewGroupLevel")
	@ResponseBody
	public ResponseData<String> saveNewGroupLevel(@RequestBody GroupLevelReq form) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			groupLevelService.saveNewGroupLevel(form);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => saveNewGroupLevel");
		} catch (Exception e) {
			log.error("saveNewGroupLevel::save", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PutMapping("/editGroupLevel")
	public ResponseData<?> editGroupLevel(@RequestBody GroupLevelReq form) {
		ResponseData<?> response = new ResponseData<>();
		try {
			groupLevelService.editGroupLevel(form);
			response.setMessage(RESPONSE_MESSAGE.EDIT.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => editGroupLevel");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API => editGroupLevel :" + e);
		}
		return response;
	}
	
	@DeleteMapping("/deleteGroupLevel/{groupCode}")
	public ResponseData<?> deleteGroupLevel(@PathVariable("groupCode") String groupCode){
		ResponseData<?> responseData = new ResponseData<>();
		try {
			groupLevelService.deleteGroupLevel(groupCode);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => deleteGroupLevel");
		} catch (Exception e) {
			log.error("GroupLevelController: deleteGroupLevel ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("/getGroupLevelByGroupCode/{groupCode}")
	public ResponseData<GroupLevelRes> getGroupLevelByGroupCode(@PathVariable("groupCode") String groupCode) {
		ResponseData<GroupLevelRes> responseData = new ResponseData<GroupLevelRes>();
		try {
			responseData.setData(groupLevelService.getGroupLevelByGroupCode(groupCode));
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => getGroupLevelById");
		} catch (Exception e) {
			log.error("GroupLevelController: getGroupLevelById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("/get-dropdown-group")
	public ResponseData<List<GroupLevel>> getDropdownGroup(){
		ResponseData<List<GroupLevel>> responseData = new ResponseData<List<GroupLevel>>();
		try {
			responseData.setData(groupLevelService.getDropdownGroup());
			responseData.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API GroupLevelController => getDropdownGroup");
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GroupLevelController => getDropdownGroup :" + e);
		}
		return responseData;
	}
}
