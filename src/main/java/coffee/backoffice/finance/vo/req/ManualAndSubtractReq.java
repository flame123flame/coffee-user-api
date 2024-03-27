package coffee.backoffice.finance.vo.req;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ManualAndSubtractReq {
	private String username;
	private BigDecimal balance;
	private String wallet;
	private String walletType;
	private String turnoverType;
	private String turnoverAmount;
	private String status;
	private List<ProviderReq> game;
	
	@Data
	public static class ProviderReq {
		private String productType;
		private String providerCode;
		private String providerName;

	}
}