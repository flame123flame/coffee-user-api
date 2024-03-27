package coffee.provider.sbobet.vo.req;

import lombok.Data;

@Data
public class PlayerLogoutRequest {
    private String companyKey;
    private String serverId;
    private String username;
}
