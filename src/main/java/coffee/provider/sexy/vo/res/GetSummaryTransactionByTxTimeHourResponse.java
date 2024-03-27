package coffee.provider.sexy.vo.res;

import coffee.provider.sexy.vo.model.SummaryTransaction;
import coffee.provider.sexy.vo.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class GetSummaryTransactionByTxTimeHourResponse {

	private String status;
	private SummaryTransaction transactions;
}
