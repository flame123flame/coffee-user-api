package coffee.website.providers.vo.request;

import lombok.Data;

@Data
public class PlayGameRequest {

	private String orderNo;
	private String username;
	private String providerCode;
	private String gameName;
	private String gameCode;
	private String balance;
	private String remark;
	private String iconUrl;
	private Boolean checkBonus;
}
