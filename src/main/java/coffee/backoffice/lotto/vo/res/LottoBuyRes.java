package coffee.backoffice.lotto.vo.res;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LottoBuyRes {
    private String status;
    private String code;
    private String installment;
    private List<ErrorBuyDetail> error;
    private List<ErrorBuyDetail> successData;

    @Data
    public static class ErrorBuyDetail {
        private String lottoKindCode;
        private String statusKind = "SUCCESS";
        private List<LottoBuyDetailRes> lottoBuy;
    }

    @Data
    public static class LottoBuyDetailRes {
        private String lottoNumber;
        private String status;
        private BigDecimal newPrize;
        private BigDecimal oldPrize;
        private BigDecimal payCost;
    }
}
