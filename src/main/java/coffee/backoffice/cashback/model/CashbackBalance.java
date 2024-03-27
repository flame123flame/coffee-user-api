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
@Table(name = "cashback_balance")
public class CashbackBalance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5464949302532634272L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "username")
	private String username;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;
}
