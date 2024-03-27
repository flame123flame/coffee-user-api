package coffee.backoffice.promotion.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import coffee.backoffice.promotion.vo.model.BonusLevelDetail;
import coffee.backoffice.promotion.vo.req.RuleSettingReq;
import lombok.Data;

@Data
@Entity
@Table(name = "bonus_level_setting")
public class BonusLevelSetting implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6162915741284403133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "promo_code")
	private String promoCode;
	
	@Column(name = "bonus_level")
	private Integer bonusLevel;
	
	@Column(name = "deposit_amount")
	private BigDecimal depositAmount;
	
	@Column(name = "bonus_calculation")
	private BigDecimal bonusCalculation;
	
	@Column(name = "withdraw_condition")
	private BigDecimal withdrawCondition;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	public void setReqToEntity(BonusLevelDetail req) {
		if(req != null) {
			promoCode = req.getPromoCode();
			bonusLevel = req.getLevel();
			depositAmount = req.getDepositAmount();
			bonusCalculation = req.getBonusCalculation();
			withdrawCondition = req.getWithdrawCondition();
		}
		
	}
	
}
