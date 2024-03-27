package coffee.backoffice.finance.repository.jpa;

import coffee.backoffice.finance.model.ManualAdjustment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualAdjustmentJpa extends CrudRepository<ManualAdjustment,Long> {
}
