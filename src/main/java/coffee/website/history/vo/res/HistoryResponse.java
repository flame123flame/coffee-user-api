package coffee.website.history.vo.res;

import java.util.Date;

import framework.utils.ConvertDateUtils;
import lombok.Data;

@Data
public class HistoryResponse implements Comparable<HistoryResponse>{

	private String orderId;
	private String historyType;
	private String transactionDate;
	private String credit;
	private String balanceCredit;
	private String remark;
	
	
	public Date getDateTime() {
		Date newForm = ConvertDateUtils.parseStringToDate(getTransactionDate(), ConvertDateUtils.YYYYMMDD);
	    return newForm;
	}
	
	@Override
	public int compareTo(HistoryResponse o) {
		if (getDateTime() == null || o.getDateTime() == null)
			return 0;
	    return getDateTime().compareTo(o.getDateTime());
	}
}
