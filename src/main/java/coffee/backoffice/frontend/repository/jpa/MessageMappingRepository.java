package coffee.backoffice.frontend.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.frontend.model.MessageMapping;

@Repository
public interface MessageMappingRepository extends CrudRepository<MessageMapping, Long> {

	public List<MessageMapping> findByMessageCode(String code);

	public MessageMapping findByMessageCodeAndUsername(String messageCode, String username);

	public void deleteByMessageCode(String code);

	public void deleteByUsername(String username);

	public void deleteByMessageCodeAndUsername(String code, String username);
}
