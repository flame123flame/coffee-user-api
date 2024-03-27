package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class CustomerChangeBankReq {
	private String username;
	private String bankCode;
}
