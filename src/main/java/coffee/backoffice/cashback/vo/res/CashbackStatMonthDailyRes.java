package coffee.backoffice.cashback.vo.res;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashbackStatMonthDailyRes {
    private String dateTime;
    private BigDecimal totalLoss;
    private BigDecimal originalCashback;
    private BigDecimal actualCashback;
    private Long count;
}
