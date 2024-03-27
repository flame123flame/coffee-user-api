package framework.security.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import coffee.backoffice.admin.model.FwRole;
import coffee.backoffice.admin.repository.jpa.FwRoleRepository;
import coffee.backoffice.admin.service.FwRoleMenuAccessService;
import coffee.backoffice.player.service.FailedLoginService;
import coffee.backoffice.player.vo.req.FailedLoginReq;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import framework.constant.ProjectConstant.LOGIN_MESSAGE;
import framework.security.authorization.JwtTokenUtil;
import framework.security.service.JwtUserDetailsService;
import framework.security.vo.JwtRequest;
import framework.security.vo.JwtResponse;

@CrossOrigin
@RestController
@RequestMapping(value = "token/")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private FwRoleMenuAccessService fwRoleMenuAccessService;

	@Autowired
	FailedLoginService failedLoginService;

	@Autowired
	FwRoleRepository fwRoleRepository;

	@Autowired
	private CustomerRepository customerRepo;

	private ModelMapper modelMapper = new ModelMapper();

	@SuppressWarnings("deprecation")
	@PostMapping("generate-token/{app}")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
			@PathVariable("app") String app,HttpServletRequest request) throws Exception {

		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String username = authenticationRequest.getUsername().toLowerCase();
		authenticate(username + "∆∆∆" + app, authenticationRequest.getPassword(),
				authenticationRequest.getRealName(), "-",
				request.getHeader("HTTP_X-Real-IP"),userAgent.getOperatingSystem().getDeviceType().getName(),
				userAgent.getBrowser().getName(), userAgent.getBrowserVersion().getVersion());
		
//		authenticate(username + "∆∆∆" + app, authenticationRequest.getPassword(),
//				authenticationRequest.getRealName(), "-",
//				request.getRemoteAddr(),userAgent.getOperatingSystem().getDeviceType().getName(),
//				userAgent.getBrowser().getName(), "");
		
		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(username + "∆∆∆" + app);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(new JwtResponse("T_T", username, new ArrayList<String>(),
							new ArrayList<String>(), LOGIN_MESSAGE.BLOCK));
		}

		List<FwRole> fwRole = fwRoleRepository.getUserRole(userDetails.getUsername());
		List<String> fwRoleRes = new ArrayList<>();
		List<String> fwUserRoleMenuAccesses = new ArrayList<>();
		for (FwRole item : fwRole) {
			fwRoleRes.add(item.getName());
			List<String> fwRoleMenuAccessRes = fwRoleMenuAccessService.getByUserRoleCodeListString(item.getCode());
			fwUserRoleMenuAccesses.addAll(fwRoleMenuAccessRes);
		}
		
		final String token = jwtTokenUtil.generateToken(userDetails, app, fwRoleRes, fwUserRoleMenuAccesses,
				LOGIN_MESSAGE.SUCCESS);

		if(!"bo".equals(app)){
			Customer customer = customerRepo.findByUsername(username);
			customer.setToken(token);
			customerRepo.save(customer);
		}


		return ResponseEntity.ok(new JwtResponse(token, username, fwRoleRes,
				fwUserRoleMenuAccesses, LOGIN_MESSAGE.SUCCESS));
	}

	private void authenticate(String username, String password, String realName, String country, String ipAddress,
			String platform, String browserName, String browserVersion) throws Exception {
		FailedLoginReq failedLoginReq = new FailedLoginReq();
		List<String> list = Arrays.asList(username.split("∆∆∆"));
		
		String playerId = list.get(0);
		String app  = list.get(1);
		failedLoginReq.setUsername(playerId);
		failedLoginReq.setCountry(country);
		failedLoginReq.setIpAddress(ipAddress);
		failedLoginReq.setPlatform(platform);
		failedLoginReq.setBrowserName(browserName);
		failedLoginReq.setBrowserVersion(browserVersion);

		try {
			System.out.println("Username=>>" + username + "         Password=>>" + password);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			userDetailsService.loadUserByUsername(username);
		} catch (DisabledException e) {
			if (!app.equals("bo")) {
				failedLoginReq.setRemark(LOGIN_MESSAGE.BLOCK);
				failedLoginService.failedLogin(failedLoginReq);
			}
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			if (!app.equals("bo")) {
				failedLoginReq.setRemark(LOGIN_MESSAGE.PASSWORD_VALID);
				failedLoginService.failedLogin(failedLoginReq);
			}
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}


	@GetMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("username").toString());
		Customer customer = customerRepo.findByUsername(expectedMap.get("username").toString());
		customer.setToken(token);
		customerRepo.save(customer);
		return ResponseEntity.ok(new JwtResponse(token,expectedMap.get("username").toString(),null,null,null));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

}
