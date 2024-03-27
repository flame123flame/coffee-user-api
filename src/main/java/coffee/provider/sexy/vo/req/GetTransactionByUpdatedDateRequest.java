package coffee.provider.sexy.vo.req;

import lombok.Data;

@Data
public class GetTransactionByUpdatedDateRequest {
	
	
	private final String cert;
	private final String agentId;
	private final String timeFrom;
	private Integer status;
	private final String platform;
	private String currency;
}
