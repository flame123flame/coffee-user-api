package coffee.backoffice.player.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.player.model.Customer;
import framework.utils.ConvertDateUtils;
import lombok.Data;

@Data
public class CustomerRes {
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
	private BigDecimal withdraw;
	private BigDecimal pendingWithdrawal;
	private String affiliateCode;
	private String affiliateCodeUp;
	private String registerDate;
	private GroupLevelRes groupLevelRes;
	private String GroupMobilePhone;
	private String GroupLinkLine;
	private BigDecimal totalDeposit;
	public void setEntityToRes(Customer form) {
        id = form.getId();
        username = form.getUsername();
        mobilePhone = form.getMobilePhone();
        realName = form.getRealName();
        bankCode = form.getBankCode();
        bankAccount = form.getBankAccount();
        groupCode = form.getGroupCode();
        groupName = form.getRealName();
        tagCode = form.getTagCode();
        tagName = form.getRealName();
        createdBy = form.getCreatedBy();
        createdDate = ConvertDateUtils.formatDateToStringEn(form.getCreatedDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS);
        updatedBy = form.getUpdatedBy();
        updatedDate = ConvertDateUtils.formatDateToStringEn(form.getUpdatedDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS);
        enable = form.getEnable();
        lastLoginDate = ConvertDateUtils.formatDateToStringEn(form.getLastLoginDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS);
        loginStatus = form.getLoginStatus();
           }
}
