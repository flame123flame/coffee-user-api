package coffee.backoffice.cashback.repository.jpa;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.TransactionGame;

@Repository
public interface CalculateCashbackRepository extends CrudRepository<TransactionGame, Long> {

	@Query(value = "SELECT SUM(tg.bet_result) FROM transaction_game tg where created_date BETWEEN ?1 and ?2 and username = ?3 and game_code = ?4 ", nativeQuery = true)
	BigDecimal totalBetResultNegative(String startDate, String endDate, String username, String gameCode);

}
