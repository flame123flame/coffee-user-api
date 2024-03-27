package coffee.provider.joker.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GameListRequest {
	@JsonProperty("Method")
	private final String method;
	
	@JsonProperty("Timestamp")
	private Long timestamp;
}
