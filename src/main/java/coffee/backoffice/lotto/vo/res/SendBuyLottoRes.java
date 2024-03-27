package coffee.backoffice.lotto.vo.res;

import coffee.backoffice.lotto.vo.req.LottoBuyReq;
import lombok.Data;

@Data
public class SendBuyLottoRes {
    private String status;
    private String massage;
    private LottoBuyRes data;
}
