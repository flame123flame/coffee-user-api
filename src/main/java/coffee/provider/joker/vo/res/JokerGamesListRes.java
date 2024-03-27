package coffee.provider.joker.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class JokerGamesListRes {

	private String GameType;
	private String GameCode;
	private String GameName;
	private BigDecimal DefaultWidth;
	private BigDecimal DefaultHeight;
	private String Special;
	private BigDecimal Order;
	private String Image1;
	private String Image2;
}
