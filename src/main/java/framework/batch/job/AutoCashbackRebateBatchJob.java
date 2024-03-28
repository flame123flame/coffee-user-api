package framework.batch.job;

import coffee.backoffice.cashback.service.CalculateCashbackService;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.rebate.service.CalculateRebateService;
import framework.batch.config.AutoCashbackRebateBatchJobConfig;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@DisallowConcurrentExecution
public class AutoCashbackRebateBatchJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(AutoCashbackRebateBatchJobConfig.class);

    @Autowired
    private CalculateRebateService calculateRebateService;

    @Autowired
    CalculateCashbackService calculateCashbackService;
    @Autowired
    ProviderSummaryService providerSummaryService;

    @Value("${batch.rebate}")
    private String config;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (config.equals("enable")) {
            try {
//                calculateCashbackService.doCashback(false);
//                calculateRebateService.doRebateWaiting();
            } catch (Exception e) {
                log.error("RebateDailyBatchJob", e);
            }
        }
    }
}
