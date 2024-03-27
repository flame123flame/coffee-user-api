package coffee.backoffice.promotion.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromotionRequestReq {
	
	private String username;
	private String requestId;
    private String playerGroup;
    private String promoCode;
    private String statusRequest;

}