package coffee.backoffice.casino.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class GameGroupReq {
	private String code;
	private String nameTh;
    private String nameEn;
    private List<String> productCode;
	
}
