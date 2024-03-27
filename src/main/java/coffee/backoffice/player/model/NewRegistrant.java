package coffee.backoffice.player.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "new_registrant")
@Getter
@Setter
public class NewRegistrant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7540746515215116975L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "new_registrant_id")
	private int newRegistrantId;
	@Column(name = "agent_old")
	private String agentOld;
	@Column(name = "agent_team")
	private String agentTeam;
	@Column(name = "affiliate")
	private String affiliate;
	@Column(name = "player_id")
	private String playerId;
	@Column(name = "real_name")
	private String realName;
	@Column(name = "tag_name")
	private String tagName;
	@Column(name = "deposit_amt")
	private BigDecimal depositAmt;
	@Column(name = "current_balance")
	private BigDecimal currentBalance;
	@Column(name = "registered_date")
	private Date registeredDate;
	@Column(name = "registered_ip")
	private String registeredIP;
	@Column(name = "create_by")
	private String createBy;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_by")
	private String updateBy;
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name = "is_deleted")
	private String isDeleted = "N";

}
