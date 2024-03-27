package coffee.backoffice.masterdata.vo.req;

import lombok.Data;

@Data
public class ConstantReq {
	private Long fwConstantId;
	private String constantKey;
	private String constantValue;
}
