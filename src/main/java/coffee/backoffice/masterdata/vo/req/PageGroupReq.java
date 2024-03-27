package coffee.backoffice.masterdata.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class PageGroupReq {
	private Long fwPageGroupId;
	private String pageGroupCode;
	private String pageGroupName;
	private String pageGroupDescription;
	private List<PageDtlReq> listDetail;
}
