package coffee.backoffice.report.vo.req;

import java.util.Date;

import lombok.Data;
@Data
public class GameReportReq {
	private Date firstDayDate;
	private Date lastDayDate;
	private String provider;
	private String product;
	private String group;
	private String game;
}
