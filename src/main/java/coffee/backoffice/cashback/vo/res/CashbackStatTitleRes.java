package coffee.backoffice.cashback.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashbackStatTitleRes {
    private String cashbackTitle;
    private BigDecimal totalLoss;
    private BigDecimal originalCashback;
    private BigDecimal actualCashback;
}
