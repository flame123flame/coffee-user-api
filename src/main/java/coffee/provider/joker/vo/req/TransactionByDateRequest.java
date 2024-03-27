package coffee.provider.joker.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransactionByDateRequest {

	@JsonProperty("Method")
	private final String method;

	@JsonProperty("StartDate")
	private String startDate;

	@JsonProperty("EndDate")
	private String endDate;

	@JsonProperty("NextId")
	private String nextId;

	@JsonProperty("Timestamp")
	private Long timestamp;
}
