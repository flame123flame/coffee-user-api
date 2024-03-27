package coffee.backoffice.frontend.vo.res;

import java.util.List;

import lombok.Data;

@Data
public class MessageRes {
	private Long id;
	private String messageCode;
	private List<String> usernames;
	private List<String> groupCodes;
	private String subject;
	private String webMessage;
	private String createdBy;
	private String createdDate;
	private String promoCode;
	private String messageType;
}
