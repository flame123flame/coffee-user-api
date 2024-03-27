package coffee.backoffice.report.vo.req;

import java.util.Date;

import lombok.Data;

@Data
public class ValidBestsWinLossReq {
	private String productCode;
	private Date firstDayDate;
	private Date lastDayDate;
}
