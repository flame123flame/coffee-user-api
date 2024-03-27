package coffee.backoffice.masterdata.vo.res;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class LovHdrRes {
	private Long fwLovHdrId;
	private String lovKey;
	private String lovDescription;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private List<LovDtlRes> listDetail;
}
