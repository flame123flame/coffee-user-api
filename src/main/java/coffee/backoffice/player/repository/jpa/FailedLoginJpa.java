package coffee.backoffice.player.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.model.FailedLogin;

@Repository
public interface FailedLoginJpa extends CrudRepository<FailedLogin, Long>{
	
	public FailedLogin findById(String id);
	public FailedLogin findByFailedLoginCode(String faliedLoginCode);
	public void deleteByFailedLoginCode(String faliedLoginCode);
	public FailedLogin findByUsername(String username);
	
	@Query(value = "SELECT TOP 1 fl.* FROM failed_login fl WHERE fl.username = ?1 AND fl.remark = ?2 ORDER BY fl.created_date DESC" , nativeQuery = true)
	public FailedLogin findTop1ByUsernameAndRemarkOrderByCreatedDateDesc(String username, String remark);
}
