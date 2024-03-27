package coffee.backoffice.finance.vo.res;

import coffee.backoffice.player.vo.res.CustomerRes;
import lombok.Data;

@Data
public class DepositDetailRes {
	
	private Long id;
	private String depositOrder;

	private CustomerRes customerRes;

	
	private String companyBankCode;
	private String companyBankAccount;
	private String companyAccountName;
	
	private String slip;
	private String depositRemark;
}
