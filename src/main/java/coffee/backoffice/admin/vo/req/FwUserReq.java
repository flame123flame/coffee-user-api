package coffee.backoffice.admin.vo.req;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FwUserReq {
//    private Long fwUsersId;
    private String username;
    private String password;
//    private Date lastLoginTime;
//    private String lastLoginIp;
    private Boolean isDisable;
    private Boolean isActive;
//    private String createdBy;
//    private Date createdDate;
//    private String updatedBy;
//    private Date updatedDate;

    private List<FwUserRoleMappingReq> fwUserRoleMappingReqList;
}
