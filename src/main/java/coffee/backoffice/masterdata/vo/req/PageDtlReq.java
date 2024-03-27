package coffee.backoffice.masterdata.vo.req;

import lombok.Data;

@Data
public class PageDtlReq {
	private Long fwPageDtlId;
	private String pageDtlCode;
	private String pageGroupCode;
	private String pageDtlName;
	private String pageDtlDescription;
}
