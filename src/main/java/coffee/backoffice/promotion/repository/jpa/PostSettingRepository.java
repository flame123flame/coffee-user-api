package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.PostSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSettingRepository extends JpaRepository<PostSetting,Long> {
	
	public PostSetting findByPromoCode(String promoCode);

}
