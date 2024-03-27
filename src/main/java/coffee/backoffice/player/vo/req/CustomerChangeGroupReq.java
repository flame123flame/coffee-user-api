package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class CustomerChangeGroupReq {
	private String username;
	private String groupCode;
}
