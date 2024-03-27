package coffee.backoffice.promotion.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

@Data
public class AppSettingReq {
	
	private String promoCode;
	private BigDecimal validPeriod;
	private String verificationType;
	private String depositSequence;
	private Integer reciveLimit;
	private String allowApp;
	private List<String> groupList;
	private List<String> notAllowTag;
	private String riskOptions;
	private String violationSetting;
	private Boolean timeGapLimitationEnable;
	private Long timeGapLimitationValue;
	private String timeGapLimitationType;
	private Long violationCountSetting;
	private Boolean sameRealName;
	private Boolean sameIP;

}
