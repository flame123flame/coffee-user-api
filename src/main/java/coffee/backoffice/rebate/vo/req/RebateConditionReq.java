package coffee.backoffice.rebate.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class RebateConditionReq {
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
}
