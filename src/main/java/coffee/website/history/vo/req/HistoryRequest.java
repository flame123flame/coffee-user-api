package coffee.website.history.vo.req;

import lombok.Data;

@Data
public class HistoryRequest {

	private String username;
	private String historyType;
	private String period;
}
