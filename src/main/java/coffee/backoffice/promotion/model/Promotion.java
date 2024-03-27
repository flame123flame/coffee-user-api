package coffee.backoffice.promotion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.vo.req.PromotionReq;
import lombok.Data;

@Data
@Entity
@Table(name = "promotion")
public class Promotion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2407905908267418257L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "promo_code")
	private String promoCode;
	
	@Column(name = "promo_type")
	private String  promoType;
	
	@Column(name = "promo_title")
	private String promoTitle;
	
	@Column(name = "app_platform")
	private String  appPlatform;
	
	@Column(name = "promo_period_type")
	private String  promoPeriodType;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "view_status")
	private String  viewStatus = PromotionConstant.Status.SHOW;
	
	@Column(name = "created_by")
	private String  createdBy;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	public void setReqToEntity(PromotionReq req) {
		
		id = req.getId() !=null ? req.getId(): id;
		promoCode = req.getPromoCode() !=null ? req.getPromoCode(): promoCode;
		promoTitle = req.getPromoTitle() !=null ? req.getPromoTitle(): promoTitle;
		promoType = req.getPromoType() !=null ? req.getPromoType(): promoType;
		appPlatform = req.getAppPlatform() !=null ? req.getAppPlatform(): appPlatform;
		promoPeriodType = req.getPromoPeriodType() !=null ? req.getPromoPeriodType(): promoPeriodType;
		startDate = req.getStartDate() !=null ? req.getStartDate(): startDate;
		endDate = req.getEndDate() !=null ? req.getEndDate(): endDate;
		viewStatus = req.getViewStatus() !=null ? req.getViewStatus(): viewStatus;
	}
	
}
