package coffee.backoffice.admin.model;

import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "fw_user_role_mapping")
public class FwUserRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code" , length = 255)
    private String code = GenerateRandomString.generateUUID();
    @Column(name = "created_by" , length = 255)
    private String createdBy = UserLoginUtil.getUsername();
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "updated_by" , length = 255)
    private String updatedBy = UserLoginUtil.getUsername();
    @Column(name = "updated_date")
    private Date updatedDate = new Date();
    @Column(name = "username" , length = 255)
    private String username;
    @Column(name = "fw_role_code" , length = 255)
    private String fwRoleCode;
}
