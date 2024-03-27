package coffee.backoffice.rebate.vo.res;

import coffee.backoffice.casino.vo.res.GameProviderRes;
import coffee.backoffice.rebate.model.RebateCondition;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RebateConditionRes {
	private Long id;
	private String code;
	private String rebateCode;
	private BigDecimal rebatePercent;
	private BigDecimal maxRebate;
	private BigDecimal validBets;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String gameProviderCode;
	private String gameGroupCode;
	private GameProviderRes gameProviderRes;

	public void setEntityToRes(RebateCondition req) {
		this.setId(req.getId());
		this.setCode(req.getCode());
		this.setRebateCode(req.getRebateCode());
		this.setRebatePercent(req.getRebatePercent());
		this.setMaxRebate(req.getMaxRebate());
		this.setValidBets(req.getValidBets());
		this.setCreatedDate(req.getCreatedDate());
		this.setUpdatedBy(req.getUpdatedBy());
		this.setUpdatedDate(req.getUpdatedDate());
		this.setGameProviderCode(req.getGameProviderCode());
		this.setGameGroupCode(req.getGameGroupCode());
	}
}
