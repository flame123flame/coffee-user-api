package coffee.backoffice.promotion.vo.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class MyPromotionDetail {
	
	private String promoCode;
    private Date dateActive;
    private String status;
    private String promotionTitle;
    private BigDecimal bonus;
    private String remark;
    private BigDecimal targetTurnOver;
    private BigDecimal currentTurnOver;

}
