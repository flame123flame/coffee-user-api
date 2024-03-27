package coffee.backoffice.admin.vo.res;

import lombok.Data;

@Data
public class FwRoleMenuAccessRes {
    private Long id;
    private String code;
    private String fwUserRoleCode;
    private String menuId;
}
