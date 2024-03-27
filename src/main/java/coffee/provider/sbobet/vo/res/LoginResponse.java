package coffee.provider.sbobet.vo.res;

import coffee.provider.sbobet.vo.model.Error;
import lombok.Data;

@Data
public class LoginResponse {
	private String url;
    private String serverId;
    private Error error;
}
