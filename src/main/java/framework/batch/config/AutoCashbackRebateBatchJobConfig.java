package framework.batch.config;

import framework.batch.job.AutoCashbackRebateBatchJob;
import framework.batch.job.TransactionBatchJob;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

@Configuration
@ConditionalOnProperty(name = "cronExpressions.daily.dailyNineAm", havingValue = "", matchIfMissing = false)
public class AutoCashbackRebateBatchJobConfig {
    private static final Logger log = LoggerFactory.getLogger(AutoCashbackRebateBatchJobConfig.class);

    @Value("${cronExpressions.daily.dailyNineAm}")
    private String cronExpressions;

    @Bean(name = "jobAutoCashbackRebateFactory")
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean jobAutoCashbackRebateDetail(@Qualifier("jobAutoCashbackRebateFactory") JobFactory jobFactory,
                                                            @Qualifier("jobAutoCashbackRebateJobTrigger") Trigger jobAutoCashbackRebateJobTrigger) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        factory.setTriggers(jobAutoCashbackRebateJobTrigger);

        return factory;
    }

    @Bean(name = "jobAutoCashbackRebateDetail")
    public JobDetailFactoryBean jobAutoCashbackRebateDetail() {
        return createJobDetail(AutoCashbackRebateBatchJob.class);
    }

    @Bean(name = "jobAutoCashbackRebateJobTrigger")
    public CronTriggerFactoryBean jobAutoCashbackRebateJobTrigger(@Qualifier("jobAutoCashbackRebateDetail") JobDetail jobDetail) {
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
