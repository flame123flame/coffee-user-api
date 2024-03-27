package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PlayerValidBetProductRes {
	private String productNameEn;
	private String productNameTh;
	private String productCode;	
	private BigDecimal validBet;
}
