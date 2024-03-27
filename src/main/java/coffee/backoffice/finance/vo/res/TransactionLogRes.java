package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TransactionLogRes {
	private Long id;
	private Date transactionDate;
	private String transactionType;
	private String username;
	private BigDecimal transactionAmount;
	private BigDecimal beforeBalance;
	private BigDecimal afterBalance;
	private BigDecimal subWallet;
	private String remark;
	private String createdBy;
}
