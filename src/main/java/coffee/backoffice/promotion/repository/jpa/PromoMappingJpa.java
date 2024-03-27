package coffee.backoffice.promotion.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.promotion.model.PromotionMapping;

import java.util.List;

@Repository
public interface PromoMappingJpa extends CrudRepository<PromotionMapping, Long>{
	PromotionMapping findByUsernameAndStatus(String username,String status);

    PromotionMapping findByPromoCode(String promoCode);

    List<PromotionMapping> findAllByPromoCode(String promoCode);
}
