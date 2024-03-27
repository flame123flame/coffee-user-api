package coffee.website.affiliate.vo.model;

import java.math.BigDecimal;
import java.util.List;

import coffee.backoffice.finance.model.TransactionList;
import lombok.Data;

@Data
public class WithdrawDetail {

	private BigDecimal totalIncome;
	private BigDecimal income;
	private List<TransactionList> listHistory;
}
