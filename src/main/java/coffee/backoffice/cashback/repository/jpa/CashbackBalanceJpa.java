package coffee.backoffice.cashback.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.cashback.model.CashbackBalance;

@Repository
public interface CashbackBalanceJpa extends CrudRepository<CashbackBalance, Long> {
	CashbackBalance findByUsername(String username);
	
	public void deleteByUsername(String username);
}
