package coffee.provider.sbobet.vo.res;

import coffee.provider.sbobet.vo.model.Error;
import lombok.Data;

@Data
public class LogoutResponse {
    private String serverId;
    private Error error;
}
