package coffee.backoffice.affiliate.vo.req;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class AffiliateGroupReq {
	private Long id;
	private String affiliateGroupCode;
	private String groupName;
	private String description;
	private String withdrawCondition;
	private BigDecimal minTotalBets;
	private BigDecimal minAffiliateCount;
	private BigDecimal minTotalIncome;
	private List<AffiliateChannelReq> channelList;
}
