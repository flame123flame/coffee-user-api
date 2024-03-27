package coffee.provider.sa.vo.res;

import lombok.Data;

@Data
public class VerifyUsernameResponse {

	private String username;
	private Boolean isExist;
	private byte errorMsgId;
	private String errorMsg;
}
