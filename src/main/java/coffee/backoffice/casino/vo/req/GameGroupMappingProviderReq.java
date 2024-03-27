package coffee.backoffice.casino.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class GameGroupMappingProviderReq {
	private String groupCode;
	private List<String> providerCode;
}
