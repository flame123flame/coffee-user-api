package coffee.website.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.frontend.service.LandingService;
import coffee.website.frontend.vo.res.LandingWebPlayer;

@Service
public class LandingWebPlayerService {

	@Autowired
	private LandingService landingService;

	public LandingWebPlayer getLanding(String configPath) {
		return landingService.getLandingByPath(configPath);
	}
}
