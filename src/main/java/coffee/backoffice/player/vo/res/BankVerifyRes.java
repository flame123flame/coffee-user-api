package coffee.backoffice.player.vo.res;

import java.util.Date;

import lombok.Data;

@Data
public class BankVerifyRes {
	private Long id;
	private String username;
	private String bankAccount;
	private String realName;
	private String bankNameEn;
	private String bankNameTh;
	private String bankStatus;
	private String bankCode;
	private Date registerDate;
}
