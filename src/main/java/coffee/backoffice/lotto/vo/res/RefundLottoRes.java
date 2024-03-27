package coffee.backoffice.lotto.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundLottoRes {
    private BigDecimal money;
    private String status;
}
