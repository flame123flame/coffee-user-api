package coffee.backoffice.cashback.vo.res;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import coffee.backoffice.cashback.model.Cashback;
import coffee.backoffice.cashback.model.CashbackBatchWaiting;
import coffee.backoffice.casino.vo.res.GameProductTypeRes;
import coffee.backoffice.player.vo.res.GroupLevelRes;
import coffee.backoffice.player.vo.res.TagManagementRes;
import lombok.Data;

@Data
public class CashbackRes {
    private Long id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long periodStatus;
    private Long cashbackType;
    private Boolean isAutoCashback;
    private Boolean status;
    private List<String> vipGroupCode;
    private String productTypeCode;
    private BigDecimal cashbackConditionMultiplier;
    private Date createdDate;
    private Date updatedDate;
    private String updatedBy;
    private String code;
    private List<String> tagCode;
    private String gamesCodeExclude;
    private GameProductTypeRes gameProductTypeRes;
    private TagManagementRes tagManagement;
    private List<CashbackConditionRes> cashbackConditionResList;
    private List<CashbackBatchWaiting> cashbackBatchWaitingList;
    private GroupLevelRes groupLevelRes;
    private Date nextBatchJobDate;
    private Date startBatchJobDate;


    public void setEntityToRes(Cashback req) {
        this.setId(req.getId());
        this.setTitle(req.getTitle());
        this.setDescription(req.getDescription());
        this.setStartDate(req.getStartDate());
        this.setEndDate(req.getEndDate());
        this.setPeriodStatus(req.getPeriodStatus());
        this.setIsAutoCashback(req.getIsAutoCashback());
        this.setStatus(req.getStatus());
        this.setVipGroupCode(new ArrayList<String>(Arrays.asList(req.getVipGroupCode().split(","))));
        this.setCashbackConditionMultiplier(req.getCashbackConditionMultiplier());
        this.setCreatedDate(req.getCreatedDate());
        this.setUpdatedDate(req.getUpdatedDate());
        this.setNextBatchJobDate(req.getNextBatchJobDate());
        this.setStartBatchJobDate(req.getStartBatchJobDate());
        this.setUpdatedBy(req.getUpdatedBy());
        this.setCode(req.getCode());
        this.setTagCode(new ArrayList<String>(Arrays.asList(req.getTagCode().split(","))));
    }
}
