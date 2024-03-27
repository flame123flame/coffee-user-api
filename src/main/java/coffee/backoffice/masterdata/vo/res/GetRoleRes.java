package coffee.backoffice.masterdata.vo.res;

import java.util.List;

import lombok.Data;

@Data
public class GetRoleRes {
	private Long fwRoleId;
	private String roleCode;
	private String roleName;
	private String roleDescription;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private List<GetRoleUserPageGroupRes> listGroup;
}
