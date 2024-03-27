package coffee.backoffice.promotion.vo.req;

import java.math.BigDecimal;
import java.util.List;

import coffee.backoffice.promotion.vo.model.BonusLevelDetail;
import coffee.backoffice.promotion.vo.model.WalletDetail;
import lombok.Data;

@Data
public class RuleSettingReq  {

	private String promoCode;
	private String bonusType;
	private String conditionType;
	private BigDecimal singleBonusLimit;
	private BigDecimal maxApprovalLimit;
	private BigDecimal maxBonusLimit;
	private BigDecimal depositAmt;
	private BigDecimal bonusCalculate;
	private BigDecimal multiplierCondition;
	private BigDecimal maxWithdraw;
	private String removeGeneral;
	private List<WalletDetail> wallet;
	private List<BonusLevelDetail> level;
	private String amountLimitType;
	private Long amountLimit;
	private String receiveBonusWallet;

}
