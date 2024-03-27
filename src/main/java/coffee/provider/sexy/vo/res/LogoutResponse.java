package coffee.provider.sexy.vo.res;

import java.util.List;

import lombok.Data;

@Data
public class LogoutResponse {

	private String status;
	private int count;
	private List<String> results;
	private List<String> logoutUsers;
}
