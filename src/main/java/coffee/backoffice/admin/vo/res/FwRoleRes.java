package coffee.backoffice.admin.vo.res;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FwRoleRes {
    private Long id;
    private String code;
    private String name;
    private Boolean isDisable;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<FwRoleMenuAccessRes> fwRoleMenuAccessRes;
    private Long fwUserCount;
}
