package coffee.backoffice.rebate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import coffee.backoffice.rebate.vo.req.RebateReq;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

@Entity
@Data
@Table(name = "rebate")
public class Rebate implements Serializable {
    private static final long serialVersionUID = -7264920419338542444L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "period_status")
    private Long periodStatus;

    @Column(name = "rebate_type")
    private Long rebateType;

    @Column(name = "is_auto_rebate")
    private Boolean isAutoRebate;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "vip_group_code", length = 255)
    private String vipGroupCode;

    @Column(name = "product_type_code", length = 255)
    private String productTypeCode;

    @Column(name = "max_group_rebate")
    private BigDecimal maxGroupRebate;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_date")
    private Date updatedDate = new Date();

    @Column(name = "next_batch_job_date")
    private Date nextBatchJobDate;

    @Column(name = "start_batch_job_date")
    private Date startBatchJobDate;

    @Column(name = "updated_by", length = 255)
    private String updatedBy = UserLoginUtil.getUsername();

    @Column(name = "code", length = 255)
    private String code = GenerateRandomString.generateUUID();

    @Column(name = "tag_code", length = 255)
    private String tagCode;

    @Column(name = "games_code_exclude", length = 255)
    private String gamesCodeExclude;

    @Column(name = "rebate_condition_multiplier")
    private BigDecimal rebateConditionMultiplier;

    public void setReqToEntity(RebateReq req) {
        id = req.getId() == null ? id : req.getId();
        title = req.getTitle() == null ? title : req.getTitle();
        description = req.getDescription() == null ? description : req.getDescription();
        startDate = req.getStartDate() == null ? startDate : req.getStartDate();
        endDate = req.getEndDate() == null ? endDate : req.getEndDate();
        periodStatus = req.getPeriodStatus() == null ? periodStatus : req.getPeriodStatus();
        rebateType = req.getRebateType() == null ? rebateType : req.getRebateType();
        isAutoRebate = req.getIsAutoRebate() == null ? isAutoRebate : req.getIsAutoRebate();
        status = req.getStatus() == null ? status : req.getStatus();
        vipGroupCode = req.getVipGroupCode() == null ? vipGroupCode : String.join(",", req.getVipGroupCode());
        productTypeCode = req.getProductTypeCode() == null ? productTypeCode : req.getProductTypeCode();
        maxGroupRebate = req.getMaxGroupRebate() == null ? maxGroupRebate : req.getMaxGroupRebate();
        createdDate = req.getCreatedDate() == null ? createdDate : req.getCreatedDate();
        updatedDate = req.getUpdatedDate() == null ? updatedDate : req.getUpdatedDate();
        updatedBy = req.getUpdatedBy() == null ? updatedBy : req.getUpdatedBy();
        code = req.getCode() == null ? code : req.getCode();
        tagCode = req.getTagCode() == null ? tagCode :  String.join(",",req.getTagCode());
        gamesCodeExclude = req.getGamesCodeExclude() == null ? gamesCodeExclude : req.getGamesCodeExclude();
        rebateConditionMultiplier = req.getRebateConditionMultiplier() == null ? rebateConditionMultiplier : req.getRebateConditionMultiplier();
    }
}
