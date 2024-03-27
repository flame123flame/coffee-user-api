package coffee.provider.sexy.vo.req;

import lombok.Data;

@Data
public class CreateMemberRequest {
	
	
	private final String cert;
	private final String agentId;
	private final String userId;
	private final String currency;
	private final String betLimit;
	private String language;
}
