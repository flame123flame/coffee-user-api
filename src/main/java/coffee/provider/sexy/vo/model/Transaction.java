package coffee.provider.sexy.vo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Transaction {

//	@JsonProperty("ID")
	private Long ID;
	private String userId;
	private String platformTxId;
	private String platform;
	private String gameCode;
	private String gameType;
	private String betType;
	private String txTime;
	private BigDecimal betAmount;
	private BigDecimal winAmount;
	private BigDecimal turnover;
	private Integer txStatus;
	private BigDecimal realBetAmount;
	private BigDecimal realWinAmount;
	private BigDecimal jackpotBetAmount;
	private BigDecimal jackpotWinAmount;
	private String currency;
	private String updateTime;
	private String roundId;
	private String gameInfo;
	private String settleStatus;
}
