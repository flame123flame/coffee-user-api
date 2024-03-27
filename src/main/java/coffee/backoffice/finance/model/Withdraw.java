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

@Data
@Entity
@Table(name = "withdraw")
public class Withdraw implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7048840372241833354L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "order_withdraw")
	private String orderWithdraw;

	@Column(name = "username")
	private String username;
	
	@Column(name = "bank_account")
	private String bankAccount;
	
	@Column(name = "account_name")
	private String accountName;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;
	
	@Column(name = "to_bank_account")
	private String toBankAccount;
	
	@Column(name = "to_account_name")
	private String toAccountName;
	
	@Column(name = "to_bank_name")
	private String toBankName;
	
	@Column(name = "withdraw_date")
	private Date withdrawDate;
	
	@Column(name = "withdraw_status")
	private String withdrawStatus;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "admin_remark")
	private String adminRemark;

	@Column(name = "user_remark")
	private String userRemark;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "after_balance")
	private BigDecimal afterBalance;
	@Column(name = "before_balance")
	private BigDecimal beforeBalance;
}
