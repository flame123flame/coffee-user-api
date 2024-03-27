package framework.batch.config;

import framework.batch.job.RebateDailyBatchJob;
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
@ConditionalOnProperty(name = "cronExpressions.daily.rebateDaily", havingValue = "", matchIfMissing = false)
public class RebateDailyBatchJobConfig {
	private static final Logger log = LoggerFactory.getLogger(RebateDailyBatchJobConfig.class);

	@Value("${cronExpressions.daily.rebateDaily}")
	private String cronExpressions;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBeanRebate(@Qualifier("jobFactory")JobFactory jobFactory,
			@Qualifier("rebateJobTrigger") Trigger rebateJobTrigger) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setJobFactory(jobFactory);
		factory.setTriggers(rebateJobTrigger);

		return factory;
	}

	@Bean(name = "jobRebateDetail")
	public JobDetailFactoryBean jobRebateDetail() {
		return createJobDetail(RebateDailyBatchJob.class);
	}

	@Bean(name = "rebateJobTrigger")
	public CronTriggerFactoryBean rebateJobTrigger(@Qualifier("jobRebateDetail") JobDetail jobRebateDetail) {
		return createCronTrigger(jobRebateDetail, cronExpressions);
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
