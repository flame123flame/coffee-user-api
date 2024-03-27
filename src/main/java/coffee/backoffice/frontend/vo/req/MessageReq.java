package coffee.backoffice.frontend.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class MessageReq {
	private Long id;
	private String messageCode;
	private List<String> usernames;
	private List<String> groupCodes;
	private String subject;
	private String webMessage;
	private String promoCode;
	private String username;
	private String messageType;
}
