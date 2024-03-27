package coffee.backoffice.player.repository.jpa;

import coffee.backoffice.player.model.WithdrawalInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalInformationJpa extends CrudRepository<WithdrawalInformation,Long> {
    WithdrawalInformation findByUsername(String username);
}
