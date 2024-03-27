package coffee.backoffice.rebate.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.rebate.model.RebateCondition;
@Repository
public interface RebateConditionJpa extends JpaRepository<RebateCondition,Long> {
    List<RebateCondition> findAll();
    List<RebateCondition> findAllByRebateCode(String code);
    List<RebateCondition> findAllByRebateCodeAndGameProviderCode(String code,String gameCode);

    Optional<RebateCondition> findByCode(String code);

    Optional<RebateCondition> findById(Long Id);

    public void deleteByRebateCode(String code);
}
