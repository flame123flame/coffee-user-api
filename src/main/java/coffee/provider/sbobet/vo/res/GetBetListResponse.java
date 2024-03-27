package coffee.provider.sbobet.vo.res;

import java.util.List;

import coffee.provider.sbobet.vo.model.BetList;
import coffee.provider.sbobet.vo.model.Error;
import lombok.Data;

@Data
public class GetBetListResponse {

	private List<BetList> result;
	private String serverId;
	private Error error;
}
