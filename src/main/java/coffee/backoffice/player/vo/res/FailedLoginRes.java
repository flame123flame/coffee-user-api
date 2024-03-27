package coffee.backoffice.player.vo.res;

import java.util.Date;

import lombok.Data;

@Data
public class FailedLoginRes {
	private String failedLoginCode;
	private String username;
	private String realName;
	private String remark;
	private Date lastFailedLogin;
	private String country;
	private int countFailedLogin;
	private String ipAddress;
	private String platform;
	private String browserName;
	private String browserVersion;
}
