package coffee.backoffice.frontend.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.frontend.model.DepositSetting;

@Repository
public interface DepositSettingJpa extends CrudRepository<DepositSetting, Long>{
	List<DepositSetting> findAll();
}
