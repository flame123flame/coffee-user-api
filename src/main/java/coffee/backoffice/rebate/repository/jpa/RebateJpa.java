package coffee.backoffice.rebate.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import coffee.backoffice.rebate.model.Rebate;
@Repository
public interface RebateJpa extends JpaRepository<Rebate,Long> {
    List<Rebate> findAll();

    Rebate findByCode(String code);
    
    public void deleteByCode(String code);

    List<Rebate> findALLByVipGroupCodeContaining(String code);
    
    List<Rebate> findAllByPeriodStatus(Long periodStatus);

    @Query(value = "SELECT * from rebate r where r.start_date <= ?1 and r.end_date >= ?1 AND r.status = 1 AND (r.next_batch_job_date is null or r.next_batch_job_date <= ?1)",nativeQuery = true)
    List<Rebate> findAllMustDoRebate(Date date);

}
