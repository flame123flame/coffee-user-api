package coffee.provider.lotto.vo.req;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class LotttoProviderUpdateWalletReqModel {
	private final String key;
	private final List<Lotto> data;

	@Data
	public static class Lotto {
		private String username;
		private String prize;
	}
}
