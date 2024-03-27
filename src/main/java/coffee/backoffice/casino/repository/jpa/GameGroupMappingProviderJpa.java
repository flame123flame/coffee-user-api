package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.GameGroupMappingProvider;

@Repository
public interface GameGroupMappingProviderJpa extends CrudRepository<GameGroupMappingProvider, Long> {

	List<GameGroupMappingProvider> findByGameGroupCode(String code);

	List<GameGroupMappingProvider> findByProviderCode(String code);

	List<GameGroupMappingProvider> findByGameGroupCodeAndProviderCode(String group, String provider);

	public void deleteByGameGroupCode(String code);

	public void deleteByProviderCode(String code);
	
	List<GameGroupMappingProvider> findAllByProviderCode(String code);

	@Query(value = "SELECT game_group_code FROM game_group_mapping_provider WHERE provider_code IN ( ?1 ) " , nativeQuery = true)
    List<String> findGameGroupCodeByProviderCodeIn(List<String> providersCode);
}
