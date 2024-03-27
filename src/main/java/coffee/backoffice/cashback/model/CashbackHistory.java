package coffee.backoffice.cashback.model;

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
@Table(name = "cashback_history")
public class CashbackHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 637426070901045632L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "cashback_history_code")
	private String cashbackHistoryCode;

	@Column(name = "cashback_title")
	private String cashbackTitle;

	@Column(name = "username")
	private String username;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "is_auto_cashback")
	private Boolean isAutoCashback;

	@Column(name = "total_loss")
	private BigDecimal totalLoss;

	@Column(name = "original_cashback")
	private BigDecimal originalCashback;

	@Column(name = "actual_cashback")
	private BigDecimal actualCashback;

	@Column(name = "remark")
	private String remark;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "cashback_code")
	private String cashbackCode;
}
