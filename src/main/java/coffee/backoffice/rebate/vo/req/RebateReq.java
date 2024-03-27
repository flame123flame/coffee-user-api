package coffee.backoffice.rebate.vo.req;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RebateReq {
	private Long id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Long periodStatus;
	private Long rebateType;
	private Boolean isAutoRebate;
	private Boolean status;
	private List<String> vipGroupCode;
	private String productTypeCode;
	private BigDecimal maxGroupRebate;
	private Date createdDate;
	private Date updatedDate;
	private String updatedBy;
	private String code;
	private List<String> tagCode;
	private String gamesCodeExclude;
	private List<RebateConditionReq> rebateConditionList;
	private BigDecimal rebateConditionMultiplier;

}
