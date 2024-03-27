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

import coffee.backoffice.cashback.vo.req.CashbackConditionReq;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

@Entity
@Data
@Table(name = "cashback_condition")
public class CashbackCondition implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -5421258446933956360L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code = GenerateRandomString.generateUUID();

	@Column(name = "cashback_code", length = 255)
	private String cashbackCode;

	@Column(name = "more_than_amount")
	private BigDecimal moreThanAmount;

	@Column(name = "cashback_percent")
	private BigDecimal cashbackPercent;

	@Column(name = "max_cashback_amount")
	private BigDecimal maxCashbackAmount;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy = UserLoginUtil.getUsername();

	@Column(name = "updated_date")
	private Date updatedDate = new Date();

	public void setReqToEntity(CashbackConditionReq req) {
		id = req.getId() == null ? id : req.getId();
		code = req.getCode() == null ? code : req.getCode();
		cashbackCode = req.getCashbackCode() == null ? cashbackCode : req.getCashbackCode();
		cashbackPercent = req.getCashbackPercent() == null ? cashbackPercent : req.getCashbackPercent();
		moreThanAmount = req.getMoreThanAmount() == null ? moreThanAmount : req.getMoreThanAmount();
		maxCashbackAmount = req.getMaxCashbackAmount() == null ? maxCashbackAmount : req.getMaxCashbackAmount();
//		createdDate = req.getCreatedDate() == null ? createdDate : req.getCreatedDate();
//		updatedBy = req.getUpdatedBy() == null ? updatedBy : req.getUpdatedBy();
//		updatedDate = req.getUpdatedDate() == null ? updatedDate : req.getUpdatedDate();
	}
}
