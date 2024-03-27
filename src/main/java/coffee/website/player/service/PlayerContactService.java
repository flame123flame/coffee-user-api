package coffee.website.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.model.AffiliateNetwork;
import coffee.backoffice.affiliate.service.AffiliateService;
import coffee.backoffice.player.model.ContactUs;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.service.ContactUsService;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.service.GroupLevelService;

@Service
public class PlayerContactService {

	@Autowired
	private ContactUsService contactUsService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GroupLevelService groupLevelService;
	
	@Autowired
	private AffiliateService affiliateService;
	
	public void requestContact(String username) {
		Customer customer = customerService.getByUsername(username);
		if(customer != null) {
			ContactUs contact = new ContactUs();
			contact.setUsername(username);
			contact.setRealName(customer.getRealName());
			GroupLevel group = groupLevelService.getByGroupCode(customer.getGroupCode());
			contact.setGroupName(group.getGroupName());
			contact.setEmail(customer.getEmail());
			contact.setMobilePhone(customer.getMobilePhone());
			contact.setBankCode(customer.getBankCode());
			contact.setBankAccount(customer.getBankAccount());
			contact.setBankStatus(customer.getBankStatus());
			AffiliateNetwork network = affiliateService.getAffiliateNetworkByUsername(username);
			contact.setRegisteredDate(network.getRegisterDate());
			contact.setLastLogin(customer.getLastLoginDate());
			contact.setStatus(customer.getEnable()? "ENABLE":"DISABLE");
			contactUsService.saveContact(contact);
		}
	}
	
}
