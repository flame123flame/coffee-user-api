package coffee.provider.sbobet.vo.req;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SboBetRequest {

	private String username;
	private BigDecimal balance;
	private String orderId;
}
