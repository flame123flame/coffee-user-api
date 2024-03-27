package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.PromotionMapping;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionMappingRepository extends JpaRepository<PromotionMapping, Long> {

	public PromotionMapping findFirstByUsernameAndStatusAndDateActiveAfterOrderByDateActiveDesc(String username, String status, Date dateActive);
	
	public List<PromotionMapping> findByUsernameAndStatusAndDateActiveBeforeOrderByDateActiveDesc(String username, String status, Date dateActive);
	
	public PromotionMapping findFirstByUsernameAndStatusOrderByUpdatedDateDesc(String username, String status);
	
	public List<PromotionMapping> findByUsername(String username);

	public List<PromotionMapping> findByUsernameAndStatus(String username, String status);

	public PromotionMapping findByUsernameAndStatusAndRequestId(String username, String status, String requestId);

	public PromotionMapping findByUsernameAndPromoCodeAndStatus(String username, String promoCode, String status);

}
