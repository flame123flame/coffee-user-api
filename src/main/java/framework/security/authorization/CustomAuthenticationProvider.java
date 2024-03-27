package framework.security.authorization;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import framework.security.model.FwUser;
import framework.security.repository.FwUserRepo;

@Component("CustomAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private FwUserRepo fwUserRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String[] data = authentication.getName().split("∆∆∆");
		String username = data[0];
		String app = data[1];
		String password = authentication.getCredentials().toString();

		FwUser adm = null;
		Customer customer = null;
		if ("bo".equals(app)) {
			adm = fwUserRepo.findByUsername(username);
			if (adm == null ) {
				throw new BadCredentialsException("User not found with username: " + username);
			}
		} else {
			customer = customerRepo.findByUsername(username);
			if (customer == null) {
				throw new BadCredentialsException("User not found with username: " + username);
			}
		}
		
		String usernameDB;
		String passwordDB;
		if ("bo".equals(app)) {
			usernameDB = adm.getUsername();
			passwordDB = adm.getPassword();
		} else {
			usernameDB = customer.getUsername();
			passwordDB = customer.getPassword();
		}
		
		if (checkData(username, password, usernameDB, passwordDB)) {
			return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
		} 
		else {
			throw new BadCredentialsException("Authentication failed");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private boolean checkData(String username, String password, String usernameDB, String passwordDB){
		return	username.equals(usernameDB) && passwordEncoder.matches(password, passwordDB) ;
	}

}
