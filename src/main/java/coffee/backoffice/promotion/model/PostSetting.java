package coffee.backoffice.promotion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import coffee.backoffice.promotion.vo.req.PostSettingReq;
import lombok.Data;

@Data
@Entity
@Table(name = "post_setting")
public class PostSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1004878127921875530L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "promo_code")
	private String promoCode;
	
	@Column(name = "promo_title")
	private String promoTitle;
	
	@Column(name = "lang")
	private String lang;
	
	@Column(name = "desk_banner")
	private String deskBanner;
	
	@Column(name = "desk_detail")
	private String deskDetail;
	
	@Column(name = "mobile_banner")
	private String  mobileBanner;
	
	@Column(name = "mobile_detail")
	private String  mobileDetail;

	@Column(name = "created_by")
	private String  createdBy;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	public void setReqToEntity(PostSettingReq req) {
		
		promoCode = req.getPromoCode() !=null ? req.getPromoCode(): promoCode;
		promoTitle = req.getPromoTitle() !=null ? req.getPromoTitle(): promoTitle;
		lang = req.getLang() !=null ? req.getLang(): lang;
		deskBanner = req.getDeskBanner() !=null ? req.getDeskBanner(): deskBanner;
		deskDetail = req.getDeskDetail() !=null ? req.getDeskDetail(): deskDetail;
		mobileBanner = req.getMobileBanner() !=null ? req.getMobileBanner(): mobileBanner;
		mobileDetail = req.getMobileDetail() !=null ? req.getMobileDetail(): mobileDetail;
		
	}
	
}
