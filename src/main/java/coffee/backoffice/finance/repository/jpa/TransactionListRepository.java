package coffee.backoffice.finance.repository.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import coffee.backoffice.finance.model.TransactionList;

@Repository
public interface TransactionListRepository extends JpaRepository<TransactionList, Long> {
	
	public TransactionList findFirstByUsernameAndTransactionTypeOrderByCreatedDateAsc(String username,String transactionType);

	public List<TransactionList> findByUsername(String username);

	public List<TransactionList> findByUsernameAndCreatedDateBefore(String username, Date createdDate);

	public List<TransactionList> findByUsernameAndCreatedDateAfter(String username, Date createdDate);

	public List<TransactionList> findByUsernameAndCreatedDateBetween(String username, Date startDate, Date endDate);

	public List<TransactionList> findByUsernameAndTransactionType(String username, String transactionType);
	
	@Query(value = "SELECT SUM(tranfer_amount) FROM transaction_list WHERE username = ?1 AND transaction_type = ?2", nativeQuery = true)
	public BigDecimal findSumByUsernameAndTransactionType(String username, String transactionType);

	public List<TransactionList> findByUsernameAndTransactionTypeAndCreatedDateBefore(String username,
			String transactionType, Date createdDate);

	public List<TransactionList> findByUsernameAndTransactionTypeAndCreatedDateAfter(String username,
			String transactionType, Date createdDate);

	public List<TransactionList> findByUsernameAndTransactionTypeAndCreatedDateBetween(String username,
			String transactionType, Date startDate, Date endDate);

	@Query(value = "SELECT SUM(tl.add_balance) FROM transaction_list tl  WHERE tl.transaction_date BETWEEN ?1 AND ?2  AND tl.transaction_type = ?3 ", nativeQuery = true)
	public BigDecimal totalAddBalanceByType(Date startDate, Date endDate, String transactionType);

	@Query(value = "SELECT SUM(tl.sub_balance) FROM transaction_list tl  WHERE tl.transaction_date BETWEEN ?1 AND ?2  AND tl.transaction_type = ?3 ", nativeQuery = true)
	public BigDecimal totalSubBalanceByType(Date startDate, Date endDate, String transactionType);

	@Query(value = "SELECT SUM(tl.add_balance) FROM transaction_list tl  WHERE tl.transaction_date BETWEEN ?1 AND ?2  AND tl.transaction_type = ?3 ", nativeQuery = true)
	public BigDecimal findTotalAddBalanceByType(Date startDate, Date endDate, String transactionType);

	@Query(value = "SELECT COUNT(ct.username) FROM (SELECT username FROM transaction_list tl WHERE transaction_date BETWEEN ?1 AND ?2 AND transaction_type =?3 GROUP BY username) ct", nativeQuery = true)
	public Integer countUsernameByTypeGroupByUsername(Date startDate, Date endDate, String transactionType);

	@Query(value = "SELECT COUNT(transaction_type) FROM transaction_list tl WHERE transaction_date BETWEEN ?1 AND ?2 AND transaction_type =?3", nativeQuery = true)
	public Integer countTransactionTypeByType(Date startDate, Date endDate, String transactionType);
	
	
	
	@Query( value = "SELECT COALESCE(SUM(total_balance),0) FROM transaction_list WHERE created_date BETWEEN ?1 AND ?2 AND transaction_type = ?3"  , nativeQuery = true)
	 public BigDecimal findBySum(Date startDate, Date endDate, String transactionType);
	
	@Query( value = "SELECT COUNT(transaction_type) FROM transaction_list WHERE created_date BETWEEN ?1 AND ?2 AND transaction_type = ?3"  , nativeQuery = true)
	 public Integer findByCount(Date startDate, Date endDate, String transactionType);
	
	@Query( value = "SELECT COUNT(DISTINCT username) FROM transaction_list WHERE created_date BETWEEN ?1 AND ?2 AND transaction_type = ?3"  , nativeQuery = true)
	 public Integer findByPeople(Date startDate, Date endDate, String transactionType);
	
	@Query( value = "SELECT SUM(total_balance) FROM transaction_list WHERE created_date BETWEEN ?1 AND ?2 ", nativeQuery = true)
	 public BigDecimal findBySumTotal(Date startDate, Date endDate);
	
	
	
}
