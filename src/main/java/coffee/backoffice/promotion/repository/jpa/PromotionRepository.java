package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.Promotion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
	
	public Promotion findByPromoCode(String promoCode);
	
	public List<Promotion> findByPromoTypeAndPromoCode(String promoType,String promoCode);
	
	public List<Promotion> findByPromoTypeAndPromoCodeIn(String promoType,List<String> promoCode);

	public List<Promotion> findByPromoType(String promoType);

    List<Promotion> findAllByViewStatus(String show);
}
