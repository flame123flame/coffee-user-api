package coffee.backoffice.finance.vo.req;

import java.util.Date;

import lombok.Data;

@Data
public class AllTransactionReq {
	
	private Date startDate;
	private Date endDate;
	
//	private String startDate;
//	private String endDate;

}
