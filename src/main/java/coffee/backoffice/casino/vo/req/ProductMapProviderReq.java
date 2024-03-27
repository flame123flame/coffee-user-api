package coffee.backoffice.casino.vo.req;

import java.util.List;

import lombok.Data;

@Data
public class ProductMapProviderReq {
	private String productCode;
	private List<String> providerCode;
}
