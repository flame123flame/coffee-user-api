package framework.batch.config;

import framework.batch.job.BankBotBatchJob;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

@Configuration
@ConditionalOnProperty(name = "cronExpressions.minute.bankbot", havingValue = "", matchIfMissing = false)
public class BankBotBatchJobConfig {
	private static final Logger log = LoggerFactory.getLogger(BankBotBatchJobConfig.class);

	@Value("${cronExpressions.minute.bankbot}")
	private String cronExpressions;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBeanBankBot(@Qualifier("jobFactory") JobFactory jobFactory,
													 @Qualifier("bankBotJobTrigger") Trigger sampleJobTrigger) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setJobFactory(jobFactory);
		factory.setTriggers(sampleJobTrigger);

		return factory;
	}

	@Bean(name = "jobBankbotDetail")
	public JobDetailFactoryBean jobDetail() {
		return createJobDetail(BankBotBatchJob.class);
	}

	@Bean(name = "bankBotJobTrigger")
	public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("jobBankbotDetail") JobDetail jobDetail) {
		return createCronTrigger(jobDetail, cronExpressions);
	}

	private static JobDetailFactoryBean createJobDetail(Class jobClass) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(true);
		return factoryBean;
	}

	private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		return factoryBean;
	}

}
