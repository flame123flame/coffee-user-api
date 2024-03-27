package coffee.provider.sexy.vo.req;

import lombok.Data;

@Data
public class WithdrawRequest {
	
	
	private final String cert;
	private final String agentId;
	private final String userId;
	private final String txCode;
	private int withdrawType;
	private String transferAmount;
}
