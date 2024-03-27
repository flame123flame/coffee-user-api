package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.promotion.model.Promotion;
import lombok.Data;

@Data
public class WithdrawConditionRes {
	private String username;
	private String conditionType;
	private BigDecimal currentTurnover;
	private BigDecimal targetTurnover;
	private String conditionStatus;
	private String promoCode;
	private String createdBy;
	private Date createdDate;
	private String remark;
	private Promotion promotion;
	private BigDecimal amount;
}
