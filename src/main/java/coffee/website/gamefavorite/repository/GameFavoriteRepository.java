package coffee.website.gamefavorite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coffee.website.gamefavorite.model.GameFavorite;

@Repository
public interface GameFavoriteRepository extends JpaRepository<GameFavorite,Long> {
	
	public GameFavorite findByUsernameAndGameCodeAndProvider(String username,String gameCode,String provider);
	public List<GameFavorite> findByUsernameOrderByCountPlayDesc(String username);
	public List<GameFavorite> findByUsernameAndViewStatusOrderByUpdatedDateDesc(String username,String viewStatus);
}
