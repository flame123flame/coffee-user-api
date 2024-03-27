package coffee.provider.lotto.vo.req;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LottoPayCostReq {
	private String lottoGroupTransactionCode;
	private BigDecimal payCost;
	private String username;
	private String installment;
	private Integer roundYeekee;
	private String lottoClassCode;
}
