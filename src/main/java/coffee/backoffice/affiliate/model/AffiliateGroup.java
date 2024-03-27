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
@Table(name = "affiliate_group")
public class AffiliateGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8183821344725117597L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "affiliate_group_code")
	private String affiliateGroupCode;
	@Column(name = "group_name")
	private String groupName;
	@Column(name = "description")
	private String description;
	@Column(name = "withdraw_condition")
	private String withdrawCondition;
	@Column(name = "min_total_bets")
	private BigDecimal minTotalBets;
	@Column(name = "min_affiliate_count")
	private BigDecimal minAffiliateCount;
	@Column(name = "min_total_income")
	private BigDecimal minTotalIncome;
	@Column(name = "created_by")
	private String  createdBy;
	@Column(name = "created_date")
	private Date createdDate = new Date();
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "enable")
	private Boolean enable = true;
}
