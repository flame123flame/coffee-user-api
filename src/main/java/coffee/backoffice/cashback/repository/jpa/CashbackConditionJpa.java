package coffee.backoffice.cashback.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.cashback.model.CashbackCondition;

@Repository
public interface CashbackConditionJpa extends JpaRepository<CashbackCondition, Long> {
	List<CashbackCondition> findAll();

	List<CashbackCondition> findAllByCashbackCode(String code);

	CashbackCondition findByCode(String code);

	Optional<CashbackCondition> findById(Long Id);
	
	public void deleteByCashbackCode(String code);

    List<CashbackCondition> findAllByCashbackCodeOrderByMoreThanAmountDesc(String code);
}
