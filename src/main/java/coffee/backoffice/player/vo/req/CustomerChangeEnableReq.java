package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class CustomerChangeEnableReq {
	private String username;
	private Boolean enable;
}
