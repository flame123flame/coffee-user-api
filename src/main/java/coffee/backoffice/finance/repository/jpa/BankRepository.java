package coffee.backoffice.finance.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.Bank;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long>{
	public List<Bank> findAll();
	public Bank findByBankCode(String bankCode);
	public void deleteByBankCode(String bankCode);
}
