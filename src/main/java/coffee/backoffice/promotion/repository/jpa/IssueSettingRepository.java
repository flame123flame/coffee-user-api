package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.IssueSetting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueSettingRepository extends JpaRepository<IssueSetting,Long> {
	
	public List<IssueSetting> findByPromoCode(String promoCode);
	List<IssueSetting> findAllByPromoCode(String promoCode);

}
