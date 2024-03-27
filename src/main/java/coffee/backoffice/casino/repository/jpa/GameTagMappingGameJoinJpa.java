package coffee.backoffice.casino.repository.jpa;

import coffee.backoffice.casino.model.GameTagMappingGameJoin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameTagMappingGameJoinJpa extends CrudRepository<GameTagMappingGameJoin, Long> {
    @Query(value = "select count(1) from game_tag_mapping_game tb where tb.game_tag_code = ?1",nativeQuery = true)
    Long countAllByGameTagCode(String code);

    List<GameTagMappingGameJoin> findTop8ByGameTagCode(String code);
    List<GameTagMappingGameJoin> findAllByGameTagCode(String code);
    List<GameTagMappingGameJoin> findTop2ByGameTagCode(String code);

    List<GameTagMappingGameJoin> findAllByGameCode(String code);
}
