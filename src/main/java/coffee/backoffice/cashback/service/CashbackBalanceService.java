package coffee.backoffice.cashback.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.cashback.model.CashbackBalance;
import coffee.backoffice.cashback.repository.jpa.CashbackBalanceJpa;
import framework.utils.GenerateRandomString;

@Service
public class CashbackBalanceService {

	@Autowired
	private CashbackBalanceJpa cashbackBalanceJpa;

	public void createCashbackBalance(String username) {
		CashbackBalance tempData = cashbackBalanceJpa.findByUsername(username);
		if (tempData == null) {
			CashbackBalance data = new CashbackBalance();
			data.setCode(GenerateRandomString.generateUUID());
			data.setUsername(username);
			data.setBalance(BigDecimal.ZERO);
			data.setCreatedBy("__system");
			cashbackBalanceJpa.save(data);
		}
	}

	public void updateCashbackBalance(String username, BigDecimal amount) {
		CashbackBalance data = cashbackBalanceJpa.findByUsername(username);
		if(data != null) {
			data.setUsername(username);
			data.setBalance(data.getBalance().add(amount));
			data.setUpdatedBy("__system");
			data.setUpdatedDate(new Date());
			cashbackBalanceJpa.save(data);
		}
	}
	
	public void updateCashback(String username, BigDecimal amount) {
		CashbackBalance data = cashbackBalanceJpa.findByUsername(username);
		if(data != null) {
			data.setUsername(username);
			data.setBalance(amount);
			data.setUpdatedBy("__system");
			data.setUpdatedDate(new Date());
			cashbackBalanceJpa.save(data);
		}
	}

	public void updateCashbackBalanceWithdraw(String username, BigDecimal amount) {
		CashbackBalance data = cashbackBalanceJpa.findByUsername(username);
		if(data != null) {
			data.setUsername(username);
			data.setBalance(data.getBalance().subtract(amount).setScale(BigDecimal.ROUND_HALF_UP));
			data.setUpdatedBy("__system");
			data.setUpdatedDate(new Date());
			cashbackBalanceJpa.save(data);
		}
	}

	public CashbackBalance getCashbackBalanceByUsername(String username) {
		return cashbackBalanceJpa.findByUsername(username);
	}

	@Transactional
	public void deleteCashbackBalance(String username) {
		cashbackBalanceJpa.deleteByUsername(username);
	}

}
