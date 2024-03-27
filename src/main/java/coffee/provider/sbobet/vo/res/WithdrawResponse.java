package coffee.provider.sbobet.vo.res;

import java.math.BigDecimal;

import coffee.provider.sbobet.vo.model.Error;
import lombok.Data;

@Data
public class WithdrawResponse {
	private BigDecimal amount;
	private String txnId;
	private String refno;
	private BigDecimal balance;
	private BigDecimal outstanding;
    private String serverId;
    private Error error;
}
