package coffee.backoffice.admin.vo.req;

import lombok.Data;

import java.util.List;

@Data
public class FwRoleReq {
//    private Long id;
//    private String code;
    private String name;
    private Boolean isDisable;
    private List<FwRoleMenuAccessReq> fwRoleMenuAccessReq;
//    private String createdBy;
//    private Date createdDate;
//    private String updatedBy;
//    private Date updatedDate;
}
