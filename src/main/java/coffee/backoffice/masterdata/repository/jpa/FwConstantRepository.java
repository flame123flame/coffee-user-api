package coffee.backoffice.masterdata.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.masterdata.model.FwConstant;

@Repository
public interface FwConstantRepository extends CrudRepository<FwConstant, Long>{
	public List<FwConstant> findAllByOrderByCreateDateDesc();
	
	public List<FwConstant> findAll();
	public FwConstant findByConstantKey(String constantKey);
}
