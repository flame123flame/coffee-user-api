package coffee.provider.sexy.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CheckTransferOperationResponse {

	private String status;
	private String txCode;
	private String txStatus;
	private BigDecimal transferAmount;
	private String transferType;
	private BigDecimal balance;
}
