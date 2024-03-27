package coffee.backoffice.cashback.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CashbackHistoryUserRes {
	private String cashbackTitle;
	private BigDecimal actualCashback;
	private String remark;
	private String createdDate;
}
