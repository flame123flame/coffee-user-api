package coffee.provider.sa.vo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BetDetail {
	
	private String BetTime;
	private String PayoutTime;
	private String Username;
	private Long HostID;
	private String Detail;
	private String GameID;
	private Long Round;
	private Long Set;
	private Long BetID;
	private BigDecimal BetAmount;
	private BigDecimal Rolling;
	private BigDecimal ResultAmount;
	private BigDecimal Balance;
	private String GameType;
	private Integer BetType;
	private Integer BetSource;
	private Long TransactionID;
}