package coffee.backoffice.lotto.vo.req;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LottoBuyReq {
    private String username ;
    private String lottoClassCode;
    private String vipCode;
    private BigDecimal commissionPercent;
    private List<PayNumber> payNumber;
    private Integer roundYeeKee;

    @Data
    static public class PayNumber {
        private String lottoKindCode;
        private List<LottoBuy> lottoBuy;
    }

    @Data
    static public class LottoBuy {
        private String lottoNumber;
        private BigDecimal payCost;
        private BigDecimal prize;
        private Boolean confirm;
    }

}
