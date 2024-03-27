package coffee.backoffice.affiliate.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AffiliateReq {
    private Long id;
    private String username;
    private String affiliateCode;
    private String affiliateGroupCode;
    private String detail;
    private String banner;
    private int clickCount;
    private BigDecimal totalIncome;
    private BigDecimal incomeOne;
    private BigDecimal incomeTwo;
    private BigDecimal withdraw;
    private Date createdDate;
    private Date updatedDate;
    private String updatedBy;
}
