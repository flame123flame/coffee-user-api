package coffee.backoffice.promotion.vo.res;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromotionPlayerRes {

    private String promoCode;
    private String promoTitle;
    private String promoBanner;
    private String promoDetail;
    private String promoType;
   
}
