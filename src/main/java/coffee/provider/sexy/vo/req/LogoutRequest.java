package coffee.provider.sexy.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class LogoutRequest {
	
	
	private final String cert;
	private final String agentId;
	private final String userIds;
}
