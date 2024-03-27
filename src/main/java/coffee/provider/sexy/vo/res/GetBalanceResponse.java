package coffee.provider.sexy.vo.res;

import coffee.provider.sexy.vo.model.ResultsBalanace;
import lombok.Data;

@Data
public class GetBalanceResponse {

	private String status;
	private String count;
	private String querytime;
	private ResultsBalanace results;
}
