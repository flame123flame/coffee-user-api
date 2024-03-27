package framework.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import framework.security.model.FwUser;
import framework.security.repository.FwUserRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private FwUserRepo fwUserRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public UserDetails loadUserByUsername(String data) throws UsernameNotFoundException{
		List<String> list = Arrays.asList(data.split("∆∆∆"));
		String username = list.get(0);
		String app = list.get(1);

		FwUser adm = null;
		Customer customer = null;
		if ("bo".equals(app)) {
			adm = fwUserRepo.findByUsername(username);
			if (adm == null) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}

		} else {
			customer = customerRepo.findByUsername(username);
			if (customer == null) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			} else {

				customer.setLastLoginDate(new Date());
				customer.setLoginStatus(true);
				customerRepo.save(customer);
			}

			if (!customer.getEnable()) {
				throw new DisabledException("User disable : " + username);
			}
		}

		String passwordDB;
		if ("bo".equals(app)) {
			passwordDB = adm.getPassword();
		} else {
			passwordDB = customer.getPassword();
			
		}
		return new org.springframework.security.core.userdetails.User(username, passwordDB, new ArrayList<>());
	}

}
