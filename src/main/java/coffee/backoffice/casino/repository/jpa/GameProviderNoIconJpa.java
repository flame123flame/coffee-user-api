package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.GameProviderNoIcon;

@Repository
public interface GameProviderNoIconJpa extends CrudRepository<GameProviderNoIcon,Long> {
    GameProviderNoIcon findByCode(String code);
    List<GameProviderNoIcon> findAll();
}
