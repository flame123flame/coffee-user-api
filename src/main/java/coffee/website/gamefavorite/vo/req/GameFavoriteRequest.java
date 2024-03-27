package coffee.website.gamefavorite.vo.req;

import lombok.Data;

@Data
public class GameFavoriteRequest {

	private String username;
	private String gameCode;
	private String gameName;
	private String providerCode;
	private String viewStatus;
	private String iconUrl;
	
}
