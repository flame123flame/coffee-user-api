package coffee.backoffice.affiliate.vo.res;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AffiliateGroupRes {
	private Long id;
	private String affiliateGroupCode;
	private String groupName;
	private String description;
	private String withdrawCondition;
	private BigDecimal minTotalBets;
	private BigDecimal minAffiliateCount;
	private BigDecimal minTotalIncome;
	private String  createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private Boolean enable;
	private List<AffiliateChannelRes> channelList;
}
