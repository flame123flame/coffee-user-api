package coffee.backoffice.finance.vo.req;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletReq {

	private String username;
	private String providerCode;
	private BigDecimal bet;
	private BigDecimal balance;
}
