package coffee.provider.sbobet.vo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AgentRegisterRequest {
    private String companyKey;
    private String serverId;
    private String username;
    private String password;
    private String currency;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal maxPerMatch;
    private Integer casinoTableLimit;
}
