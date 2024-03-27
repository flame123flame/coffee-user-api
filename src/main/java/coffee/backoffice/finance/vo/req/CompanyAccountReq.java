package coffee.backoffice.finance.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CompanyAccountReq {

    private String companyAccountCode;
    private String bank;
    private String bankAccount;
    private String accountName;
    private String bankCode;
    private BigDecimal maxDepositDaily;
    private BigDecimal maxWithdrawDaily;
    private String groupCode;
    private Date createdAt;
    private Date updatedAt;
    private BigDecimal balance;
    private Boolean enable;
    private String displayName;
    private String bankId;
}
