package coffee.backoffice.finance.vo.req;

import lombok.Data;

@Data
public class PlayerValidBetReq {
	private String username;
	private String startDate;
	private String endDate;
}
