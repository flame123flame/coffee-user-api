package coffee.website.frontend.vo.res;

import java.util.List;

import lombok.Data;
@Data
public class LandingWebPlayer {
	private String header;
	private String detail;
	private List<String> img;
	private String configPath;
}
