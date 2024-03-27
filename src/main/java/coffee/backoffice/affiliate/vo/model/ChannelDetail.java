package coffee.backoffice.affiliate.vo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ChannelDetail {
	private String channelName;
	private BigDecimal shareRate;
	private BigDecimal bets;
	private BigDecimal income;
}
