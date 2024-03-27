package coffee.backoffice.admin.repository.jpa;

import coffee.backoffice.admin.model.FwRoleMenuAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FwRoleMenuAccessRepository extends CrudRepository<FwRoleMenuAccess,Long> {
    List<FwRoleMenuAccess> findAllByFwUserRoleCode(String fwUserRoleCode);
}
