package coffee.backoffice.masterdata.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class LovHdrReq {
	private Long fwLovHdrId;
	private String lovKey;
	private String lovDescription;
	private List<LovDtlReq> listDetail;
}
