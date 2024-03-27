package framework.batch.job;

import coffee.backoffice.finance.service.BankBotService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@DisallowConcurrentExecution
public class BankBotBatchJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(BankBotBatchJob.class);

	@Autowired
	private BankBotService bankBotService;

	@Value("${batch.bankbot}")
	private String config;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (config.equals("enable")) {
			try {
				log.info("Start BankBotBatchJob");
				bankBotService.logBankBot();
				bankBotService.updateDepositBankBot();
			} catch (Exception e) {
				log.error("BankBotBatchJob", e);
			}
		}
	}
}
