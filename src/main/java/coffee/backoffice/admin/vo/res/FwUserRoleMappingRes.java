package coffee.backoffice.admin.vo.res;

import lombok.Data;

import java.util.Date;

@Data
public class FwUserRoleMappingRes {
    private Long id;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String username;
    private String fwRoleCode;
}
