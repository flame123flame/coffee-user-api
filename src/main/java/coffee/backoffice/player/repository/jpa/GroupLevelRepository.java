package coffee.backoffice.player.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.model.GroupLevel;

@Repository
public interface GroupLevelRepository extends CrudRepository<GroupLevel, Long> {

	public List<GroupLevel> findAll();
	public List<GroupLevel> findAllByGroupCodeNotLike(String groupCode);
	public GroupLevel findByGroupCode(String groupCode);
	public GroupLevel findByDefaultGroup(Boolean defaultGroup);
	public void deleteByGroupCode(String groupCode);
		
}
