package coffee.backoffice.rebate.model;


import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "rebate_batch_waiting")
public class RebateBatchWaiting implements Serializable {
    private static final long serialVersionUID = -3740593201429491879L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code = GenerateRandomString.generateUUID();
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "rebate_code")
    private String rebateCode;
    @Column(name = "rebate_condition_code")
    private String rebateConditionCode;
    @Column(name = "username")
    private String username;
    @Column(name = "status")
    private String status;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "receive_date")
    private Date receiveDate;
    @Column(name = "created_by")
    private String createdBy = UserLoginUtil.getUsername();
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "is_auto")
    private Boolean isAuto;
    @Column(name = "rebate_title")
    private String rebateTitle ;
    @Column(name = "condition_multiple")
    private BigDecimal conditionMultiple ;
    @Column(name = "date_period_type")
    private Long datePeriodType ;
}
