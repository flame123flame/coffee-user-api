package coffee.provider.joker.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlayGameRequest {
	@JsonProperty("Method")
	private final String method;
	
	@JsonProperty("Username")
	private final String username;
//	
//	@JsonProperty("Amount")
//	private final String amount;
//	
//	@JsonProperty("RequestID")
//	private final String requestId;
	
	@JsonProperty("Timestamp")
	private Long timestamp;
}
