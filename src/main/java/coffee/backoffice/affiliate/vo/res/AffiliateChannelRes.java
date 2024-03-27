package coffee.backoffice.affiliate.vo.res;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class AffiliateChannelRes {
	private Long id;
	private String affiliateChannelCode;
	private String affiliateGroupCode;
	private String channelName;
	private String productTypeCode;
	private BigDecimal shareRateOne;
	private BigDecimal shareRateTwo;
	private String remark;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private Boolean enable;
	private String providerCode;
	private String gameGroupCode;
}
