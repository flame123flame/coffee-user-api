package coffee.provider.sbobet.vo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetBetListRequest {
    private final String companyKey;
    private final String serverId;
    private final String portfolio;
    private String startDate;
    private String endDate;
}
