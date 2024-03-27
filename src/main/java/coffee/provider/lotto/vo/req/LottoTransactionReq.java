package coffee.provider.lotto.vo.req;

import java.util.Date;
import java.util.List;

import coffee.provider.lotto.vo.model.LottoTransaction;
import lombok.Data;

@Data
public class LottoTransactionReq {

	private Date timeStart;
	private Date timeEnd;
}
