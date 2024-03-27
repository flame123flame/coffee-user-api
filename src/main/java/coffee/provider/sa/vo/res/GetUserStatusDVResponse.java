package coffee.provider.sa.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GetUserStatusDVResponse {

	private Boolean isSuccess;
	private String username;
	private BigDecimal balance;
	private Boolean online;
	private Boolean betted;
	private BigDecimal bettedAmount;
	private BigDecimal maxBalance;
	private BigDecimal maxWinning;
	private BigDecimal withholdAmount;
	private byte errorMsgId;
	private String errorMsg;
}
