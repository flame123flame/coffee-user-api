package coffee.backoffice.admin.repository.jpa;

import coffee.backoffice.admin.model.FwRole;
import coffee.backoffice.admin.model.FwUserRoleMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FwRoleRepository extends CrudRepository<FwRole,Long> {
    FwRole findByCode(String fwUserRoleCode);


    @Query(value = "SELECT fr.* from fw_user_role_mapping furm inner join fw_role fr on fr.code = furm.fw_role_code where furm.username = ?1" , nativeQuery = true)
    List<FwRole> getUserRole(String s);

    Optional<FwRole> findByName(String name);
}
