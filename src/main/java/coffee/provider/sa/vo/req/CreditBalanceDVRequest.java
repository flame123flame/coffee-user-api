package coffee.provider.sa.vo.req;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CreditBalanceDVRequest {

	private String method;
	private String key;
	private Date time;
	private String username;
	private String orderId;
	private BigDecimal creditAmount;
}
