package coffee.backoffice.finance.vo.req;

import lombok.Data;

@Data
public class WithdrawConditionStausReq {
	private String username;
	private String conditionStatus;
}
