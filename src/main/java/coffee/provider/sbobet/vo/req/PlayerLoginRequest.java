package coffee.provider.sbobet.vo.req;

import lombok.Data;

@Data
public class PlayerLoginRequest {
    private String companyKey;
    private String serverId;
    private String username;
    private String portfolio;
}
