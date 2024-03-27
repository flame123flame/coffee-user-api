package coffee.backoffice.player.model;

import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "deposit_information")
public class DepositInformation implements Serializable {
    private static final long serialVersionUID = -8239284823363417416L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code = GenerateRandomString.generateUUID();
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    private Date updatedDate;
    @Column(name = "total_count")
    private Long totalCount;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "username")
    private String username;
}
