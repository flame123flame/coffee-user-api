package coffee.backoffice.cashback.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.cashback.model.CashbackHistory;

@Repository
public interface CashbackHistoryRepository extends CrudRepository<CashbackHistory, Long>{

	List<CashbackHistory> findAll();
	List<CashbackHistory> findAllByUsernameOrderByCreatedDateDesc(String username);
	@Query(value = "SELECT TOP 1 * FROM cashback_history rh ORDER BY created_date DESC", nativeQuery = true)
	CashbackHistory findTopOneByCreatedOrderByDesc();
}
