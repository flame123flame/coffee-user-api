package coffee.backoffice.player.vo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TurnOverDetail {
	
	private String conditionType;
	private BigDecimal targetTurnOver;
	private BigDecimal currentTurnOver;
	private String wording;
	private BigDecimal withdrawAmount;

}
