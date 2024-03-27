package coffee.backoffice.finance.vo.req;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepositReq {
	
	private Long id;
	private String depositOrder;
	private String username;
	private String companyAccountCode;
	private String depositType;
	private BigDecimal amount;
	private String depositDate;
	private String remark;
	private String status;
	private String slip;
}
