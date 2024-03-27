package coffee.backoffice.finance.vo.req;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ManualAdjustmentReq {
    private Long id;
    private String code;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
    private String username;
    private String adjustmentType;
    private String wallet;
    private BigDecimal amount;
    private String addSubtract;
}
