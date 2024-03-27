package coffee.backoffice.finance.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.Deposit;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long>{
	public Deposit findByDepositOrderAndUsername(String depositOrder,String username);
	public Deposit findByDepositOrder(String depositOrder);
	public Deposit findByUsernameAndDepositRemark(String username,String depositRemark);
	public Deposit findFirstBySystemTypeOrderByCreatedDateDesc(String systemType);
	
	public List<Deposit> findByUsername(String username);
	public List<Deposit> findByUsernameAndCreatedDateBefore(String username,Date createdDate);
	public List<Deposit> findByUsernameAndCreatedDateAfter(String username,Date createdDate);
	public List<Deposit> findByUsernameAndCreatedDateBetween(String username,Date startDate,Date endDate);
	public List<Deposit> findByUsernameAndStatusOrderByCreatedDateDesc(String username,String status);
}
