package coffee.provider.joker.vo.model;

import java.math.BigDecimal;
import java.util.List;

import coffee.provider.joker.vo.res.JokerGamesListRes;
import lombok.Data;

@Data
public class JokerGetCredit {

	private String Username;
	private BigDecimal Credit;
	private BigDecimal OutstandingCredit;
	private String Message;
}
