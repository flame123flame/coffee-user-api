package coffee.backoffice.report.vo.res;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromotionReportDatatableRes {
    private Long id;
    private String promoCode;
    private String promoTitle;
    private String promoType;
    private String appPlatform;
    private String promoPeriodType;
    private Date startDate;
    private Date endDate;
    private String status;
    private String viewStatus;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;


    private Long rejectCount;
    private Long approveCount;
    private Long allCount;
}
