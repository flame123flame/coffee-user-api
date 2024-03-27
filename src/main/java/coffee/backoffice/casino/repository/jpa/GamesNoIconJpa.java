package coffee.backoffice.casino.repository.jpa;

import java.util.List;

import coffee.backoffice.casino.model.Games;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.GamesNoIcon;

@Repository
public interface GamesNoIconJpa extends CrudRepository<GamesNoIcon, Long> {
	List<GamesNoIcon> findAll();

    List<GamesNoIcon> findAllByProviderCodeAndStatus(String providerCode, boolean b);

    List<GamesNoIcon> findAllByProviderCodeAndStatusAndGameGroupCodeIsNotNull(String providerCode, boolean b);

    List<GamesNoIcon> findAllByProviderCodeAndGameGroupCode(String gameProviderCode, String gameGroupCode);

    List<GamesNoIcon> findAllByProviderCodeAndGameGroupCodeAndGameCodeNotIn(String gameProviderCode, String gameGroupCode , List<String> codeList);
}
