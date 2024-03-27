package coffee.provider.joker.vo.model;

import java.math.BigDecimal;
import java.util.List;

import coffee.provider.joker.vo.res.JokerGamesListRes;
import lombok.Data;

@Data
public class JokerPlayGame {

	private String Token;
	private String Username;
	private String RequestID;
	private BigDecimal Credit;
	private BigDecimal BeforeCredit;
	private BigDecimal OutstandingCredit;
	private String Time;
	private String Message;
}
