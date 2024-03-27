package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import coffee.backoffice.casino.model.GameProductTypeNoIcon;

public interface GameProductTypeNoIconJpa extends CrudRepository<GameProductTypeNoIcon, Long> {
	GameProductTypeNoIcon findByCode(String gameProductTypeCode);

	List<GameProductTypeNoIcon> findAll();
}
