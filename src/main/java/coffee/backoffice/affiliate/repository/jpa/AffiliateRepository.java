package coffee.backoffice.affiliate.repository.jpa;

import coffee.backoffice.affiliate.model.Affiliate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate,Long> {
    Affiliate findByAffiliateCode(String affiliateCode);
    
}
