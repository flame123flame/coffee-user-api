package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OverallCompanyProfitLossRes {
	private BigDecimal adjustment;
	private BigDecimal bonus;
	private BigDecimal companyTotal;
//	private BigDecimal currency;
	private BigDecimal deposit;
	private Integer depositCount;
	private Integer depositPeople;
	private Integer firstDeposit;
//	private BigDecimal jpbet;
//	private BigDecimal jpwin;
	private BigDecimal rebate;
	private BigDecimal cashback;
	private Integer registerCount;
//	private BigDecimal totaltip;
	private BigDecimal totalWinLoss;
	private BigDecimal validBet;
	private BigDecimal withdraw;
}	
