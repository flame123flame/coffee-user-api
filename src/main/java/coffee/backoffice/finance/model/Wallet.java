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
@Table(name = "wallet")
public class Wallet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8904006977673383565L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;
	
	@Column(name = "balance")
	private BigDecimal balance = BigDecimal.ZERO;
	
	@Column(name = "bonus")
	private BigDecimal bonus = BigDecimal.ZERO;
	
	@Column(name = "pending_withdrawal")
	private BigDecimal pendingWithdrawal = BigDecimal.ZERO;

	@Column(name = "wallet_id")
	private String walletId;
	
	@Column(name = "wallet_name")
	private String walletName;
	
	@Column(name = "wallet_type")
	private String walletType;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;
}
