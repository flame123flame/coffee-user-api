package coffee.provider.joker.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.provider.joker.vo.model.JokerOutstanding;

@Repository
public interface JokerOutstandingRepository extends CrudRepository<JokerOutstanding, Long>{
	public JokerOutstanding findByUsername(String username);
}
