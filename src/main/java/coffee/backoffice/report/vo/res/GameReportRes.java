package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import framework.model.DataTableResponse;
import lombok.Data;

@Data
public class GameReportRes {

	private DataTableResponse<DataLisrRes> dataList;
	private DataSummaryRes summary;

	@Data
	public static class DataLisrRes {
		private String gamegroupcode;
		private String gamegroupname;
		private String gamecode;
		private String gamename;
		private String gameprovidercode;
		private String gameprovidername;
		private String producttypecode;
		private String producttypename;
		private BigDecimal payout;
		private Integer playercount;
		private String summarydate;
		private BigDecimal totalbet;
		private BigDecimal totalloss;
		private BigDecimal totalprizewon;
		private Integer totaltxn;
		private BigDecimal totalwin;
		private BigDecimal totalwinloss;
		private BigDecimal validbet;
	}

	@Data
	public static class DataSummaryRes {
		private String gamegroupcode;
		private String gamegroupname;
		private String gamecode;
		private String gamename;
		private String gameprovidercode;
		private String gameprovidername;
		private String producttypecode;
		private String producttypename;
		private BigDecimal payout = BigDecimal.ZERO;
		private int playercount;
		private String summarydate;
		private BigDecimal totalbet = BigDecimal.ZERO;
		private BigDecimal totalloss = BigDecimal.ZERO;
		private BigDecimal totalprizewon = BigDecimal.ZERO;
		private int totaltxn;
		private BigDecimal totalwin = BigDecimal.ZERO;
		private BigDecimal totalwinloss = BigDecimal.ZERO;
		private BigDecimal validbet = BigDecimal.ZERO;
	}
}
