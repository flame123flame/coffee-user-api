package coffee.backoffice.affiliate.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.affiliate.model.AffiliateIncomeChannel;

@Repository
public interface AffiliateIncomeChannelRepository extends CrudRepository<AffiliateIncomeChannel, Long> {
	
	public AffiliateIncomeChannel findByAffiliateChannelCodeAndUsername(String code,String username);
}
