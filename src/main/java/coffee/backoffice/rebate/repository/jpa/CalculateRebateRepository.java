package coffee.backoffice.rebate.repository.jpa;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.TransactionGame;

@Repository
public interface CalculateRebateRepository extends CrudRepository<TransactionGame, Long> {
	@Query(value = "SELECT SUM(tg.bet) FROM transaction_game tg where created_date BETWEEN ?1 and ?2 and username = ?3 and game_code = ?4 ", nativeQuery = true)
	BigDecimal totalBetByProviderAndGameCode(String startDate,String endDate,String username, String gameCode);
}
