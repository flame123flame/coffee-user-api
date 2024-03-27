package coffee.backoffice.player.vo.req;

import lombok.Data;

@Data
public class CustomerChangeBankAccountReq {
	private String username;
	private String bankAccount;
}
