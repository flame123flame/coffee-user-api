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
@Table(name = "deposit")
public class Deposit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5761422960509665942L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "deposit_order")
	private String depositOrder;
	
	@Column(name = "username")
	private String username;

	@Column(name = "company_account_code")
	private String companyAccountCode;
	
	@Column(name = "deposit_type")
	private String depositType;
	
	@Column(name = "slip")
	private String slip;
	
	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;

	@Column(name = "status")
	private String status;

	@Column(name = "auditor")
	private String auditor;

	@Column(name = "auditor_date")
	private Date auditorDate;

	@Column(name = "deposit_remark")
	private String depositRemark;

	@Column(name = "deposit_date")
	private Date depositDate;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "system_type")
	private String systemType;

	@Column(name = "after_balance")
	private BigDecimal afterBalance;
	
	@Column(name = "before_balance")
	private BigDecimal beforeBalance;

}
