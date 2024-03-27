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
@Table(name = "issue_setting")
public class IssueSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3893001496160717876L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "promo_code")
	private String  promoCode;

	@Column(name = "provider_name")
	private String providerName;
	
	@Column(name = "provider_code")
	private String providerCode;
}
