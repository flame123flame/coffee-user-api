package coffee.backoffice.rebate.vo.res;

import coffee.backoffice.casino.vo.res.GameProductTypeRes;
import coffee.backoffice.player.model.TagManagement;
import coffee.backoffice.player.vo.res.GroupLevelRes;
import coffee.backoffice.player.vo.res.TagManagementRes;
import coffee.backoffice.rebate.model.Rebate;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class RebateRes {
	private Long id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Long periodStatus;
	private Long rebateType;
	private Boolean isAutoRebate;
	private Boolean status;
	private List<String> vipGroupCode;
	private String productTypeCode;
	private BigDecimal maxGroupRebate;
	private Date createdDate;
	private Date updatedDate;
	private String updatedBy;
	private String code;
	private List<String> tagCode;
	private String gamesCodeExclude;
	private BigDecimal rebateConditionMultiplier;
	private GameProductTypeRes gameProductTypeRes;
	private TagManagementRes tagManagement;
	private List<RebateConditionRes> rebateConditionResList;
	private GroupLevelRes groupLevelRes;

	public void setEntityToRes(Rebate req) {
		this.setId(req.getId());
		this.setTitle(req.getTitle());
		this.setDescription(req.getDescription());
		this.setStartDate(req.getStartDate());
		this.setEndDate(req.getEndDate());
		this.setPeriodStatus(req.getPeriodStatus());
		this.setRebateType(req.getRebateType());
		this.setIsAutoRebate(req.getIsAutoRebate());
		this.setStatus(req.getStatus());
		this.setVipGroupCode(new ArrayList<String>(Arrays.asList(req.getVipGroupCode().split(","))));
		this.setProductTypeCode(req.getProductTypeCode());
		this.setMaxGroupRebate(req.getMaxGroupRebate());
		this.setCreatedDate(req.getCreatedDate());
		this.setUpdatedDate(req.getUpdatedDate());
		this.setUpdatedBy(req.getUpdatedBy());
		this.setCode(req.getCode());
		this.setTagCode(new ArrayList<String>(Arrays.asList(req.getTagCode().split(","))));
		this.setGamesCodeExclude(req.getGamesCodeExclude());
		this.setRebateConditionMultiplier(req.getRebateConditionMultiplier());
	}
}
