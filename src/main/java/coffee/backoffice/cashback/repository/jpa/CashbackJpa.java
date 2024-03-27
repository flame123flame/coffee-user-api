package coffee.backoffice.cashback.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import coffee.backoffice.cashback.model.Cashback;

@Repository
public interface CashbackJpa extends JpaRepository<Cashback, Long> {
	List<Cashback> findAll();

	List<Cashback> findAllByPeriodStatus(Long periodStatus);

	Cashback findByCode(String code);

	public void deleteByCode(String code);

	@Query(value = "SELECT * from cashback r where r.start_date <= ?1 and r.end_date >= ?1 AND r.status = 1 AND (r.next_batch_job_date is null or r.next_batch_job_date <= ?1)",nativeQuery = true)
	List<Cashback> findAllMustDoCashBack(Date date);
}
