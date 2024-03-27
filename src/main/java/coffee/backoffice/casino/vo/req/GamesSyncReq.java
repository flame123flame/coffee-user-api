package coffee.backoffice.casino.vo.req;

import lombok.Data;

@Data
public class GamesSyncReq {
	private String provider;
	private String gameGroupNameEn;
	private String gameGroupCode;

}
