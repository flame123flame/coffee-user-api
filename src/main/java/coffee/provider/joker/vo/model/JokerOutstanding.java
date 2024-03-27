package coffee.provider.joker.vo.model;

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
@Table(name = "joker_outstanding")
@Data
public class JokerOutstanding implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2949544834895965259L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "check_outstanding")
	private Boolean checkOutstanding;
	
	@Column(name = "outstanding_amount")
	private BigDecimal outstandingAmount = BigDecimal.ZERO;

}
