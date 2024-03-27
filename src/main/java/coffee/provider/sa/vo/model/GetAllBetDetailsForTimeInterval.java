package coffee.provider.sa.vo.model;

import lombok.Data;

@Data
public class GetAllBetDetailsForTimeInterval {

	private BetList BetDetailList;
	private int ErrorMsgId;
	private String ErrorMsg;
	
}
