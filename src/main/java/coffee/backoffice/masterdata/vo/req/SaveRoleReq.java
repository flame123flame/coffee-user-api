package coffee.backoffice.masterdata.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class SaveRoleReq {
	private Long fwRoleId;
	private String roleCode;
	private String roleName;
	private String roleDescription;
	private List<SaveRoleUserPageGroupReq> listGroup;
}
