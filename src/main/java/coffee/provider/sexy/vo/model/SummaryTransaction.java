package coffee.provider.sexy.vo.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SummaryTransaction{

	private BigDecimal betAmount;
	private BigDecimal realBetAmount;
	private BigDecimal realWinAmount;
	private BigDecimal winAmount;
	private BigDecimal jackpotBetAmount;
	private BigDecimal jackpotWinAmount;
	private String currency;
	private BigDecimal turnover;
}