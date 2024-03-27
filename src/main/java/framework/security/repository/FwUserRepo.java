package framework.security.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import framework.security.model.FwUser;

import java.util.List;

@Repository
public interface FwUserRepo extends CrudRepository<FwUser, Long> {
	FwUser findByUsername(String username);

	@Query(value = "select count(1) from fw_user fu where fu.fw_user_role_code = ?1",nativeQuery = true)
	Long countByUserRoleCode(String code);
}

