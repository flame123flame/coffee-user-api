package coffee.backoffice.player.vo.req;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class GroupLevelReq {
	private String groupCode;
	private String groupName;
	private int countPlayer;
	private String status;
	private String description;
	private Boolean defaultGroup;
	private BigDecimal totalDepositAmtUp;
	private BigDecimal validBetsUp;
	private BigDecimal totalDepositAmtRm;	
	private BigDecimal validBetsRm;	
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
