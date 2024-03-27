package coffee.backoffice.masterdata.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.masterdata.model.FwLovDtl;

@Repository
public interface FwLovDtlRepository extends CrudRepository<FwLovDtl, Long>{
	
	public List<FwLovDtl> findAllByOrderBySeqAsc();
	
	public void deleteByLovKey(String lovKey);
	
	public List<FwLovDtl> findByLovKey(String lovKey);
	
	public List<FwLovDtl> findByLovKeyOrderBySeqAsc(String lovKey);
}
