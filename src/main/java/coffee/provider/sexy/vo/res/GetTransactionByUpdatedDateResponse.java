package coffee.provider.sexy.vo.res;

import java.util.List;

import coffee.provider.sexy.vo.model.Transaction;
import lombok.Data;

@Data
public class GetTransactionByUpdatedDateResponse {

	private String status;
	private List<Transaction> transactions;
}
