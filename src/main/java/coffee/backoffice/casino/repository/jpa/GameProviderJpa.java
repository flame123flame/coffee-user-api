package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.GameProvider;

@Repository
public interface GameProviderJpa extends CrudRepository<GameProvider, Long> {
	List<GameProvider> findAll();

	GameProvider findByCode(String code);

	GameProvider findByNameEn(String code);

	public void deleteByCode(String code);

}
