package coffee.backoffice.rebate.model;

import coffee.backoffice.rebate.vo.req.RebateConditionReq;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "rebate_condition")
public class RebateCondition implements Serializable {
	private static final long serialVersionUID = 6401414843809864553L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code = GenerateRandomString.generateUUID();

	@Column(name = "rebate_code", length = 255)
	private String rebateCode;

	@Column(name = "rebate_percent")
	private BigDecimal rebatePercent;

	@Column(name = "max_rebate")
	private BigDecimal maxRebate;

	@Column(name = "valid_bets")
	private BigDecimal validBets;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy = UserLoginUtil.getUsername();

	@Column(name = "updated_date")
	private Date updatedDate = new Date();
	@Column(name = "game_provider_code", length = 8000)
	private String gameProviderCode;
	@Column(name = "game_group_code", length = 8000)
	private String gameGroupCode;

	public void setReqToEntity(RebateConditionReq req) {
		id = req.getId() == null ? id : req.getId();
		code = req.getCode() == null ? code : req.getCode();
		rebateCode = req.getRebateCode() == null ? rebateCode : req.getRebateCode();
		rebatePercent = req.getRebatePercent() == null ? rebatePercent : req.getRebatePercent();
		maxRebate = req.getMaxRebate() == null ? maxRebate : req.getMaxRebate();
		validBets = req.getValidBets() == null ? validBets : req.getValidBets();
		createdDate = req.getCreatedDate() == null ? createdDate : req.getCreatedDate();
		updatedBy = req.getUpdatedBy() == null ? updatedBy : req.getUpdatedBy();
		updatedDate = req.getUpdatedDate() == null ? updatedDate : req.getUpdatedDate();
		gameProviderCode = req.getGameProviderCode() == null ? gameProviderCode : req.getGameProviderCode();
		gameGroupCode = req.getGameGroupCode() == null ? gameGroupCode : req.getGameGroupCode();
	}
}
