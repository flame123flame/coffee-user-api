package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OverallGameWinLossRes {
	private List<DataListRes> dataList;
	private DataSummaryRes summary;

	@Data
	public static class DataListRes {
		private String providerCode;
		private String providerName;
		private String productType;
		private String productTypeName;
		private BigDecimal totalBet;
		private Integer totalPlayer;
		private Integer totalTxn;
		private BigDecimal totalWinLoss;
		private BigDecimal validBet;
	}

	@Data
	public static class DataSummaryRes {
			private BigDecimal totalBet;
			private Integer totalPlayer;
			private Integer totalTxn;
			private BigDecimal totalWinLoss;
			private BigDecimal validBet;
	}
}
