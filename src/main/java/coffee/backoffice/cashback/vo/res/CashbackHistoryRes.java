package coffee.backoffice.cashback.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CashbackHistoryRes {

	private Long id;
	private String cashbackHistoryCode;
	private String cashbackCode; 
	private String cashbackTitle;
	private String username;
	private String groupCode;
	private String groupName;
	private Boolean isAutoCashback;
	private BigDecimal totalLoss;
	private BigDecimal originalCashback;
	private BigDecimal actualCashback;
	private String remark;
	private Boolean status;
	private String createdBy;
	private String createdDate;
}
