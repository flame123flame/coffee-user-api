package coffee.backoffice.frontend.vo.res;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class LandingRes {
	private Long id;
	private String header;
	private String detail;
	private List<String> img;
	private String configPath;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
}
