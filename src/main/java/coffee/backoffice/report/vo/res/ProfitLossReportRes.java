package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProfitLossReportRes {
	private List<DataListRes> dataList;
	private DataSummaryRes summary;

	@Data
	public static class DataListRes {
		private BigDecimal adjustment;
		private BigDecimal bonus;
		private BigDecimal companywinloss;
		private BigDecimal cashback;
		private BigDecimal deposit;
		private BigDecimal depositbonus;
		private Integer depositcount;
		private Integer firstdepositcount;
		private BigDecimal rebate;
		private Integer registercount;
		private String summarydate;
		private BigDecimal validbet;
		private BigDecimal withdraw;
		private Integer withdrawcount;
	}

	@Data
	public static class DataSummaryRes {
		private BigDecimal adjustment = BigDecimal.ZERO;
		private BigDecimal bonus = BigDecimal.ZERO;
		private BigDecimal companywinloss = BigDecimal.ZERO;
		private BigDecimal cashback = BigDecimal.ZERO;
		private BigDecimal deposit = BigDecimal.ZERO;
		private BigDecimal depositbonus = BigDecimal.ZERO;
		private Integer depositcount = 0;
		private Integer firstdepositcount = 0;
		private BigDecimal rebate = BigDecimal.ZERO;
		private Integer registercount = 0;
		private String summarydate;
		private BigDecimal validbet = BigDecimal.ZERO;
		private BigDecimal withdraw = BigDecimal.ZERO;
		private Integer withdrawcount = 0;
	}
}
