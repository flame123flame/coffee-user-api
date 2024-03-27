package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import framework.model.DataTableResponse;
import lombok.Data;

@Data
public class DepositListRes {
	private DataTableResponse<DataLisrRes> dataList;
	private DataSummaryRes summary;
	
	@Data
	public static class DataLisrRes {
	private String depositOrder;
	private String username;
	private String realname;
	private String depositType;
	private String depositRemark;
	private BigDecimal deposit;
	private BigDecimal beforeBalance;
	private BigDecimal afterBalance;
	private String companyBankName;
	private String companyBankCode;
	private String companyAccountName;
	private String companyAccountNumber;
	private String status;
	private String auditor;
	private Date auditorDate;
	private Date depositDate;
	private Date createdDate;
	}
	@Data
	public static class DataSummaryRes {
		private BigDecimal subTotalDeposit = BigDecimal.ZERO;
		private BigDecimal totalDeposit = BigDecimal.ZERO;
	}
}
