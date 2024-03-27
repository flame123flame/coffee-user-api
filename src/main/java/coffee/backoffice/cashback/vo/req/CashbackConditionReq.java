package coffee.backoffice.cashback.vo.req;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.casino.vo.res.GameProviderRes;
import lombok.Data;

@Data
public class CashbackConditionReq {
	private Long id;
	private String code;
	private String cashbackCode;
	private BigDecimal moreThanAmount;
	private BigDecimal cashbackPercent;
	private BigDecimal maxCashbackAmount;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;


}
