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
@Table(name = "transaction_list")
public class TransactionList implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2304711395866300324L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "transaction_date")
	private Date transactionDate = new Date();
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "transaction_type")
	private String transactionType;
	
	@Column(name = "from_sender")
	private String fromSender;
	
	@Column(name = "to_recive")
	private String toRecive;
	
	@Column(name = "add_balance")
	private BigDecimal addBalance;
	
	@Column(name = "sub_balance")
	private BigDecimal subBalance;
	
	@Column(name = "tranfer_amount")
	private BigDecimal tranferAmount;
	
	@Column(name = "total_balance")
	private BigDecimal totalBalance;
	
	@Column(name = "sub_wallet")
	private String subWallet;
	
	@Column(name = "before_balance")
	private BigDecimal beforeBalance;
	
	@Column(name = "after_balance")
	private BigDecimal afterBalance;
	
	@Column(name = "transaction_amount")
	private BigDecimal transactionAmount;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;
	
}
