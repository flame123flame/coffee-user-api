package coffee.backoffice.casino.vo.res;

import java.util.List;

import lombok.Data;

@Data
public class GetAllProductTypeGameProviderRes {
	
	private String nameEn;
	private String nameTh;
	private String productTypeCode;
	private List<GameProviderMapRes> listGameProvider;
	
	@Data
	public static class GameProviderMapRes {
	    private String nameTh;
	    private String nameEn;
	    private String gameProviderCode;
	}
}
