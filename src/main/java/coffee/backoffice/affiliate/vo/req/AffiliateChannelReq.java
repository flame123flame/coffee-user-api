package coffee.backoffice.affiliate.vo.req;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AffiliateChannelReq {
	private Long id;
	private String affiliateChannelCode;
	private String affiliateGroupCode;
	private String channelName;
	private String productType;
	private BigDecimal shareRateOne;
	private BigDecimal shareRateTwo;
	private String remark;
	private String provider;
	private String gameGroup;
}
