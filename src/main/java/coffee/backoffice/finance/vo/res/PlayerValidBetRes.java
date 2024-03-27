package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PlayerValidBetRes {
	private String gameProvider;
	private BigDecimal validBet;
}
