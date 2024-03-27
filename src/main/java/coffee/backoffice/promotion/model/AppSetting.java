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

import coffee.backoffice.promotion.vo.req.AppSettingReq;
import lombok.Data;

@Data
@Entity
@Table(name = "app_setting")
public class AppSetting implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5775851420961073973L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "valid_period")
    private BigDecimal validPeriod;

    @Column(name = "verification_type")
    private String verificationType;

    @Column(name = "deposit_sequence")
    private String depositSequence;
    
    @Column(name = "recive_limit")
    private Integer reciveLimit;

    @Column(name = "allow_app")
    private String allowApp;

    @Column(name = "not_allow_tag")
    private String notAllowTag;

    @Column(name = "risk_options")
    private String riskOptions;

    @Column(name = "violation_setting")
    private String violationSetting;

    @Column(name = "group_list")
    private String groupList;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "time_gap_limitation_enable")
    private Boolean timeGapLimitationEnable;
    @Column(name = "time_gap_limitation_value")
    private Long timeGapLimitationValue;
    @Column(name = "time_gap_limitation_type")
    private String timeGapLimitationType;
    @Column(name = "violation_count_setting")
    private Long violationCountSetting;
    @Column(name = "same_real_name")
    private Boolean sameRealName;
    @Column(name = "same_ip")
    private Boolean sameIP;


    public void setReqToEntity(AppSettingReq req) {
        if (req != null) {
            promoCode = req.getPromoCode();
            validPeriod = req.getValidPeriod();
            verificationType = req.getVerificationType();
            depositSequence = req.getDepositSequence();
            allowApp = req.getAllowApp();
            if (req.getGroupList() != null) {
                groupList = String.join(",", req.getGroupList());
            }
            notAllowTag = String.join(",", req.getNotAllowTag());
            riskOptions = req.getRiskOptions();
            violationSetting = req.getViolationSetting();
            timeGapLimitationEnable = req.getTimeGapLimitationEnable();
            timeGapLimitationValue = req.getTimeGapLimitationValue();
            timeGapLimitationType = req.getTimeGapLimitationType();
            violationCountSetting = req.getViolationCountSetting();
            sameRealName = req.getSameRealName();
            sameIP = req.getSameIP();
            reciveLimit = req.getReciveLimit();
        }
    }
}
