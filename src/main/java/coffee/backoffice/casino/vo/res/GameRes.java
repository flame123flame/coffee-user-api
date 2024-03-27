package coffee.backoffice.casino.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class GameRes {
	private Long id;
	private String code;
	private String gameCode;
	private String externalId;
	private BigDecimal betLimits;
	private String displayName;
	private String iconUrl;
	private String provider;
	private String productType;
	private String gameGroup;
	private Date updatedAt;
	private String updatedBy;
	private String status;
	private String description;
}
