package coffee.backoffice.frontend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "deposit_setting")
@Data
public class DepositSetting implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5347443588172627513L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "money_transfer")
	private String moneyTransfer;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_date")
	private Date updatedDate;
}
