package coffee.provider.sa.vo.req;

import java.util.Date;

import lombok.Data;

@Data
public class GetAllBetDetailsForTimeIntervalDVRequest {

	private String method;
	private String key;
	private Date time;
	private String username;
	private Date fromTime;
	private Date toTime;
}
