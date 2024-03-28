package coffee.backoffice.player.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

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

	@Column(name = "description")
	private String description;

	@Column(name = "default_group")
	private Boolean defaultGroup;

	@Column(name = "status")
	private String status;

	@Column(name = "show_in_front_end")
	private Boolean showInFrontEnd;

	@Column(name = "total_deposit_amt_up")
	private BigDecimal totalDepositAmtUp;

	@Column(name = "valid_bets_up")
	private BigDecimal validBetsUp;

	@Column(name = "total_deposit_amt_rm")
	private BigDecimal totalDepositAmtRm;

	@Column(name = "valid_bets_rm")
	private BigDecimal validBetsRm;

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

	@Column(name = "group_mobile_phone")
	private String groupMobilePhone;

	@Column(name = "group_link_line")
	private String groupLinkLine;

	@Lob
	@Column(name = "group_icon_img")
	private String groupIconImg;

	@Column(name = "general_condition")
	private BigDecimal generalCondition;

	@Column(name = "level")
	private String level;

}
