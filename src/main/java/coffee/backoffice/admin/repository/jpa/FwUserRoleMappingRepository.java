package coffee.backoffice.admin.repository.jpa;

import coffee.backoffice.admin.model.FwUserRoleMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface FwUserRoleMappingRepository extends CrudRepository<FwUserRoleMapping,Long> {
    List<FwUserRoleMapping> findAllByUsername(String username);

    @Query(value = "select count(1) from fw_user_role_mapping furm where furm.fw_role_code = ?1" , nativeQuery = true)
    Long countByRoleCode(String code);
}
