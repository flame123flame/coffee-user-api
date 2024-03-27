package coffee.backoffice.masterdata.vo.req;

import lombok.Data;

@Data
public class SaveRoleUserPageDtlReq {
	 private Long userPageDtlId;
	 private String pageDtlCode;
	 private Boolean readMark;
	 private Boolean writeMark;
	 private Boolean editMark;
	 private Boolean approveMark;
}
