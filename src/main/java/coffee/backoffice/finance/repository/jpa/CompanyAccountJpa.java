package coffee.backoffice.finance.repository.jpa;

import coffee.backoffice.finance.model.CompanyAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyAccountJpa extends JpaRepository<CompanyAccount, Long> {
	
    List<CompanyAccount> findAll();
    
    CompanyAccount findByCompanyAccountCode(String companyAccountCode);
    
    CompanyAccount findByBankIdAndBankAccount(String bankId,String bankAccount);

    CompanyAccount findByBankAccountAndBankCode(String bankAcc,String bankCode);
    
    CompanyAccount save(CompanyAccount data);
    default Page<CompanyAccount> findPaginate(final Pageable pageable) {
        return  findAll(pageable);
    }

//    @Query(value = "SELECT top 1 ca.* FROM customer c2 " +
//            "inner join group_level gl  on gl.group_code = c2.group_code " +
//            "inner join company_account ca  on ca.group_code  = gl.group_code " +
//            "where c2.username = :username " +
//            "ORDER by newid()",nativeQuery = true)
//    Optional<CompanyAccount> getRand(@Param("username")String username);
    @Query(value = "SELECT top 1 ca.* FROM company_account ca WHERE group_code = ?1 ORDER by newid()",nativeQuery = true)
    Optional<CompanyAccount> getRand(String groupCode);
}
