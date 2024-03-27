package coffee.website.affiliate.vo.res;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import coffee.website.affiliate.vo.model.ChannelDetail;
import lombok.Data;

@Data
public class AffliateDownlineResponse {
	private Date registerDate;
	private String username;
	private BigDecimal totalBet;
	private BigDecimal totalIncome;
	private List<ChannelDetail> channelDetailList;
}
