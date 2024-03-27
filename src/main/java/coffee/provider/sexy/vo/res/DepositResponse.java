package coffee.provider.sexy.vo.res;

import lombok.Data;

@Data
public class DepositResponse {

	private String status;
	private String amount;
	private String method;
	private String currentBalance;
	private int databaseId;
	private String lastModified;
	private String txCode;
}
