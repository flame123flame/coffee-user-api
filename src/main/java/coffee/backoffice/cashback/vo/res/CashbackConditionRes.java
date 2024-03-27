package coffee.backoffice.cashback.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.cashback.model.CashbackCondition;
import coffee.backoffice.casino.vo.res.GameProviderRes;
import lombok.Data;

@Data
public class CashbackConditionRes {
	private Long id;
	private String code;
	private String cashbackCode;
	private BigDecimal moreThanAmount;
	private BigDecimal cashbackPercent;
	private BigDecimal maxCashbackAmount;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String gameProviderCode;
	private String gameGroupCode;
	private GameProviderRes gameProviderRes;

	public void setEntityToRes(CashbackCondition req) {
		this.setId(req.getId());
		this.setCode(req.getCode());
		this.setCashbackCode(req.getCashbackCode());
		this.setCashbackPercent(req.getCashbackPercent());
		this.setMoreThanAmount(req.getMoreThanAmount());
		this.setMaxCashbackAmount(req.getMaxCashbackAmount());
		this.setCreatedDate(req.getCreatedDate());
		this.setUpdatedBy(req.getUpdatedBy());
		this.setUpdatedDate(req.getUpdatedDate());
	}
}
