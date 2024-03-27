package coffee.backoffice.rebate.vo.res;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RebateStatMonthDailyRes {

    private String dateTime;
    private BigDecimal validBets;
    private BigDecimal originalRebate;
    private BigDecimal actualRebate;
    private Long count;
}
