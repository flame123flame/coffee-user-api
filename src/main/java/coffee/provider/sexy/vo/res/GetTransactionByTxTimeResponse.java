package coffee.provider.sexy.vo.res;

import coffee.provider.sexy.vo.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class GetTransactionByTxTimeResponse {

	private String status;
	private List<Transaction> transactions;
}
