package coffee.backoffice.report.vo.req;

import java.util.Date;

import lombok.Data;

@Data
public class ProfitLossReportReq {
	private String firstDayDate;
	private String lastDayDate;
}
