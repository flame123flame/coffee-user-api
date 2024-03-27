package coffee.provider.sa.vo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DebitAllBalance {

	private String Username;
	private BigDecimal DebitAmount;
	private String OrderId;
	private int ErrorMsgId;
	private String ErrorMsg;
}
