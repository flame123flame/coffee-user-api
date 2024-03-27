package coffee.backoffice.finance.vo.req;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class WithdrawReq {

	private String orderWithdraw;
	private String username;
	private String bankAccount;
	private String accountName;
	private String bankName;
	private BigDecimal amount;
	private String toBankAccount;
	private String toAccountName;
	private String toBankName;
	private Date withdrawDate;
	private String withdrawStatus;
	private String adminRemark;
	private String userRemark;


}
