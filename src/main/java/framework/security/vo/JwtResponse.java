package framework.security.vo;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private final String jwttoken;
	private final String username;
	private final List<String> role;
	private final List<String> menuAccess;
	private final String message;
}
