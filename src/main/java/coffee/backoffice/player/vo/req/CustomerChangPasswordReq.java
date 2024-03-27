package coffee.backoffice.player.vo.req;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerChangPasswordReq {
	private String username;
	private String firstName;
	private String lastName;
	private String nickName;
	private Date birthday;
	private String email;
	private String oldPassword;
	private String newPassword;
}
