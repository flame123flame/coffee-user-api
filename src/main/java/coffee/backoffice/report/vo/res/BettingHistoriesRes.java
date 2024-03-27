package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import framework.model.DataTableResponse;
import lombok.Data;

@Data
public class BettingHistoriesRes {

	private DataTableResponse<DataListRes> dataList;
	private DataSummaryRes summary;

	@Data
	public static class DataListRes {
		private Date betTime;
		private Date ResultTime;
		private String ticketId;
		private String providercode;
		private String providername;
		private String producttypecode;
		private String producttypename;
		private String groupcode;
		private String groupname;
		private String gamecode;
		private String gamename;
		private String playerId;
		private BigDecimal betAmount;
		private BigDecimal validBet;
		private BigDecimal winLoss;
	}

	@Data
	public static class DataSummaryRes {
		private BigDecimal subtotalBetAmount = BigDecimal.ZERO;
		private BigDecimal subtotalValidBet = BigDecimal.ZERO;
		private BigDecimal subtotalWinLoss = BigDecimal.ZERO; 
		private BigDecimal totalBetAmount = BigDecimal.ZERO;
		private BigDecimal totalValidBet = BigDecimal.ZERO;
		private BigDecimal totalWinLoss = BigDecimal.ZERO;
	}

}
