package coffee.backoffice.finance.model;

import coffee.backoffice.player.model.GroupLevel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@Table(name = "company_account")
public class CompanyAccount implements Serializable {
    private static final long serialVersionUID = 4817498714886742497L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "bank" , length = 255)
    private String bank;
    @Column(name = "company_account_code" , length = 255)
    private String companyAccountCode;
    @Column(name = "bank_account" , length = 255)
    private String bankAccount;
    @Column(name = "account_name" , length = 255)
    private String accountName;
    @Column(name = "bank_code" , length = 255)
    private String bankCode;
    @Column(name = "max_deposit_daily")
    private BigDecimal maxDepositDaily;
    @Column(name = "curr_deposit_daily")
    private BigDecimal currDepositDaily = BigDecimal.ZERO;
    @Column(name = "max_withdraw_daily")
    private BigDecimal maxWithdrawDaily;
    @Column(name = "curr_withdraw_daily")
    private BigDecimal currWithdrawDaily = BigDecimal.ZERO;
    @Column(name = "group_code" , length = 255)
    private String groupCode;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;
    @Column(name = "enable")
    private Boolean enable = true;
    @Column(name = "display_name" , length = 255)
    private String displayName;
    @Column(name = "bank_id" , length = 255)
    private String bankId;
}
