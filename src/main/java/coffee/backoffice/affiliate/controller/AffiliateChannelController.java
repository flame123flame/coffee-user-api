package coffee.backoffice.affiliate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.affiliate.model.AffiliateChannel;
import coffee.backoffice.affiliate.service.AffiliateChannelService;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/affiliate-channel/")
@Slf4j
public class AffiliateChannelController {
	
	@Autowired
	private AffiliateChannelService channelService;
	
	@DeleteMapping("delete-affiliate-channel/{affiliateChannelCode}")
	public ResponseData<List<AffiliateChannel>> deleteAffiliateChannel(@PathVariable("affiliateChannelCode") String code){
		ResponseData<List<AffiliateChannel>> responseData = new ResponseData<List<AffiliateChannel>>();
		try {
			responseData.setData(channelService.updateEnableAffiliateChannel(code));
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API AffiliateChannelController => deleteAffiliateChannel");
		}catch(Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API AffiliateChannelController => deleteAffiliateChannel :" + e);
		}
		return responseData;
		
	}
}
