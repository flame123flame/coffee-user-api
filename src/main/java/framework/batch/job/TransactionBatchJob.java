package framework.batch.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import coffee.provider.joker.service.JokerTransactionsService;
import coffee.provider.lotto.service.LottoProviderService;
import coffee.provider.sa.service.SaGamingTransactionService;
import coffee.provider.sbobet.service.SboBetTransactionService;
import coffee.provider.sexy.service.MxTransactionService;

@DisallowConcurrentExecution
public class TransactionBatchJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionBatchJob.class);
	
	@Autowired
	private JokerTransactionsService jokerTransactionsService;
	
	@Autowired
	private MxTransactionService mxTransactionService;
	
	@Autowired
	private SboBetTransactionService sboBetTransactionService;
	
	@Autowired
	private SaGamingTransactionService saGamingTransactionService;
	
	@Autowired
	private LottoProviderService lottoProviderService;
	
	@Value("${batch.transaction}")
	private String config;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(config.equals("enable")) {
			try {
				System.out.println("Batch updateTransactionProvider job!");
//				jokerTransactionsService.updatedTransaction();
//				mxTransactionService.updatedTransaction();
//				mxTransactionService.updatedTransactionJili();
//				mxTransactionService.updatedTransactionKm();
//				mxTransactionService.updatedTransactionPg();
//				mxTransactionService.updatedTransactionRt();
//				sboBetTransactionService.updatedTransaction();
//				saGamingTransactionService.updatedTransaction();
//				lottoProviderService.getLottoTransactionByUpdateDate();
			}catch (Exception e) {
				log.error("TransactionBatchJob" , e);
			}
		}
	}

}
