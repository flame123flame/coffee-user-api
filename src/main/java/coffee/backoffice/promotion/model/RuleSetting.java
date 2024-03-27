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

import coffee.backoffice.promotion.vo.req.RuleSettingReq;
import lombok.Data;

@Data
@Entity
@Table(name = "rule_setting")
public class RuleSetting implements Serializable {

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
	
	@Column(name = "bonus_type")
	private String bonusType;
	
	@Column(name = "condition_type")
	private String conditionType;
	
	@Column(name = "single_bonus_limit")
	private BigDecimal singleBonusLimit;
	
	@Column(name = "max_approval_limit")
	private BigDecimal maxApprovalLimit;
	
	@Column(name = "max_bonus_limit")
	private BigDecimal maxBonusLimit;
	
	@Column(name = "deposit_amount")
	private BigDecimal depositAmount;
	
	@Column(name = "bonus_calculation")
	private BigDecimal bonusCalculation;
	
	@Column(name = "withdraw_condition")
	private BigDecimal withdrawCondition;
	
	@Column(name = "max_withdraw")
	private BigDecimal maxWithdraw;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "remove_general")
	private String removeGeneral;
	
	@Column(name = "amount_limit_type")
	private String amountLimitType;
	
	@Column(name = "amount_limit")
	private Long amountLimit;
	
	@Column(name = "receive_bonus_wallet")
	private String receiveBonusWallet;
	
	public void setReqToEntity(RuleSettingReq req) {
		if(req != null) {
			promoCode = req.getPromoCode();
			bonusType = req.getBonusType();
			conditionType = req.getConditionType();
			singleBonusLimit = req.getSingleBonusLimit();
			maxApprovalLimit = req.getMaxApprovalLimit();
			maxBonusLimit = req.getMaxBonusLimit();
			depositAmount = req.getDepositAmt();
			bonusCalculation = req.getBonusCalculate();
			withdrawCondition = req.getMultiplierCondition();
			removeGeneral = req.getRemoveGeneral();
			amountLimitType = req.getAmountLimitType();
			amountLimit = req.getAmountLimit();
			receiveBonusWallet = req.getReceiveBonusWallet();
			maxWithdraw = req.getMaxWithdraw();
		}
		
	}
	
}
