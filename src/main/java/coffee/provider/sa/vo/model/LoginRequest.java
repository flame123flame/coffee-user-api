package coffee.provider.sa.vo.model;

import lombok.Data;

@Data
public class LoginRequest {

	private String Token;
	private String DisplayName;
	private int ErrorMsgId;
	private String ErrorMsg;
}
