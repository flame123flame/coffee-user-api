package coffee.backoffice.finance.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.GameHistory;

@Repository
public interface GameHistoryRepository extends CrudRepository<GameHistory, Long>{
	
	public GameHistory findByOrderNoAndProvider(String orderNo,String provider);
	
	public GameHistory findFirstByUsernameAndProviderOrderByCreatedDateDesc(String username,String provider);
	
	public GameHistory findFirstByUsernameAndProviderAndGameCodeOrderByCreatedDateDesc(String username,String provider,String gameCode);
	
	public List<GameHistory> findByUsername(String username);
	
	public List<GameHistory> findByUsernameAndCreatedDateAfter(String username,Date createdDate);
	
	public List<GameHistory> findByUsernameAndCreatedDateBefore(String username,Date createdDate);
	
	public List<GameHistory> findByUsernameAndCreatedDateBetween(String username,Date startDate,Date endDate);
	
	public List<GameHistory> findByUsernameAndProviderAndCreatedDateAfter(String username,String provider,Date createdDate);
	
	public List<GameHistory> findByUsernameAndProviderAndCreatedDateBefore(String username,String provider,Date createdDate);
	
	public List<GameHistory> findByUsernameAndProviderAndPlayStatusOrderByCreatedDateDesc(String username,String provider,String playStatus);
	
	public List<GameHistory> findByUsernameAndGameCodeAndProviderAndPlayStatusOrderByCreatedDateDesc(String username,String gameCode,String provider,String playStatus);

}
