package coffee.backoffice.player.repository.jpa;

import coffee.backoffice.player.model.DepositInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositInformationJpa extends CrudRepository<DepositInformation,Long> {
    DepositInformation findByUsername(String username);
}
