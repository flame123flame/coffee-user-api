package coffee.backoffice.finance.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.Wallet;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long>{
	public List<Wallet> findByUsername(String username);
	
	public Wallet findByUsernameAndWalletName(String username,String walletName);

	List<Wallet> findAllByUsername(String username);
}
