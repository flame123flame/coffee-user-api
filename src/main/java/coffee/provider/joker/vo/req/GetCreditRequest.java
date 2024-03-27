package coffee.provider.joker.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetCreditRequest {
	@JsonProperty("Method")
	private final String method;
	
	@JsonProperty("Username")
	private final String username;
	
	@JsonProperty("Timestamp")
	private Long timestamp;
}
