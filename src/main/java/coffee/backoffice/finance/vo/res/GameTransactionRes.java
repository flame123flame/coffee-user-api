package coffee.backoffice.finance.vo.res;

import lombok.Data;

@Data
public class GameTransactionRes {
	private String id;
	private String gameCode;
	private String bet;
	private String bet_result;
	private String gameSessionId;
	private String balance;
	private String username;
	private String createdDate;
	private String gameProvider;
	private String winLoss;
	private String gameResult;
	private String displayName;

}
