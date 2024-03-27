package coffee.provider.sexy.vo.req;

import lombok.Data;

@Data
public class CheckTransferOperationRequest {
	
	private final String cert;
	private final String agentId;
	private final String txCode;
}
