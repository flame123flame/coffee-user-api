package coffee.backoffice.player.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.vo.res.BankVerifyRes;
import coffee.backoffice.player.vo.res.CustomerRes;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findAll();

	List<Customer> findAllByEnable(Boolean enable);

	Customer findByUsername(String username);

	Customer findByMobilePhone(String phone);
	
	Customer findByBankCodeAndBankAccount(String bankCode,String bankAccount);

	List<Customer> findByGroupCode(String code);
	List<Customer> findByGroupCodeIn(List<String> listCode);

	boolean existsByMobilePhone(String mobilePhone);

	boolean existsByMobilePhoneAndRegisterStatus(String mobilePhone, Long status);

	boolean existsByUsernameAndRegisterStatus(String mobilePhone, Long status);

	boolean existsByRegisterStatus(Long status);

	boolean existsByUsername(String username);

	public void deleteByUsername(String username);

	@Query(value = "SELECT COUNT(c.username) FROM customer c  where c.created_date BETWEEN ?1 and ?2 ", nativeQuery = true)
	Integer findTotalRegPlayers(String startDate, String endDate);

}
