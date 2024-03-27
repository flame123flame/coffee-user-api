package coffee.backoffice.finance.vo.req;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class WithdrawConditionReq {

	private Long id;
	private String username;
	private String condtionType;
	private String calculateType;
	private BigDecimal amount;
	private BigDecimal operatorValue;
	private BigDecimal currentTurnover;
	private BigDecimal targetTurnover;
	private String conditionStatus;

}
