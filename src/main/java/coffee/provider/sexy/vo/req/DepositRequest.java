package coffee.provider.sexy.vo.req;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepositRequest {
	
	
	private final String cert;
	private final String agentId;
	private final String userId;
	private final BigDecimal transferAmount;
	private final String txCode;
}