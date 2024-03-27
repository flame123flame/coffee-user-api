package coffee.website.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.website.register.vo.req.GetOtpReq;
import coffee.website.register.vo.res.GetOtpRes;

@Service
public class RegisterService {

    @Autowired
    private CustomerRepository customerRepository;

    public Boolean validateUserName(String username) {
        boolean customer = customerRepository.existsByUsername(username);
        return customer;
    }

    public void registerFail(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer.getRegisterStatus() != 2)
            customerRepository.delete(customer);
    }


}
