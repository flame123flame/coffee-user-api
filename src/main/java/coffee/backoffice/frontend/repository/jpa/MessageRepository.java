package coffee.backoffice.frontend.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.frontend.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{
	
	public Message findByMessageCode(String messageCode);
//	Message findByMessageCodeAndEnable(String code,Boolean enable);
	public void deleteByMessageCode(String code);
}
