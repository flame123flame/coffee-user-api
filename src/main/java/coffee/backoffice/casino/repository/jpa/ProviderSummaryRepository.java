package coffee.backoffice.casino.repository.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import coffee.backoffice.casino.model.ProviderSummary;

@Repository
public interface ProviderSummaryRepository extends JpaRepository<ProviderSummary,Long> {
	
	public List<ProviderSummary> findByUsername( String username);
	
	public ProviderSummary findByProviderCodeAndUsername(String code , String username);
	
	public List<ProviderSummary> findByProviderCodeInAndUsername(List<String> code , String username);
	
	@Query(value = "SELECT SUM(total_valids_bet) FROM provider_summary WHERE provider_code IN (?1) AND username = ?2 ", nativeQuery = true)
	public BigDecimal findSumProviderSummary(List<String> code , String username);
}
