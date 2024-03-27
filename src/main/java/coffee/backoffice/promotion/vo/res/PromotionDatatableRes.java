package coffee.backoffice.promotion.vo.res;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.Date;

@Data
public class PromotionDatatableRes {
    private Long id;
    private String promoCode;
    private String promoTitle;
    private String promoType;
    private String wallet;
    private String promoPeriodType;
    private Date startDate;
    private Date endDate;
    private String updatedBy;
    private Date updatedDate;
    private String viewStatus;
    private Boolean deleteAble;

    private String receiveBonusWallet;
}
