package coffee.backoffice.finance.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GameTransactionReq {
	
    private String gameCode;
    private String sessionId;
    private String username;
    private String after;

}
