package coffee.backoffice.player.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class CustomerChangeTagReq {
	private String username;
	private List<String> tagCode;
}
