package coffee.backoffice.masterdata.repository.redis;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import coffee.backoffice.masterdata.model.redis.ConstantRedisModel;

@EnableRedisRepositories
public interface ConstantRedisRepo extends CrudRepository<ConstantRedisModel, Long>{
	public ConstantRedisModel findByKey(String constantCode);
}
