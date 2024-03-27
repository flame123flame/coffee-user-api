package coffee.backoffice.finance.vo.res;

import coffee.backoffice.finance.model.Bank;
import coffee.backoffice.finance.model.CompanyAccount;
import coffee.backoffice.player.model.GroupLevel;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@Data
public class CompanyAccountRes {
	
	private String companyAccountCode;
    private String bank;
    private String bankAccount;
    private String accountName;
    private String bankCode;
    private BigDecimal maxDepositDaily;
    private BigDecimal currDepositDaily;
    private BigDecimal maxWithdrawDaily;
    private BigDecimal currWithdrawDaily;
    private String groupCode;
    private String createdAt;
    private String updatedAt;
    private BigDecimal balance;
    private Boolean enable;
    private String displayName;
    private String bankId;
    private GroupLevel group;

    public void setEntityToRes(CompanyAccount req) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        companyAccountCode = req.getCompanyAccountCode();
        bank = req.getBank();
        bankAccount = req.getBankAccount();
        accountName = req.getAccountName();
        bankCode = req.getBankCode();
        maxDepositDaily = req.getMaxDepositDaily();
        currDepositDaily = req.getCurrDepositDaily();
        maxWithdrawDaily = req.getMaxWithdrawDaily();
        currWithdrawDaily = req.getCurrWithdrawDaily();
        bankId = req.getBankId();
        groupCode = req.getGroupCode();
        createdAt = req.getCreatedAt() !=null ? simpleDateFormat.format(req.getCreatedAt()):null;
        updatedAt = req.getUpdatedAt() !=null ? simpleDateFormat.format(req.getUpdatedAt()):null;
        balance = req.getBalance();
        enable = req.getEnable();
        displayName = req.getDisplayName();
    }
}
