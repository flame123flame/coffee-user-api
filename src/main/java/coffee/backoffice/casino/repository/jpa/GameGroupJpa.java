package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.GameGroup;

@Repository
public interface GameGroupJpa extends CrudRepository<GameGroup, Long> {
	List<GameGroup> findAll();

	GameGroup findByCode(String code);

	GameGroup findByNameEn(String code);

	public void deleteByCode(String code);
}
