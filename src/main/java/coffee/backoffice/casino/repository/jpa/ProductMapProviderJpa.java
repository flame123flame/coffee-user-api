package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.ProductMappingProvider;

@Repository
public interface ProductMapProviderJpa extends CrudRepository<ProductMappingProvider, Long> {
	List<ProductMappingProvider> findByProviderCode(String pvCode);

	List<ProductMappingProvider> findByProductCode(String pdCode);
	
	List<ProductMappingProvider> findAll();
	
	List<ProductMappingProvider> findByProductCodeAndProviderCode(String product,String provider);
	
	@Query(value = "SELECT provider_code FROM game_product_type_mapping_provider WHERE product_code = ?1" , nativeQuery = true)
	public List<String> findProviderCodeByProductCode(String product);

	public void deleteByProductCode(String code);

	public void deleteByProviderCode(String code);

	public void deleteByCode(String code);
	
}
