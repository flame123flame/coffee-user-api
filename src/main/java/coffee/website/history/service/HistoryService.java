package coffee.website.history.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.GameHistory;
import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.GameHistoryService;
import coffee.backoffice.rebate.service.RebateHistoryService;
import coffee.website.history.vo.req.HistoryRequest;
import coffee.website.history.vo.res.HistoryResponse;
import framework.constant.ProjectConstant.HISTORY_PERIOD;
import framework.constant.ProjectConstant.HISTORY_TYPE;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.utils.ConvertDateUtils;

@Service
public class HistoryService {

	@Autowired
	private AllTransactionService allTransactionService;
	
	@Autowired
	private GameHistoryService gameHistoryService;
	
	@Autowired
	private RebateHistoryService rebateHistoryService;
	
	public List<HistoryResponse> getHistoryPlayer(HistoryRequest req){
		List<HistoryResponse> list = new ArrayList<HistoryResponse>();
		List<GameHistory> transactionGameList = new ArrayList<GameHistory>();
		List<TransactionGame> transactionLottoList = new ArrayList<TransactionGame>();
		List<TransactionList> transactionDepositList = new ArrayList<TransactionList>();
		List<TransactionList> transactionWithdrawList = new ArrayList<TransactionList>();
		List<TransactionList> transactionRebeteList = new ArrayList<TransactionList>();
		List<TransactionList> transactionPromotionList = new ArrayList<TransactionList>();
		HistoryResponse history = null;
		
		Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.set(Calendar.HOUR_OF_DAY, 00);
    	calendar.set(Calendar.MINUTE, 00);
    	calendar.set(Calendar.SECOND, 00);
    	Date end = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		Date start = calendar.getTime();
		
    	if(HISTORY_PERIOD.CURRENT.equals(req.getPeriod())) {
//    		transactionGameList = allTransactionService.getGameTransactionByUsernameAfter(req.getUsername(), end);
    		transactionGameList = gameHistoryService.getGameHistoryUsernameAfter(req.getUsername(), end);
    		transactionLottoList = allTransactionService.getGameTransactionByUsernameAndGameProviderAfer(req.getUsername(), PROVIDERS.LOTTO ,end);
    		transactionDepositList = allTransactionService.getTransactionByUsernameAndTypeAfter(req.getUsername(),TRANSACTION_TYPE.DEPOSIT, end);
    		transactionWithdrawList = allTransactionService.getTransactionByUsernameAndTypeAfter(req.getUsername(),TRANSACTION_TYPE.WITHDRAW, end);
    		transactionRebeteList = allTransactionService.getTransactionByUsernameAndTypeAfter(req.getUsername(),TRANSACTION_TYPE.REBATE, end);
    		transactionPromotionList.addAll(allTransactionService.getTransactionByUsernameAndTypeAfter(req.getUsername(),TRANSACTION_TYPE.PROMOTION_BALANCE, end));
    		transactionPromotionList.addAll(allTransactionService.getTransactionByUsernameAndTypeAfter(req.getUsername(),TRANSACTION_TYPE.PROMOTION_BONUS, end));
    		
    	}else {
//    		transactionGameList = allTransactionService.getGameTransactionByUsernameBetween(req.getUsername(),start,end);
    		transactionGameList = gameHistoryService.getGameHistoryUsernameBetween(req.getUsername(), start, end);
    		transactionLottoList = allTransactionService.getGameTransactionByUsernameAndGameProviderBetween(req.getUsername(), PROVIDERS.LOTTO ,start,end);
    		transactionDepositList = allTransactionService.getTransactionByUsernameAndTypeBetween(req.getUsername(),TRANSACTION_TYPE.DEPOSIT,start, end);
    		transactionWithdrawList = allTransactionService.getTransactionByUsernameAndTypeBetween(req.getUsername(),TRANSACTION_TYPE.WITHDRAW, start, end);
    		transactionRebeteList = allTransactionService.getTransactionByUsernameAndTypeBetween(req.getUsername(),TRANSACTION_TYPE.REBATE, start, end);
    		transactionPromotionList.addAll(allTransactionService.getTransactionByUsernameAndTypeBetween(req.getUsername(),TRANSACTION_TYPE.PROMOTION_BALANCE, start, end));
    		transactionPromotionList.addAll(allTransactionService.getTransactionByUsernameAndTypeBetween(req.getUsername(),TRANSACTION_TYPE.PROMOTION_BONUS, start, end));
    	
    	}
    	
    	// set Game
    	for(GameHistory temp :transactionGameList) {
    		if(BigDecimal.ZERO.compareTo(temp.getCreditResult()) == 0) {
    			continue;
    		}
    		history = new HistoryResponse();
    		if(temp.getTransactionResult() != null) {
    			if(HISTORY_TYPE.WIN.equalsIgnoreCase(temp.getTransactionResult())) {
    				history.setHistoryType(HISTORY_TYPE.WIN);
    			}
    			if(HISTORY_TYPE.DRAW.equalsIgnoreCase(temp.getTransactionResult())) {
    				history.setHistoryType(HISTORY_TYPE.DRAW);
    			}
    			if(HISTORY_TYPE.LOSE.equalsIgnoreCase(temp.getTransactionResult())) {
    				history.setHistoryType(HISTORY_TYPE.LOSE);
    			}
    			if(HISTORY_TYPE.RUNNING.equalsIgnoreCase(temp.getTransactionResult())) {
    				history.setHistoryType(HISTORY_TYPE.RUNNING);
    			}
    		}else {
    			if(BigDecimal.ZERO.compareTo(temp.getCreditResult()) < 0) {
    				history.setHistoryType(HISTORY_TYPE.WIN);
    			}else {
    				history.setHistoryType(HISTORY_TYPE.LOSE);
    			}
    		}
			
			history.setOrderId(temp.getOrderNo());
			history.setTransactionDate(ConvertDateUtils.formatDateToString(temp.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
			history.setCredit(temp.getCreditResult().toString());
			history.setBalanceCredit(temp.getCreditOut().toString());
			if(!PROVIDERS.SBO.equals(temp.getProvider())) {
				history.setRemark("เล่นเกมส์ "+temp.getGameName()+" | ค่าย : "+temp.getProvider());
			}else {
				history.setRemark("แทงคู่ "+temp.getGameName()+" | ผล : "+temp.getRemark());
			}
			list.add(history);
		}
    	
    	// set Deposit
    	for(TransactionList temp :transactionDepositList) {
			history = new HistoryResponse();
			history.setHistoryType(HISTORY_TYPE.DEPOSIT);
			history.setOrderId(temp.getTransactionId());
			history.setTransactionDate(ConvertDateUtils.formatDateToString(temp.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
			history.setCredit(temp.getAddBalance().toString());
			history.setBalanceCredit(temp.getAfterBalance().toString());
			history.setRemark("ฝากเงิน");
			list.add(history);
		}
    	
    	// set Promotion
    	for(TransactionList temp :transactionPromotionList) {
    		history = new HistoryResponse();
			history.setHistoryType(HISTORY_TYPE.PROMOTION);
			history.setOrderId(temp.getTransactionId());
			history.setTransactionDate(ConvertDateUtils.formatDateToString(temp.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
			history.setCredit(temp.getAddBalance().toString());
			history.setBalanceCredit(temp.getAfterBalance().toString());
			history.setRemark("ยอดโปรโมชั่น "+temp.getTransactionType());
			list.add(history);
    	}
    	
    	// set Withdraw
    	for(TransactionList temp :transactionWithdrawList) {
    		history = new HistoryResponse();
			history.setHistoryType(HISTORY_TYPE.WITHDRAW);
			history.setOrderId(temp.getTransactionId());
			history.setTransactionDate(ConvertDateUtils.formatDateToString(temp.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
			history.setCredit(temp.getSubBalance().toString());
			history.setBalanceCredit(temp.getAfterBalance().toString());
			history.setRemark("ถอนเงิน");
			list.add(history);
    	}
    	
    	// set Withdraw
    	for(TransactionList temp :transactionRebeteList) {
    		history = new HistoryResponse();
			history.setHistoryType(HISTORY_TYPE.REBATE);
			history.setOrderId(temp.getTransactionId());
			history.setTransactionDate(ConvertDateUtils.formatDateToString(temp.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
			history.setCredit(temp.getAddBalance().toString());
			history.setBalanceCredit(temp.getAfterBalance().toString());
			history.setRemark("คืนเงิน");
			list.add(history);
    	}
    	
    	for(TransactionGame temp :transactionLottoList) {
    		history = new HistoryResponse();
    		if(temp.getWinLoss() != null) {
    			if(BigDecimal.ZERO.compareTo(temp.getWinLoss()) > 0) {
    				history.setHistoryType(HISTORY_TYPE.WIN);
    			}else {
    				history.setHistoryType(HISTORY_TYPE.LOSE);
    			}
    		}else {
    			history.setHistoryType(HISTORY_TYPE.LOSE);
    		}
    		
			history.setOrderId(temp.getGameSessionId());
			history.setTransactionDate(ConvertDateUtils.formatDateToString(temp.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
			history.setCredit(temp.getBetResult().subtract(temp.getBet()).toString());
			history.setBalanceCredit(temp.getBalance().toString());
			history.setRemark("แทงหวย");
			list.add(history);
    	}
    	
		return list;
	}
}
