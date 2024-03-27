package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingRepository extends JpaRepository<AppSetting,Long> {
	
	public AppSetting findByPromoCode(String promoCode);

}
