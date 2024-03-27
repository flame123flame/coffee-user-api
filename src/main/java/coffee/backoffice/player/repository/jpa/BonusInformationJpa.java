package coffee.backoffice.player.repository.jpa;

import coffee.backoffice.player.model.BonusInformation;
import coffee.backoffice.player.model.DepositInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusInformationJpa extends CrudRepository<BonusInformation,Long> {
    BonusInformation findByUsername(String username);
}
