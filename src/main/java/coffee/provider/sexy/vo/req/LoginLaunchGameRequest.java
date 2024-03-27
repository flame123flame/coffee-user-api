package coffee.provider.sexy.vo.req;

import lombok.Data;

@Data
public class LoginLaunchGameRequest {
	
	
	private final String cert;
	private final String agentId;
	private final String userId;
	private String gameCode;
	private final String gameType;
	private final String platform;
	private String isMobileLogin;
	private String externalURL;
	private String language;
	private String hall;
}
