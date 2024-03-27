package coffee.backoffice.finance.model;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import framework.utils.StringPrefixedSequenceIdGenerator;

@Data
@Entity
@Table(name = "withdraw_condition")
public class WithdrawCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5633169895924820840L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;
	
	@Column(name = "condition_type")
	private String conditionType;
	
	@Column(name = "calculate_type")
	private String calculateType;
	
	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;
	
	@Column(name = "operator_value")
	private BigDecimal operatorValue = BigDecimal.ZERO;
	
	@Column(name = "current_turnover")
	private BigDecimal currentTurnover = BigDecimal.ZERO;
	
	@Column(name = "target_turnover")
	private BigDecimal targetTurnover = BigDecimal.ZERO;
	
	@Column(name = "condition_status")
	private String conditionStatus;
	
	@Column(name = "promo_code")
	private String promoCode;
	
	@Column(name = "remark")
	private String remark;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;
}
