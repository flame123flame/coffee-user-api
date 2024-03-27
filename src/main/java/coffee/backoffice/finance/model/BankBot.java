package coffee.backoffice.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "Tx")
public class BankBot implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4134188195724728452L;

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "TxId")
	private BigInteger txId;

	@Column(name = "TxDateTime")
	private Date txDateTime;
	
	@Column(name = "TxBankId")
	private String txBankId;
	
	@Column(name = "TxAccountNo")
	private String txAccountNo;
	
	@Column(name = "TxType")
	private String txType;
	
	@Column(name = "TxToBankId")
	private String txToBankId;
	
	@Column(name = "TxToAccountNo")
	private String txToAccountNo;
	
	@Column(name = "TxFromBankId")
	private String txFromBankId;
	
	@Column(name = "TxFromAccountNo")
	private String txFromAccountNo;
	
	@Column(name = "TxAmount")
	private BigDecimal txAmount;
	
	@Column(name = "TxEndingBalance")
	private BigDecimal txEndingBalance;
	
	@Column(name = "BankRef")
	private String bankRef;
	
	@Column(name = "Remark")
	private String remark;
	

}
