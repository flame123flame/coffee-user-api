package coffee.backoffice.cashback.repository.jpa;

import coffee.backoffice.cashback.model.CashbackBatchWaiting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CashBackBatchWaitingJpa extends CrudRepository<CashbackBatchWaiting,Long> {
    boolean existsByUsernameAndStatus(String username, String waiting);

    CashbackBatchWaiting findByUsernameAndStatus(String username, String waiting);

    List<CashbackBatchWaiting> findAllByStatusAndReceiveDateLessThan(String waiting, Date date);

    List<CashbackBatchWaiting> findAllByCashbackCode(String code);

    List<CashbackBatchWaiting> findAllByStatus(String waiting);

    List<CashbackBatchWaiting> findAllByStatusAndIsAuto(String waiting, boolean b);

    List<CashbackBatchWaiting> findAllByStatusAndReceiveDateLessThanAndIsAuto(String waiting, Date date, boolean b);

    CashbackBatchWaiting findByCode(String code);

    List<CashbackBatchWaiting> findAllByCashbackCodeAndStatusAndReceiveDateLessThanAndIsAuto(String code, String waiting, Date date, boolean b);

    List<CashbackBatchWaiting> findAllByCashbackCodeAndStatus(String cashbackCode, String waiting);
}
