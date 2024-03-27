package coffee.backoffice.casino.vo.res;

import java.util.List;

import lombok.Data;
@Data
public class GameGroupMapProviderRes {
	private String groupCode;
	private List<String> providerCode;
}
