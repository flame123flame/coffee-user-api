package coffee.backoffice.player.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class GroupLevelRes {
	
	private Long id;
	private String groupCode;
	private String groupName;
	private int countPlayer;
	private String status;
	private String description;
	private Boolean defaultGroup;
	private BigDecimal minDepositAmt;	
	private BigDecimal maxDepositAmt;	
	private BigDecimal minWithdrawalAmt;
	private BigDecimal maxWithdrawalAmt;
	private BigDecimal dailyMaxWDAmount;	
	private int dailyMaxWDCount;
	private Date updateOn;
	private String updateBy;
	private String groupMobilePhone;
	private String groupLinkLine;
	private String groupIconImg;
	private BigDecimal generalCondition;
	private String level;
}
