package coffee.backoffice.promotion.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromotionReq {
	
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
    private PostSettingReq postSetting;
    private AppSettingReq appSetting;
    private RuleSettingReq ruleSetting;
}
