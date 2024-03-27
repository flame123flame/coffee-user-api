package coffee.backoffice.masterdata.vo.res;

import java.util.List;

import lombok.Data;

@Data
public class PageGroupRes {
	private Long fwPageGroupId;
	private String pageGroupCode;
	private String pageGroupName;
	private String pageGroupDescription;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private Integer seq;
	private List<PageDtlRes> listDetail;
}
