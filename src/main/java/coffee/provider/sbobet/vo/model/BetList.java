package coffee.provider.sbobet.vo.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BetList {

	private String refNo;
	private String username;
	private String sportsType;
	private String orderTime;
	private String winLostDate;
	private String settleTime;
	private String status;
	private String modifyDate;
	private BigDecimal odds;
	private String oddsStyle;
	private BigDecimal stake;
	private BigDecimal actualStake;
	private String currency;
	private BigDecimal winLost;
	private BigDecimal turnover;
	private Boolean isHalfWonLose;
	private Boolean isLive;
	private BigDecimal maxWinWithoutActualStake;
	private String ip;
	private List<SubBet> subBet;
	
}
