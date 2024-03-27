package coffee.backoffice.casino.repository.jpa;

import coffee.backoffice.casino.model.GameTag;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface GameTagJpa extends CrudRepository<GameTag, Long> {
    Optional<GameTag> findByCode(String id);

    List<GameTag> findAll(Sort sort);
}
