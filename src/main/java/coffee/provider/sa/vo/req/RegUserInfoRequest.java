package coffee.provider.sa.vo.req;

import java.util.Date;

import lombok.Data;

@Data
public class RegUserInfoRequest {

	private String method;
	private String key;
	private Date time;
	private String username;
	private String currencyType;
}
