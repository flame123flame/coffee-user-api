package coffee.backoffice.lotto.repository.jpa;

import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LottoGroupNumberChildRepository extends CrudRepository<LottoGroupNumberChild,Long> {
    List<LottoGroupNumberChild> findAllByLottoGroupNumberCode(String code);

    @Query(value = "select count(1) from lotto_group_number_child lgnc where lgnc.lotto_group_number_code = ?1",nativeQuery = true)
    Long countByLottoGroupNumberCode(String code);
}
