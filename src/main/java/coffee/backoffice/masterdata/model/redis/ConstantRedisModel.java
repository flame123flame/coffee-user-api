package coffee.backoffice.masterdata.model.redis;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RedisHash("constant")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ConstantRedisModel {
	@Id
    private Long id;
    @Indexed
    private String key;
    private String value;
}
