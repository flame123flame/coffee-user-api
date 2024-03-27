package coffee.provider.sa.vo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreditBalance {

	private String Username;
	private BigDecimal Balance;
	private BigDecimal CreditAmount;
	private String OrderId;
	private int ErrorMsgId;
	private String ErrorMsg;
}
