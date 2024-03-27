package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CompanySurplusRes {
	private BigDecimal companyTotal;
	private String summaryDate;
}
