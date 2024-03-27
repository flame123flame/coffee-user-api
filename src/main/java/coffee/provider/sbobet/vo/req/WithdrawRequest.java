package coffee.provider.sbobet.vo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {
    private String companyKey;
    private String serverId;
    private String username;
    private BigDecimal amount;
    private String txnId;
    private Boolean IsFullAmount;
}
