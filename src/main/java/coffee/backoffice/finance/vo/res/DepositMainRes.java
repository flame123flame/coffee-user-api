package coffee.backoffice.finance.vo.res;

import java.util.Date;

import lombok.Data;

@Data
public class DepositMainRes {
	
	private String depositOrder;
	private String username;
	private String realname;
	private String depositType;
	private String depositRemark;
	private String amount;
	private String companyBankCode;
	private String companyAccountName;
	private String systemType;
	private String status;
	private String auditor;
	private Date auditorDate;
	private Date depositDate;
	private Date createdDate;
}
