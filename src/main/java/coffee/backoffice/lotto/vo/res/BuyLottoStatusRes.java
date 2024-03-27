package coffee.backoffice.lotto.vo.res;

import lombok.Data;

import java.util.List;

@Data
public class BuyLottoStatusRes {
	private Boolean outOffBalance = false;
	private Boolean serverBuyFail = false;
	private String message;
	private List<LottoBuyRes.ErrorBuyDetail> error;
	private List<LottoBuyRes.ErrorBuyDetail> successData;
	private Boolean success = false;
	private String lottoTransactionGroupCode;

}
