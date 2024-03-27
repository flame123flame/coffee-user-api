package coffee.provider.joker.vo.res;

import lombok.Data;

@Data
public class GenarateSignatureRes {
	private final String signature;
	private final Long timestampUnix;
}
