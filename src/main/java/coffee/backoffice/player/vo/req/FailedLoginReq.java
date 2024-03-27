package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class FailedLoginReq {
	
	private String username;
	private String firstname;
	private String lastname;
	private String remark;
	private String country;
	private String ipAddress;
	private String platform;
	private String browserName;
	private String browserVersion;
}
