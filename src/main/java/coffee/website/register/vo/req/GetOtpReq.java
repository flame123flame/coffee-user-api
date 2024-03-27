package coffee.website.register.vo.req;

import lombok.Data;

@Data
public class GetOtpReq {
	private String phoneNumber;
	private String username;
	private String otp;
}
