package coffee.provider.sbobet.vo.res;

import java.math.BigDecimal;

import coffee.provider.sbobet.vo.model.Error;
import lombok.Data;

@Data
public class DepositResponse {
	private String txnId;
	private String refno;
	private BigDecimal balance;
	private BigDecimal outstanding;
    private String serverId;
    private Error error;
}
