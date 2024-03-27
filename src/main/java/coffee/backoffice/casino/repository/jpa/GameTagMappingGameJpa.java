package coffee.backoffice.casino.repository.jpa;

import coffee.backoffice.casino.model.GameTagMappingGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface GameTagMappingGameJpa extends CrudRepository<GameTagMappingGame, Long> {

    @Query(value = "select count(1) from game_tag_mapping_game tb where tb.game_tag_code = ?1",nativeQuery = true)
    Long countAllByGameGroupCode(String code);

    List<GameTagMappingGame> findTop8ByGameTagCode(String code);
    List<GameTagMappingGame> findAllByGameTagCode(String code);
    List<GameTagMappingGame> findTop2ByGameTagCode(String code);

	List<GameTagMappingGame> findAllByGameCode(String code);

    public void deleteByGameCode (String gameCode);
}