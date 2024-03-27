package coffee.provider.joker.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TransactionGameListRes {
	private String OCode;
	private String Username;
	private String GameCode;
	private String Description;
	private String RoundID;
	private BigDecimal Amount;
	private BigDecimal FreeAmount;
	private BigDecimal Result;
	private Date Time;
	private String Details;
	private String AppID;
	private String CurrencyCode;
	private String Type;
	private String TransactionOCode;
}
