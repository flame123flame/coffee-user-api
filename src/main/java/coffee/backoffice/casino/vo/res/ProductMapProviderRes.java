package coffee.backoffice.casino.vo.res;

import java.util.List;

import lombok.Data;
@Data
public class ProductMapProviderRes {
	private String productCode;
	private List<String> providerCode;
}
