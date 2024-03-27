package coffee.backoffice.player.vo.req;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class NewRegistrantReq {

	private String username;
	private Boolean enable;
	
	private Date startDate;
	private Date endDate;
}
