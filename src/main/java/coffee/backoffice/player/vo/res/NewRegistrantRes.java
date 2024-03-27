package coffee.backoffice.player.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.player.model.Customer;
import framework.utils.ConvertDateUtils;
import lombok.Data;

@Data
public class NewRegistrantRes {
	
	private Number countToDay;
	private Number countToDayDeposit;
	
	private Number countToWeek;
	private Number countToWeekDeposit;
	private Integer countWeek;
	
	private Number countToLastweek;
	private Number countToLastweekDeposit;
	
	private Long id;
	private String username;
	private String mobilePhone;
	private String realName;
	private String nickname;
	private String email;
	private Date birthday;
	private String bankCode;
	private String bankNameEn;
	private String bankNameTh;
	private String bankAccount;
	private String bankImg;
	private String groupCode;
	private String groupName;
	private String level;
	private String groupImg;
	private String tagCode;
	private String tagName;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private Boolean enable;
	private String lastLoginDate;
	private Boolean loginStatus;
	private BigDecimal balance;
	private BigDecimal bonus;
	private BigDecimal pendingWithdrawal;
	private String affiliateCode;
	private String affiliateCodeUp;
	private String registerDate;
	private GroupLevelRes groupLevelRes;
	private String GroupMobilePhone;
	private String GroupLinkLine;
	private BigDecimal totalDeposit;
	
}
