package coffee.backoffice.affiliate.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import coffee.backoffice.affiliate.model.AffiliateNetwork;

@Repository
public interface AffiliateNetworkRepository extends JpaRepository<AffiliateNetwork,Long> {
	
	public AffiliateNetwork findByAffiliateCode(String affiliateCode);
	
	public List<AffiliateNetwork> findByAffiliateCodeUp(String affiliateCodeUp);
	
	public AffiliateNetwork findByUsername(String username);
	
	public Integer countByAffiliateCodeUp(String code);
	
	public List<AffiliateNetwork> findByUsernameStartsWith(String username);
	
	public List<AffiliateNetwork> findByRegisterDateBetween(Date sd,Date ed);
	
	@Query(value = "SELECT COUNT(an.username) FROM affiliate_network an  where an.register_date BETWEEN ?1 and ?2 ", nativeQuery = true)
	public Integer findTotalRegPlayers(Date startDate, Date endDate);
	
}
