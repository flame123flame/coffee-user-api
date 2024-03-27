package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class CustomerResetPasswordReq {

	private String phoneNumber;
	private String newPassword;
}
