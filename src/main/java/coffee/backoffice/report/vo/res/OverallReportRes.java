package coffee.backoffice.report.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OverallReportRes {
	BigDecimal companytotal;
	BigDecimal companytotalgr;
	BigDecimal deposit;
	BigDecimal depositgr;
	Integer registercount;
	BigDecimal registercountgr;
	String summarydate;
	BigDecimal totalwinloss;
	BigDecimal totalwinlossgr;
	BigDecimal validbet;
	BigDecimal validbetgr;
	BigDecimal withdraw;
	BigDecimal withdrawgr;
}
