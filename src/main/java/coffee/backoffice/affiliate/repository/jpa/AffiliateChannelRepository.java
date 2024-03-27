package coffee.backoffice.affiliate.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.affiliate.model.AffiliateChannel;
import coffee.backoffice.player.model.Customer;

@Repository
public interface AffiliateChannelRepository extends CrudRepository<AffiliateChannel, Long> {
	
	List<AffiliateChannel> findAll();
	List<AffiliateChannel> findAllByProductTypeCode(String productTypeCode);
	AffiliateChannel findByAffiliateChannelCode(String code);
	AffiliateChannel findByAffiliateChannelCodeAndEnable(String code,Boolean enable);
	AffiliateChannel findByAffiliateGroupCodeAndEnable(String code,Boolean enable);
	List<AffiliateChannel> findAllByAffiliateGroupCodeAndEnable(String code,Boolean enable);
	public void deleteByAffiliateChannelCode(String code);
}
