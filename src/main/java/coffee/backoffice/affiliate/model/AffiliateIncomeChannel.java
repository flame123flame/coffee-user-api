package coffee.backoffice.affiliate.model;

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
@Table(name = "affiliate_income_channel")
public class AffiliateIncomeChannel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1291733792880838573L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private String affiliateChannelCode;
	private String username;
	private BigDecimal totalBetOne;
	private BigDecimal totalBetTwo;
	private BigDecimal totalIncome;
	private String  createdBy;
	private Date createdDate = new Date();;
	private String updatedBy;
	private Date updatedDate;
}