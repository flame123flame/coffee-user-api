package coffee.backoffice.promotion.vo.res;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.promotion.model.AppSetting;
import coffee.backoffice.promotion.model.PostSetting;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.model.RuleSetting;

@Data
public class PromotionRes {
	
    private Long id;
    private String promoCode;
    private String promoTitle;
    private String promoType;
    private String wallet;
    private String promoPeriodType;
    private Date startDate;
    private Date endDate;
    private String updatedBy;
    private Date updatedDate;
    private String viewStatus;
    
    public void setEntityToRes(Promotion entity) {
    	id = entity.getId();
    	promoCode = entity.getPromoCode();
    	promoTitle = entity.getPromoTitle();
    	promoType = entity.getPromoType();
    	promoPeriodType = entity.getPromoPeriodType();
    	startDate = entity.getStartDate();
    	endDate = entity.getEndDate();
    	updatedBy = entity.getUpdatedBy();
    	updatedDate = entity.getUpdatedDate();
    	viewStatus = entity.getViewStatus();
    }
   
}
