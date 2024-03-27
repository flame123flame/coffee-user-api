package coffee.backoffice.masterdata.repository.redis;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import coffee.backoffice.masterdata.model.redis.LovRedisModel;

@EnableRedisRepositories
public interface LovRedisRepo extends CrudRepository<LovRedisModel, Long>{
	public LovRedisModel findByLovKey(String lovKey);
}
