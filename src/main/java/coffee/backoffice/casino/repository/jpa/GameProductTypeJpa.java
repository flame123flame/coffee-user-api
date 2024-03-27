package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.GameProductType;

@Repository
public interface GameProductTypeJpa extends JpaRepository<GameProductType, Long> {
	
	List<GameProductType> findAll();
	
	List<GameProductType> findAllByOrderByCreatedAtAsc();

	GameProductType findByCode(String code);

	GameProductType findByNameEn(String code);

	public void deleteByCode(String code);
}
