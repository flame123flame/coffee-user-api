package coffee.provider.sbobet.vo.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class SubBet {

	private String betOption;
	private String marketType;
	private BigDecimal hdp;
	private BigDecimal odds;
	private String league;
	private String match;
	private String status;
	private String winlostDate;
	private String liveScore;
	private String htScore;
	private String ftScore;
	private String customeizedBetType;
	private String kickOffTime;
}
