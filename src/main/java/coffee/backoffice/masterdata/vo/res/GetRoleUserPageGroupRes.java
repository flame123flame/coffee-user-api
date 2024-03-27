package coffee.backoffice.masterdata.vo.res;

import java.util.List;

import lombok.Data;

@Data
public class GetRoleUserPageGroupRes {
	 private Long userPageGroupId;
	 private String roleCode;
	 private String pageGroupCode;
	 private String createBy;
	 private String createDate;
	 private String updateBy;
	 private String updateDate;
	 private Boolean readMark;
	 private Boolean writeMark;
	 private Boolean editMark;
	 private Boolean approveMark;
	 private List<GetRoleUserPageDtlRes> listDetail;
}
