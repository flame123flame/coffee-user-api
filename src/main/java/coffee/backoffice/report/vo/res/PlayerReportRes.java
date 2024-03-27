package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;

import framework.model.DataTableResponse;
import lombok.Data;

@Data
public class PlayerReportRes {
	private DataTableResponse<DataLisrRes> dataList;
	private DataSummaryRes summary;

	@Data
	public static class DataLisrRes {
		private String username;
		private String realName;
		private String groupCode;
		private String groupName;
		private String tagCode;
		private String tagName;
		private int depositCount;
		private BigDecimal depositAmt;
		private int withdrawCount;
		private BigDecimal withdrawAmt;
		private BigDecimal totalBets;
		private BigDecimal validBets;
		private BigDecimal winLoss;
		private BigDecimal rebate;
		private BigDecimal cashback;
	}

	@Data
	public static class DataSummaryRes {
		private int subTotalDepositCount;
		private BigDecimal subTotalDepositAmt = BigDecimal.ZERO;
		private int subTotalWithdrawCount;
		private BigDecimal subTotalWithdrawAmt= BigDecimal.ZERO;
		private BigDecimal subTotalBets= BigDecimal.ZERO;
		private BigDecimal subTotalValidBets= BigDecimal.ZERO;
		private BigDecimal subTotalWinLoss= BigDecimal.ZERO;
		private BigDecimal subTotalRebate= BigDecimal.ZERO;
		private BigDecimal subTotalCashback= BigDecimal.ZERO;
		private int totalDepositCount;
		private BigDecimal totalDepositAmt= BigDecimal.ZERO;
		private int totalWithdrawCount;
		private BigDecimal totalWithdrawAmt= BigDecimal.ZERO;
		private BigDecimal totalBets= BigDecimal.ZERO;
		private BigDecimal totalValidBets= BigDecimal.ZERO;
		private BigDecimal totalWinLoss= BigDecimal.ZERO;
		private BigDecimal totalRebate= BigDecimal.ZERO;
		private BigDecimal totalCashback= BigDecimal.ZERO;
	}

}
