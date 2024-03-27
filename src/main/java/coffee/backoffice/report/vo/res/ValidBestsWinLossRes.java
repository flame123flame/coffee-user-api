package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ValidBestsWinLossRes {
	private String name;
	private BigDecimal totalwinloss;
	private BigDecimal validbet;
}
