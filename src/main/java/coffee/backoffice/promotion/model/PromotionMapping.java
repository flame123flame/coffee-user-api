package coffee.backoffice.promotion.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.vo.req.PromotionReq;
import lombok.Data;

@Data
@Entity
@Table(name = "promotion_mapping")
public class PromotionMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4529364164439483046L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "promo_code")
	private String  promoCode;
	
//	@Column(name = "curr_turnover")
//	private BigDecimal currTurnover;
//	
//	@Column(name = "max_turnover")
//	private BigDecimal maxTurnover;
	
	@Column(name = "status")
	private String  status;
	
	@Column(name = "date_active")
	private Date  dateActive;
	
	@Column(name = "request_id")
	private String  requestId;
	
	@Column(name = "updated_by")
	private String  updatedBy;
	
	@Column(name = "updated_date")
	private Date  updatedDate;
	
	
}
