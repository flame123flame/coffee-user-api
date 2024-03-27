package coffee.backoffice.frontend.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class LandingReq {
	private Long id;
	private String header;
	private String detail;
	private List<String> img;
	private String configPath;
}
