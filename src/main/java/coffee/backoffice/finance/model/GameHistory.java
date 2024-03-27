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
@Table(name = "game_history")
public class GameHistory implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6939469025722494247L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;
	
	@Column(name = "game_name")
	private String gameName;
	
	@Column(name = "game_code")
	private String gameCode;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "credit_in")
	private BigDecimal creditIn = BigDecimal.ZERO;
	
	@Column(name = "credit_out")
	private BigDecimal creditOut = BigDecimal.ZERO;
	
	@Column(name = "credit_result")
	private BigDecimal creditResult = BigDecimal.ZERO;

	@Column(name = "play_status")
	private String playStatus;
	
	@Column(name = "use_bonus")
	private Boolean useBonus;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "transaction_result")
	private String transactionResult;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate = new Date();
}
