package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import framework.model.DataTableResponse;
import lombok.Data;

@Data
public class WithdrawListRes {
	private DataTableResponse<DataLisrRes> dataList;
	private DataSummaryRes summary;

	@Data
	public static class DataLisrRes {
		private Long id;
		private Date createdDate;
		private String orderWithdraw;
		private String username;
		private String realName;
		private String bankAccount;
		private String bankCode;
		private String bankName;
		private BigDecimal amount;
		private BigDecimal beforeBalance;
		private BigDecimal afterBalance;
		private String companyBankName;
		private String companyBankCode;
		private String companyAccountName;
		private String companyAccountNumber;
		private Date withdrawDate;
		private String withdrawStatus;
		private String createdBy;
		private String updatedBy;
		private Date updatedDate;
		private String adminRemark;
		private String userRemark;
	}

	@Data
	public static class DataSummaryRes {
		private BigDecimal subTotalWithdraw = BigDecimal.ZERO;
		private BigDecimal totalWithdraw = BigDecimal.ZERO;
	}
}
