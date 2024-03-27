package coffee.backoffice.finance.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.Withdraw;

import java.util.List;

@Repository
public interface WithdrawRepository extends CrudRepository<Withdraw, Long>{


    List<Withdraw> findAllByUsernameOrderByCreatedDateDesc(String username);
}
