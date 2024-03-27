package coffee.website.providers.vo.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GameListResponse {
	
	private String gameType;
	private String gameCode;
	private String gameName;
	private String providerCode;
	private BigDecimal defaultWidth;
	private BigDecimal defaultHeight;
	private String special;
	private BigDecimal order;
	private String image1;
	private String image2;
	private String gameTag;
}
