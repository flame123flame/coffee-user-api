package coffee.backoffice.player.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "group_level")
@Data
public class GroupLevel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4122436936283495005L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "count_player")
	private int countPlayer;

	@Column(name = "status")
	private String status;

	@Column(name = "description")
	private String description;

	@Column(name = "default_group")
	private Boolean defaultGroup;

	@Column(name = "min_deposit_amt")
	private BigDecimal minDepositAmt;

	@Column(name = "max_deposit_amt")
	private BigDecimal maxDepositAmt;

	@Column(name = "min_withdrawal_amt")
	private BigDecimal minWithdrawalAmt;

	@Column(name = "max_withdrawal_amt")
	private BigDecimal maxWithdrawalAmt;

	@Column(name = "daily_max_wd_amount")
	private BigDecimal dailyMaxWDAmount;

	@Column(name = "daily_max_wd_count")
	private int dailyMaxWDCount;

	@Column(name = "update_on")
	private Date updateOn;

	@Column(name = "update_by")
	private String updateBy;

	private String groupMobilePhone;
	private String groupLinkLine;

	@Column(name = "group_icon_img")
	private String groupIconImg;

	@Column(name = "general_condition")
	private BigDecimal generalCondition;

	@Column(name = "level")
	private String level;
	
}
