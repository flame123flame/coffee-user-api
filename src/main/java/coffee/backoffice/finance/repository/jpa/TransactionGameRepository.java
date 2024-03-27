package coffee.backoffice.finance.repository.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import coffee.backoffice.player.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.TransactionGame;

@Repository
public interface TransactionGameRepository extends JpaRepository<TransactionGame, Long> {

	public List<TransactionGame> findByUsername(String username);

	public TransactionGame findByGameSessionId(String id);

	public TransactionGame findByGameSessionIdAndGameProvider(String session, String provider);

	public List<TransactionGame> findByUsernameAndGameCode(String username, String gameCode);

	public List<TransactionGame> findByUsernameAndGameProvider(String username, String gameProvider);

	public List<TransactionGame> findByUsernameAndGameProviderAndCreatedDateAfter(String username, String gameProvider, Date createdDate);

	public List<TransactionGame> findByUsernameAndGameProviderAndCreatedDateBetween(String username, String gameProvider, Date startDate, Date endDate);

	public List<TransactionGame> findByUsernameAndCreatedDateBefore(String username, Date createdDate);

	public List<TransactionGame> findByUsernameAndCreatedDateAfter(String username, Date createdDate);

	public List<TransactionGame> findByUsernameAndCreatedDateBetween(String username, Date startDate, Date endDate);

	public List<TransactionGame> findByGameProviderAndCreatedDateBetween(String provider, Date startDate, Date endDate);

	@Query(value = "SELECT SUM(tg.bet) from transaction_game tg where tg.username = ?1", nativeQuery = true)
	public BigDecimal getTotalPlayAmountByUsername(String username);

	@Query(value = "SELECT SUM(tg.bet) from transaction_game tg where created_date BETWEEN ?1 and ?2 and  tg.username = ?3 and tg.game_provider = ?4", nativeQuery = true)
	public BigDecimal totalBetByUsernameAndProvider(String startDate, String endDate, String username, String provider);

	@Query(value = "SELECT SUM(tg.valid_bet) FROM transaction_game tg  WHERE tg.created_date BETWEEN ?1 and ?2  ", nativeQuery = true)
	public BigDecimal findTotalValidBet(Date startDate, Date endDate);

	@Query(value = "SELECT SUM(tg.win_loss) FROM transaction_game tg  WHERE tg.created_date BETWEEN ?1 and ?2  ", nativeQuery = true)
	public BigDecimal findTotalWinLoss(Date startDate, Date endDate);

	@Query(value = "SELECT SUM(tg.bet) from transaction_game tg where tg.created_date BETWEEN ?1 and ?2 and tg.game_provider = ?3", nativeQuery = true)
	public BigDecimal totalBetByProvider(Date startDate, Date endDate, String provider);
	
	@Query(value = "SELECT SUM(tg.win_loss) from transaction_game tg where tg.created_date BETWEEN ?1 and ?2 and tg.game_provider = ?3", nativeQuery = true)
	public BigDecimal totalWinLossByProvider(Date startDate, Date endDate, String provider);	

	@Query(value = "SELECT COUNT(ct.username) AS totalPlayer FROM ( select username FROM transaction_game tg where created_date BETWEEN ?1 AND ?2 AND game_provider = ?3 GROUP BY username ) ct ", nativeQuery = true)
	public Integer countPlayer(Date startDate, Date endDate, String provider);
	
	@Query(value = "SELECT COUNT(game_provider) AS totalTxn from transaction_game tg where created_date BETWEEN ?1 AND ?2 AND game_provider = ?3 ", nativeQuery = true)
	public Integer countTxn(Date startDate, Date endDate, String provider);

	List<TransactionGame> findByUsernameAndGameCodeIn(String customer, List<String> gamesCodes);
	List<TransactionGame> findByUsernameAndGameCodeInAndCreatedDateBetween(String customer, List<String> gamesCodes , Date date1 , Date date2);
}
