package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.PromotionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRequestJpa extends JpaRepository<PromotionRequest,Long> {
	
	public List<PromotionRequest> findAllByOrderByCreatedAtDesc();

    public List<PromotionRequest> findByUsername(String username);
    
    public List<PromotionRequest> findByPromoCodeAndStatusRequest(String promoCode,String status);
    
    public PromotionRequest findByUsernameAndPromoCode(String username,String promoCode);
    
    public List<PromotionRequest> findByUsernameAndPromoCodeAndStatusRequest(String username,String promoCode,String status);
    
    public PromotionRequest findByRequestId(String requestId);

    PromotionRequest findByPromoCode(String promoCode);

    List<PromotionRequest> findAllByPromoCode(String promoCode);

    Boolean existsByPromoCodeAndStatusRequest(String promoCode, String pending);
}
