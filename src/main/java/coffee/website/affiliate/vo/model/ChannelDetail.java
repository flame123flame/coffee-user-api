package coffee.website.affiliate.vo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ChannelDetail {

	private String channelName;
	private BigDecimal shareRateOne;
	private BigDecimal shareRateTwo;
	private BigDecimal totalBetOne;
	private BigDecimal totalBetTwo;
	private BigDecimal totalIncome;
}
