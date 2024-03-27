package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.casino.model.GameProductType;
import coffee.backoffice.casino.model.Games;
import lombok.Data;

@Data
public class TransactionGameRes {
	private Long id;
	private String gameSessionId;
	private Date createdDate;
	private String gameProvider;
	private String username;
	private String gameCode;
	private BigDecimal bet;
	private BigDecimal betResult;
	private BigDecimal winLoss;
	private Games games;
	private GameProductType gameProductType;
}
