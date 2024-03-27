package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class CustomerChangeAccountNameReq {
	private String username;
	private String accountName;
}
