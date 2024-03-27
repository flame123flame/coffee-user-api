package coffee.backoffice.report.vo.req;

import java.util.Date;

import lombok.Data;

@Data
public class OverallGameWinLossReq {
	private Date firstDayDate;
	private Date lastDayDate;
	private String type;
}
