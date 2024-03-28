package framework.batch.job;

import coffee.backoffice.casino.service.ProviderSummaryService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import coffee.backoffice.cashback.service.CalculateCashbackService;
import coffee.backoffice.cashback.service.CashbackBalanceService;
import coffee.backoffice.rebate.service.CalculateRebateService;

@DisallowConcurrentExecution
public class RebateDailyBatchJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(RebateDailyBatchJob.class);

	@Autowired
	private CalculateRebateService calculateRebateService;
	
	@Autowired CalculateCashbackService calculateCashbackService;
	@Autowired
	ProviderSummaryService providerSummaryService;

	@Value("${batch.rebate}")
	private String config;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (config.equals("enable")) {
			try {
				//SET UPDATE BET DAILY ZERO BY PROVIDER
//				providerSummaryService.updateBetDailyZero();
				//AN ACTIVE PROMOTION WILL NOT WORK.
				System.out.println("Run Batech Job Rebate & cashback Calculate Daily!");
//				calculateRebateService.findRebateDaily(false);
//				calculateCashbackService.findCashbackDaily(false);
			} catch (Exception e) {
				log.error("RebateDailyBatchJob", e);
			}
		}
	}
}
