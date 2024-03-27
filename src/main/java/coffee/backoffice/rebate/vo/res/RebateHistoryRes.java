package coffee.backoffice.rebate.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class RebateHistoryRes {
	private Long id;
	private String rebateHistoryCode;
	private String rebateCode; 
	private String rebateTitle;
	private String username;
	private String groupCode;
	private String groupName;
	private Boolean isAutoRebate;
	private BigDecimal validBets;
	private BigDecimal originalRebate;
	private BigDecimal actualRebate;
	private String remark;
	private Boolean status;
	private String createdBy;
	private String createdDate;
}
