package coffee.backoffice.masterdata.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.masterdata.model.FwLovHdr;

@Repository
public interface FwLovHdrRepository extends CrudRepository<FwLovHdr, Long>{

	public List<FwLovHdr> findAllByOrderByCreateDateDesc();
	
	public void deleteByLovKey(String lovKey);
}
