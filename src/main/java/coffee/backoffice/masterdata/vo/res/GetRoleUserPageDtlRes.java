package coffee.backoffice.masterdata.vo.res;

import lombok.Data;

@Data
public class GetRoleUserPageDtlRes {
	 private Long userPageDtlId;
	 private String roleCode;
	 private String pageGroupCode;
	 private String pageDtlCode;
	 private String createBy;
	 private String createDate;
	 private String updateBy;
	 private String updateDate;
	 private Boolean readMark;
	 private Boolean writeMark;
	 private Boolean editMark;
	 private Boolean approveMark;
}
