package coffee.backoffice.admin.model;

import framework.utils.GenerateRandomString;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "fw_role_menu_access")
public class FwRoleMenuAccess implements Serializable {
    private static final long serialVersionUID = 4797524670551972632L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="code")
    private String code = GenerateRandomString.generateUUID();
    @Column(name="fw_user_role_code")
    private String fwUserRoleCode;
    @Column(name="menu_id")
    private String menuId;
}
