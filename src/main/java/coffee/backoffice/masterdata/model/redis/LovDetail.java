package coffee.backoffice.masterdata.model.redis;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LovDetail {
	private String lovKey;
	private String valueTh1;
	private String valueEn1;
	private String valueTh2;
	private String valueEn2;
	private String codeDetail;
	private Integer seq;
}
