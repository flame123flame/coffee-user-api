package coffee.provider.sexy.vo.req;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class MxRequest {

	private String userId;
	private String amount;
	private String gameCode;
	private String gameType;
	private String platform;
	private String provider;
	private String orderId;
	private List<String> userIds;
}
