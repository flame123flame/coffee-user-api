package coffee.backoffice.finance.vo.res;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WithdrawRes {
    private Long id;
    private String orderWithdraw;
    private String username;
    private String bankAccount;
    private String accountName;
    private String bankName;
    private BigDecimal amount;
    private String toBankAccount;
    private String toAccountName;
    private String toBankName;
    private Date withdrawDate;
    private String withdrawStatus;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String adminRemark;
    private String userRemark;
    private BigDecimal afterBalance;
    private BigDecimal beforeBalance;

}
