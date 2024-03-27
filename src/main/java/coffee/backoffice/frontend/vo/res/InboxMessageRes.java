package coffee.backoffice.frontend.vo.res;

import coffee.backoffice.promotion.vo.res.PromotionPlayerRes;
import lombok.Data;

@Data
public class InboxMessageRes {
	private Long id;
	private String messageCode;
	private String mappingCode;
	private String groupCode;
	private String subject;
	private String webMessage;
	private String createdBy;
	private String createdDate;
	private String promoCode;
	private String messageType;
	private String status;
	private PromotionPlayerRes promotion;
}
