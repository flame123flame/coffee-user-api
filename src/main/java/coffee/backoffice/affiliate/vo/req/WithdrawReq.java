package coffee.backoffice.affiliate.vo.req;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WithdrawReq {
	private String username;
	private BigDecimal amount;
}
