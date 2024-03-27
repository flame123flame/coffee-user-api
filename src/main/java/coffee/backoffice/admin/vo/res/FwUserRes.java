package coffee.backoffice.admin.vo.res;

import coffee.backoffice.admin.model.FwRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FwUserRes {
    private Long fwUsersId;
    private String username;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Boolean isDisable;
    private Boolean isActive;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<FwRole> role;
    private List<FwRoleMenuAccessRes> menu;
}
