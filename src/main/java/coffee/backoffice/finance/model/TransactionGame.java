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
@Table(name = "transaction_game")
public class TransactionGame implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2025424109963032596L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "game_provider")
	private String gameProvider;
	
	@Column(name = "game_code")
	private String gameCode;
	
	@Column(name = "bet")
	private BigDecimal bet;
	
	@Column(name = "valid_bet")
	private BigDecimal validBet = BigDecimal.ZERO;
	
	@Column(name = "bet_result")
	private BigDecimal betResult;
	
	@Column(name = "game_session_id")
	private String gameSessionId;
	
	@Column(name = "balance")
	private BigDecimal balance;
	
	@Column(name = "win_loss")
	private BigDecimal winLoss = BigDecimal.ZERO;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "game_result")
	private String gameResult;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "round")
	private Long round;

	@Column(name = "installment")
	private String installment;
	
	
}
