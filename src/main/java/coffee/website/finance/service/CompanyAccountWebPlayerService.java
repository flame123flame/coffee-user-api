package coffee.website.finance.service;

import coffee.backoffice.finance.model.CompanyAccount;
import coffee.backoffice.finance.repository.jpa.CompanyAccountJpa;
import coffee.backoffice.finance.vo.res.CompanyAccountRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyAccountWebPlayerService {

    @Autowired
    private CompanyAccountJpa companyAccountJpa;
    
    @Autowired
    private CustomerService customerService;

    public CompanyAccountRes getRand(String username) throws Exception {
    	Customer cus = customerService.getByUsername(username);
        Optional<CompanyAccount> data = companyAccountJpa.getRand(cus.getGroupCode());
        if (!data.isPresent())
            return null;
        CompanyAccountRes returnData = new CompanyAccountRes();
        returnData.setEntityToRes(data.get());
        return returnData;
    }

}
