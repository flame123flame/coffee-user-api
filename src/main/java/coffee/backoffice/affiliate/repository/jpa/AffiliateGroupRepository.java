package coffee.backoffice.affiliate.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.affiliate.model.AffiliateGroup;

@Repository
public interface AffiliateGroupRepository extends CrudRepository<AffiliateGroup, Long> {
	AffiliateGroup findByAffiliateGroupCode(String code);
	public AffiliateGroup findByAffiliateGroupCodeAndEnable(String code,Boolean enable);
	
	List<AffiliateGroup> findAllByAffiliateGroupCode(String code);
}
