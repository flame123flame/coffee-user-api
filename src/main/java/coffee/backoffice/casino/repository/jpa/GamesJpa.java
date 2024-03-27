package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.Games;

@Repository
public interface GamesJpa extends CrudRepository<Games, Long> {
	List<Games> findAll();

	List<Games> findAllByGameProductTypeCode(String code);

//	Games findByCode(String code);

	Games findByNameEn(String code);

	Games findByGameProductTypeCode(String groupCode);

	Games findByGameCode(String code);

	public List<Games> findByProviderCodeAndStatus(String providerCode,Boolean status);
	
	public void deleteByGameCode(String gameCode);
	
	@Query(value = "SELECT game_code FROM games WHERE game_group_code IN ( ?1 ) " , nativeQuery = true)
	public List<String> findGameCodeByGameGroupCodeIn(List<String> gameGroupCode);


    List<Games> findAllByProviderCodeAndStatus(String providerCode, Boolean status);

    @Query(value="select count(1) from games as tb where tb.provider_code = ?1",nativeQuery = true)
    Long countAllByProviderCode(String code);
}
