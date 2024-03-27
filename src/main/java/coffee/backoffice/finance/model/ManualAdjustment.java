package coffee.backoffice.finance.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "manual_adjustment")
public class ManualAdjustment implements Serializable {

    private static final long serialVersionUID = -8122700060621191393L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "username")
    private String username;
    @Column(name = "adjustment_type")
    private String adjustmentType;
    @Column(name = "wallet")
    private String wallet;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "add_subtract")
    private String addSubtract;
}
