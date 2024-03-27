package coffee.backoffice.lotto.repository.jpa;

import coffee.backoffice.lotto.model.LottoGroupNumber;
import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LottoGroupNumberRepository extends CrudRepository<LottoGroupNumber , Long> {
    List<LottoGroupNumber> findAllByOrderByCreatedDateDesc();
    List<LottoGroupNumber> findAllByUsernameOwnerOrUsernameOwnerOrderByCreatedDateDesc(String username,String userAdmin);
}
