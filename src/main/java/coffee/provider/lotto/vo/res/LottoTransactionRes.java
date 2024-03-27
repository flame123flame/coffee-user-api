package coffee.provider.lotto.vo.res;

import java.util.List;

import coffee.provider.lotto.vo.model.LottoTransaction;
import lombok.Data;

@Data
public class LottoTransactionRes {

	private List<LottoTransaction> data;
}
