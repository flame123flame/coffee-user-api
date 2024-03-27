package coffee.backoffice.finance.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.WithdrawCondition;

@Repository
public interface WithdrawConditionJpa extends CrudRepository<WithdrawCondition, Long> {
	public WithdrawCondition findByUsernameAndConditionStatus(String username, String conditionStatus);

    WithdrawCondition findByUsernameAndConditionType(String username, String general);

    WithdrawCondition findByUsernameAndConditionTypeAndConditionStatus(String username, String general, String notPass);
}
