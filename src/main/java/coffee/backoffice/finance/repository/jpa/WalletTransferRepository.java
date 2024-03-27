package coffee.backoffice.finance.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.WalletTransfer;

@Repository
public interface WalletTransferRepository extends JpaRepository<WalletTransfer, Long>{

    public List<WalletTransfer> findAllByOrderByCreatedDateDesc();
}
