package coffee.backoffice.rebate.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RebateStatTitleRes {
    private String title;
    private BigDecimal validBets;
    private BigDecimal originalRebate;
    private BigDecimal actualRebate;
}
