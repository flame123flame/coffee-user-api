package coffee.backoffice.casino.vo.res;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class GameGroupRes {
	private Long id;
	private String code;
	private String nameTh;
	private String nameEn;
	private Date createdAt;
	private Date updatedAt;
	private String updatedBy;
	private List<String> productCode;
}
