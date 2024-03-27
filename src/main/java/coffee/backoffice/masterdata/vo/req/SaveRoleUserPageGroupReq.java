package coffee.backoffice.masterdata.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class SaveRoleUserPageGroupReq {
	 private Long userPageGroupId;
	 private String pageGroupCode;
	 private Boolean readMark;
	 private Boolean writeMark;
	 private Boolean editMark;
	 private Boolean approveMark;
	 private List<SaveRoleUserPageDtlReq> listDetail;
}
