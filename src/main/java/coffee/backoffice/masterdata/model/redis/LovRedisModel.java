package coffee.backoffice.masterdata.model.redis;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RedisHash("lov")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LovRedisModel {
	@Id
	private Long id;
	@Indexed
	private String lovKey;
	private List<LovDetail> value;
}
