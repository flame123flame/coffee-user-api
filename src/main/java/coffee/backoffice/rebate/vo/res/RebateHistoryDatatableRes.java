package coffee.backoffice.rebate.vo.res;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
public class RebateHistoryDatatableRes {
    private Long id;
    private String rebateHistoryCode;
    private String rebateCode;
    private String rebateTitle;
    private String username;
    private String groupCode;
    private String groupName;
    private Boolean isAutoRebate;
    private BigDecimal validBets;
    private BigDecimal originalRebate;
    private BigDecimal actualRebate;
    private String remark;
    private Boolean status;
    private String createdBy;
    private String createdDate;
}
