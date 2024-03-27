package coffee.backoffice.finance.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.WithdrawCondition;

@Repository
public interface WithdrawConditionRepository extends CrudRepository<WithdrawCondition, Long>{
	public List<WithdrawCondition> findByUsername(String username);
	public List<WithdrawCondition> findByUsernameAndConditionStatus(String username,String conditionStatus);
	public List<WithdrawCondition> findByUsernameAndPromoCode(String username,String promoCode);
	public WithdrawCondition findByUsernameAndConditionStatusAndPromoCode(String username,String conditionStatus,String promoCode);
	public WithdrawCondition findByUsernameAndConditionTypeAndConditionStatus(String username,String conditionType,String conditionStatus);
	
}
