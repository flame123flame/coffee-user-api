package coffee.website.gamefavorite.vo.res;

import lombok.Data;

@Data
public class GameFavoriteResponse {

	private int seqence;
	private String providerCode;
	private String gameCode;
	private String gameName;
	private String iconUrl;
	
}
