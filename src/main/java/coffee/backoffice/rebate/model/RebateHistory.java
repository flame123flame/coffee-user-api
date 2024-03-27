package coffee.backoffice.rebate.model;

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
@Data
@Table(name = "rebate_history")
public class RebateHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3954959651761479764L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "rebate_history_code")
	private String rebateHistoryCode;

	@Column(name = "rebate_title")
	private String rebateTitle;

	@Column(name = "username")
	private String username;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "is_auto_rebate")
	private Boolean isAutoRebate;

	@Column(name = "valid_bets")
	private BigDecimal validBets;

	@Column(name = "original_rebate")
	private BigDecimal originalRebate;

	@Column(name = "actual_rebate")
	private BigDecimal actualRebate;

	@Column(name = "remark")
	private String remark;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "rebate_code")
	private String RebateCode;
}
