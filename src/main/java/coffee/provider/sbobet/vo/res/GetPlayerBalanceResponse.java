package coffee.provider.sbobet.vo.res;

import java.math.BigDecimal;

import coffee.provider.sbobet.vo.model.Error;
import lombok.Data;

@Data
public class GetPlayerBalanceResponse {
	private String username;
	private String currency;
	private BigDecimal balance;
	private BigDecimal outstanding;
    private String serverId;
    private Error error;
}
