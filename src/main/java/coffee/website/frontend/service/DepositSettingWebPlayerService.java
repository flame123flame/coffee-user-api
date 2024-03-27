package coffee.website.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.Deposit;
import coffee.backoffice.finance.service.DepositService;
import coffee.backoffice.frontend.model.DepositSetting;
import coffee.backoffice.frontend.service.DepositSettingService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.PostSetting;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.service.PromotionService;
import coffee.website.frontend.vo.res.DepositSettingWebPlayerRes;
import framework.constant.ProjectConstant;

@Service
public class DepositSettingWebPlayerService {

	@Autowired
	private DepositSettingService depositSettingService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
    private DepositService depositService;

	public DepositSettingWebPlayerRes transferStatus(String username) {
		DepositSetting data = depositSettingService.getDepositSetting();
		DepositSettingWebPlayerRes res = new DepositSettingWebPlayerRes();
		
		Customer customer = customerService.getByUsername(username);
		if(customer.getBankStatus().equals("APPROVE")) {
			res.setUserStatus(ProjectConstant.USER_BANK_STATUS.ACCESS);
		}else {
			res.setUserStatus(ProjectConstant.USER_BANK_STATUS.REJECT);
		}
		res.setServerStatus(data.getMoneyTransfer());
		
		List<PromotionMapping> mappingList = promotionService.findMappingByUsername(username);
		if(mappingList !=null && mappingList.size() > 0) {
    		for(PromotionMapping mapping:mappingList) {
    			
    			Promotion promotion = promotionService.getByPromoCode(mapping.getPromoCode());
    			if(promotion != null) {
    				if(promotion.getPromoType().equals(PromotionConstant.Type.registration)) {
    					res.setUserStatus(ProjectConstant.USER_BANK_STATUS.CONDITION);
    					res.setWording("กรุณาถอนโบนัสเครดิตฟรีออกก่อนที่จะโอนเงินเข้ามา มิฉะนั้นยอดเครดิตทั้งหมดก่อนการทำรายการฝาก จะถือว่าเป็นโมฆะ");
    					List<Deposit> depositList = depositService.getDepositByUsernameAfter(username, mapping.getUpdatedDate());
        	    		if(depositList == null ) {
        	    			return res;
        	    		}
    				}
    			}
    		}
		}
		
		return res;
	}
}
