package coffee.provider.sexy.vo.req;

import lombok.Data;

@Data
public class LoginRequest {
	
	
	private final String cert;
	private final String agentId;
	private final String userId;
	private String isMobileLogin;
	private String externalURL;
	private String gameForbidden;
	private String gameType;
	private String platform;
	private String language;
}
