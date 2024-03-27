package coffee.backoffice.frontend.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.frontend.model.Landing;

@Repository
public interface LandingJpa extends CrudRepository<Landing, Long> {
	List<Landing> findAllByOrderByCreatedDateDesc();
	
	Landing findByConfigPath(String configPath);
}
