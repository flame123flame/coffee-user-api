package coffee.backoffice.promotion.vo.req;

import lombok.Data;

@Data
public class PostSettingReq {

	private String promoCode;
	private String promoTitle;
	private String appPlatform;
	private String lang;
	private String deskBanner;
	private String deskDetail;
	private String mobileBanner;
	private String mobileDetail;

}
