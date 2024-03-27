package coffee.backoffice.rebate.repository.jpa;

import coffee.backoffice.rebate.model.RebateBatchWaiting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebateBatchWaitingJpa extends CrudRepository<RebateBatchWaiting,Long> {
    List<RebateBatchWaiting> findAllByStatus(String waiting);

    List<RebateBatchWaiting> findAllByStatusAndIsAuto(String waiting, boolean b);

    RebateBatchWaiting findByCode(String code);

    List<RebateBatchWaiting> findAllByRebateCodeAndStatusAndIsAuto(String rebateCode, String waiting, boolean b);
}
