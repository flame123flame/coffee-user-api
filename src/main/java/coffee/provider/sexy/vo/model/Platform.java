package coffee.provider.sexy.vo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Platform {

	@JsonProperty("LIVE")
	private GameType LIVE;
}
