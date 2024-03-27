package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepositWithdrawRes {
	private BigDecimal deposit;
	private BigDecimal withdraw;
	private String summaryDate;
}
