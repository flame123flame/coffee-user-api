package coffee.website.player.service;

import coffee.backoffice.casino.model.ProviderSummary;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.finance.model.Deposit;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.service.DepositService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.TagManagement;
import coffee.backoffice.player.repository.dao.CustomerDao;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.player.repository.jpa.TagManagementJpa;
import coffee.backoffice.player.vo.model.RebateDetail;
import coffee.backoffice.player.vo.model.TurnOverDetail;
import coffee.backoffice.player.vo.req.CustomerResetPasswordReq;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.model.RuleSetting;
import coffee.backoffice.promotion.service.PromotionService;
import coffee.backoffice.rebate.service.CalculateRebateService;
import framework.constant.ProjectConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerPlayerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TagManagementJpa tagManagementJpa;

    @Autowired
    private ProviderSummaryService providerSummaryService;

    @Autowired
    private CalculateRebateService calculateRebateService;

    @Autowired
    private WithdrawConditionService withdrawConditionService;

    @Autowired
    private PromotionService promotionService;
    
    @Autowired
    private WalletService walletService;
    
    @Autowired
    private DepositService depositService;
    
    public CustomerRes getCustomerByUsername(String username) {
        CustomerRes res = customerDao.findCustomerByUsername(username);
        if (res.getTagCode() != null) {
            String[] listTagCode = res.getTagCode().split(",");
            List<String> tempList = new ArrayList<>();
            for (String code : listTagCode) {
                Optional<TagManagement> data = tagManagementJpa.findByTagCode(code);
                tempList.add(data.get().getName());
            }
            res.setTagName(String.join(",", tempList));
        }
        return res;
    }

    public Customer forgotPasswordCustomer(String phoneNumber) {
        return customerRepository.findByMobilePhone(phoneNumber);
    }

    public void resetPasswordCustomer(CustomerResetPasswordReq form) {
        Customer data = customerRepository.findByMobilePhone(form.getPhoneNumber());
        data.setPassword(passwordEncoder.encode(form.getNewPassword()));
        customerRepository.save(data);
    }

    public BigDecimal getBetDaily(String username) {
        BigDecimal current = BigDecimal.ZERO;
        List<ProviderSummary> list = providerSummaryService.getProviderSummary(username);
        for (ProviderSummary data : list) {
            current = current.add(data.getBetDaily());
        }
        return current;
    }

    public RebateDetail getRebate(String username) {
        RebateDetail res = new RebateDetail();
        res.setCurrentRebate(calculateRebateService.getRebateDetail(username));
        System.out.println("res.setCurrentRebate >> " + res.getCurrentRebate());
        return res;

    }

    public TurnOverDetail getTurnOver(String username) {
    	TurnOverDetail res = null;
    	List<PromotionMapping> mappingList = promotionService.findMappingByUsername(username);
    	
    	if(mappingList !=null && mappingList.size() > 0) {
    		for(PromotionMapping mapping:mappingList) {
    			Promotion promotion = promotionService.getByPromoCode(mapping.getPromoCode());
    			if(promotion != null) {
    				if(promotion.getPromoType().equals(PromotionConstant.Type.registration)) {
    					Wallet wallet = walletService.findWalletData(username);
        	    		
        	    		res = new TurnOverDetail();
        	    		res.setWording("สมาชิกที่รับโบนัสเครดิตฟรีจะต้องถอนยอดเครดิตทั้งหมดในทีเดียว โดยที่คุณจะได้รับเงินฟรี 10% จากยอดที่คุณทำได้ (รับได้สูงสุด 1,000บาท)");
        	    		RuleSetting rule = promotionService.getRuleSettingByPromoCode(mapping.getPromoCode());
        	    		BigDecimal percentage = new BigDecimal(10);
        	    		if(rule.getMaxWithdraw() != null) {
        	    			percentage = rule.getMaxWithdraw().divide(new BigDecimal(100));
        	    		}
        	    		res.setWithdrawAmount(wallet.getBalance().multiply(percentage));
        	    		
        	    		List<Deposit> depositList = depositService.getDepositByUsernameAfter(username, mapping.getUpdatedDate());
        	    		if(depositList == null ) {
        	    			return res;
        	    		}
    				}
    			}
    		}
    	}
    	
        List<WithdrawCondition> listWithdraw = withdrawConditionService.getWithdrawConditionByUsername(username,
                ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);
        res = new TurnOverDetail();
        BigDecimal current = BigDecimal.ZERO;
        BigDecimal target = BigDecimal.ZERO;
        for (WithdrawCondition withdraw : listWithdraw) {
            current = current.add(withdraw.getCurrentTurnover());
            target = target.add(withdraw.getTargetTurnover());
        }
        res.setCurrentTurnOver(current);
        res.setTargetTurnOver(target);
        return res;

    }

}
