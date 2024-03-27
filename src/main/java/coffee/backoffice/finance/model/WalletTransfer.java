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
@Table(name = "wallet_transfer")
public class WalletTransfer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5490638252581567387L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "order_id")
	private String orderId;

	@Column(name = "username")
	private String username;
	
	@Column(name = "transfer_amount")
	private BigDecimal transferAmount;
	
	@Column(name = "from_wallet")
	private String fromWallet;
	
	@Column(name = "to_wallet")
	private String toWallet;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Column(name = "status")
	private String status;

	@Column(name = "remark")
	private String remark;

}
