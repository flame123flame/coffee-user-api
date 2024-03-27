package coffee.backoffice.cashback.model;


import framework.utils.GenerateRandomString;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "cashback_batch_waiting")
public class CashbackBatchWaiting implements Serializable {
    private static final long serialVersionUID = 7455158675667515284L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code = GenerateRandomString.generateUUID();
    @Column(name = "username")
    private String username;
    @Column(name = "before_balance")
    private BigDecimal beforeBalance;
    @Column(name = "deposit")
    private BigDecimal deposit;
    @Column(name = "withdraw")
    private BigDecimal withdraw;
    @Column(name = "current_balance")
    private BigDecimal currentBalance;
    @Column(name = "received_amount")
    private BigDecimal receivedAmount;
    @Column(name = "receive_date")
    private Date receiveDate;
    @Column(name = "status")
    private String status;
    @Column(name = "cashback_code")
    private String cashbackCode;
    @Column(name = "is_auto")
    private Boolean isAuto;
    @Column(name = "cashback_title")
    private String cashbackTitle ;
    @Column(name = "condition_multiple")
    private BigDecimal conditionMultiple ;
    @Column(name = "date_period_type")
    private Long datePeriodType ;
    @Column(name = "total_loss")
    private BigDecimal totalLoss ;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "updated_date")
    private Date updatedDate;
}
