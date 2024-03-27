package coffee.provider.joker.vo.model;

import java.util.List;

import coffee.provider.joker.vo.res.JokerGamesListRes;
import lombok.Data;

@Data
public class JokerGamesList {

	private List<JokerGamesListRes> ListGames;
	private String Message;
}
