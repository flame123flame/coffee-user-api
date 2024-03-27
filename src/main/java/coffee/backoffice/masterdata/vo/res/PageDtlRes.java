package coffee.backoffice.masterdata.vo.res;

import lombok.Data;

@Data
public class PageDtlRes {
	private Long fwPageDtlId;
	private String pageDtlCode;
	private String pageGroupCode;
	private String pageDtlName;
	private String pageDtlDescription;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private Integer seq;
}
