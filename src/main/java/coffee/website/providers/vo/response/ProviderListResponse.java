package coffee.website.providers.vo.response;

import lombok.Data;

@Data
public class ProviderListResponse {

	private String providerCode;
	private String displayName;
	private String openType;
	private String portraitImgUrl;
	private String landscapeImgUrl;
	private Long totalGame;

}
