package coffee.provider.sbobet.vo.req;

import lombok.Data;

@Data
public class PlayerRegisterRequest {
    private String companyKey;
    private String serverId;
    private String username;
    private String agent;
}
