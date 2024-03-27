package coffee.backoffice.lotto.repository.jpa;

import coffee.backoffice.lotto.model.LottoHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LottoHistoryRepository extends CrudRepository<LottoHistory,Long> {
    List<LottoHistory> findAllByUsername(String username);
    List<LottoHistory> findAll();
}
