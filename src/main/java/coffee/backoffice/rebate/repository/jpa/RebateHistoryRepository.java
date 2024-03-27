package coffee.backoffice.rebate.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.rebate.model.RebateHistory;

@Repository
public interface RebateHistoryRepository extends CrudRepository<RebateHistory, Long>{
	
	public List<RebateHistory> findByUsername(String username);
	
	public List<RebateHistory> findByUsernameAndCreatedDateAfter(String username,Date createdDate);
	
	public List<RebateHistory> findByUsernameAndCreatedDateBefore(String username,Date createdDate);
	
	public List<RebateHistory> findByUsernameAndCreatedDateBetween(String username,Date startDate,Date endDate);
	
	@Query(value = "SELECT TOP 1 * FROM rebate_history rh ORDER BY created_date DESC", nativeQuery = true)
	RebateHistory findTopOneByCreatedOrderByDesc();

}
