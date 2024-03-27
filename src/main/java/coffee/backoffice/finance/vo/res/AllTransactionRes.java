package coffee.backoffice.finance.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class AllTransactionRes {

	private BigDecimal companyDeposit;
	private Integer countDeposit;
	private Integer peopleDeposit;
	private BigDecimal manualAdd;
	private Integer countManualAdd;
	private Integer peopleManualAdd;
	private BigDecimal promotionBalance;
	private Integer countPromotionBalance;
	private Integer peoplePromotionBalance;
	private BigDecimal promotionBonus;
	private Integer countPromotionBonus;
	private Integer peoplePromotionBonus;
	private BigDecimal withdraw;
	private Integer countWithdraw;
	private Integer peopleWithdraw;
	private BigDecimal withdrawAf;
	private Integer countWithdrawAf;
	private Integer peopleWithdrawAf;
	private BigDecimal manualSub;
	private Integer countManualSub;
	private Integer peopleManualSub;
	private BigDecimal rebate;
	private Integer countRebate;
	private Integer peopleRebate;
	private BigDecimal cashback;
	private Integer countCashback;
	private Integer peopleCashback;
	private BigDecimal total;
	
	
}
