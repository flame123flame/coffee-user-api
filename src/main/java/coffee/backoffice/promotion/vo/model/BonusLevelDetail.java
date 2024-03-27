package coffee.backoffice.promotion.vo.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BonusLevelDetail {

	private String promoCode;
	private Integer level;
	private BigDecimal depositAmount;
	private BigDecimal bonusCalculation;
	private BigDecimal withdrawCondition;
	
}
