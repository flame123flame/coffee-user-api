package coffee.backoffice.masterdata.vo.req;

import lombok.Data;

@Data
public class LovDtlReq {
	private Long fwLovDtlId;
	private String codeDetail;
	private String valueTh1;
	private String valueEn1;
	private String valueTh2;
	private String valueEn2;
	private Integer seq;
}
