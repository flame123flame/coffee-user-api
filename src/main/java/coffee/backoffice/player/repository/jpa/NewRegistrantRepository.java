package coffee.backoffice.player.repository.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.model.NewRegistrant;
@Repository
public interface NewRegistrantRepository extends CrudRepository<NewRegistrant, Long>  {

	@Query(value = "select * from new_registrant where is_delete = 'N'  ", nativeQuery = true)
	public List<NewRegistrant> getAllNewRegistrant();
	
	
	@Query( value = "SELECT COUNT(an.register_date) FROM affiliate_network an  WHERE an.register_date BETWEEN ?1 AND ?2 "  , nativeQuery = true)
	 public Integer findByCountThisWeek(Date startDate, Date endDate);
	
}
