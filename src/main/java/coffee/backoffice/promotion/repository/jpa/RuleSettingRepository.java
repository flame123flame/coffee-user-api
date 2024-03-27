package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.RuleSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleSettingRepository extends JpaRepository<RuleSetting,Long> {

	public RuleSetting findByPromoCode(String promoCode);
}
