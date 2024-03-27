package coffee.backoffice.admin.model;

import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "fw_role")
public class FwRole implements Serializable {
    private static final long serialVersionUID = 1308796283095643483L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="code")
    private String code = GenerateRandomString.generateUUID();
    @Column(name="name")
    private String name;
    @Column(name="is_disable")
    private Boolean isDisable;
    @Column(name="created_by")
    private String createdBy = UserLoginUtil.getUsername();
    @Column(name="created_date")
    private Date createdDate = new Date();
    @Column(name="updated_by")
    private String updatedBy = UserLoginUtil.getUsername();
    @Column(name="updated_date")
    private Date updatedDate= new Date();

}
