package coffee.backoffice.promotion.vo.res;

import java.util.Date;

import coffee.backoffice.promotion.model.PostSetting;
import coffee.backoffice.promotion.model.Promotion;
import lombok.Data;

@Data
public class PromotionProfileRes {
	private Long id;
	private String promoCode;
	private String username;
	private String status;
	private Date dateActive;
	private String requestId;
	private Promotion promotion;
	private PostSetting postSetting;
}
