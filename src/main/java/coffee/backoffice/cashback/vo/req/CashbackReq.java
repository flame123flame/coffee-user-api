package coffee.backoffice.cashback.vo.req;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CashbackReq {
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
    private Date createdDate;
    private Date updatedDate;
    private String updatedBy;
    private String code;
    private List<String> tagCode;
    private String gamesCodeExclude;
    private List<CashbackConditionReq> cashbackConditionList;
    private BigDecimal cashbackConditionMultiplier;
}
