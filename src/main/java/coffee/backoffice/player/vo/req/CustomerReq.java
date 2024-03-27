package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class CustomerReq {
	private Long id;
	private String username;
	private String password;
	private String mobilePhone;
	private String realName;
	private String bankCode;
	private String bankAccount;
	private String groupCode;
	private String tagCode;
	private Boolean enable;
	private String affiliateId;
	private String mark;
}
