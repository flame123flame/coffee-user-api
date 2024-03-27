package coffee.website.gamefavorite.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.website.gamefavorite.model.GameFavorite;
import coffee.website.gamefavorite.repository.GameFavoriteRepository;
import coffee.website.gamefavorite.vo.req.GameFavoriteRequest;
import coffee.website.gamefavorite.vo.res.GameFavoriteResponse;
import framework.constant.ProjectConstant;

@Service
public class GameFavoriteService {

	@Autowired
	private GameFavoriteRepository gameFavoriteRepository;
	
	public List<GameFavoriteResponse> getGameFavoriteList(String username) {
		List<GameFavoriteResponse> res = new ArrayList<GameFavoriteResponse>();
		List<GameFavorite> dataList = gameFavoriteRepository.findByUsernameAndViewStatusOrderByUpdatedDateDesc(username, ProjectConstant.GAME.FAV);
		int seq = 0;
		for(GameFavorite temp:dataList) {
			GameFavoriteResponse data = new GameFavoriteResponse();
			seq++;
			data.setSeqence(seq);
			data.setProviderCode(temp.getProvider());
			data.setGameName(temp.getGameName());
			data.setGameCode(temp.getGameCode());
			data.setIconUrl(temp.getIconUrl());
			res.add(data);
		}
		return res;
	}
	
	public List<GameFavoriteResponse> getGameFrequentList(String username) {
		List<GameFavoriteResponse> res = new ArrayList<GameFavoriteResponse>();
		List<GameFavorite> dataList = gameFavoriteRepository.findByUsernameOrderByCountPlayDesc(username);
		int seq = 0;
		for(GameFavorite temp:dataList) {
			GameFavoriteResponse data = new GameFavoriteResponse();
			seq++;
			data.setSeqence(seq);
			data.setGameCode(temp.getGameCode());
			data.setGameName(temp.getGameName());
			data.setProviderCode(temp.getProvider());
			data.setIconUrl(temp.getIconUrl());
			res.add(data);
		}
		return res;
	}
	
	public void countGame(String username,String gameName,String gameCode,String provider,String iconUrl) {
		GameFavorite data = gameFavoriteRepository.findByUsernameAndGameCodeAndProvider(username,gameCode,provider);
		if(data == null) {
			data = new GameFavorite();
			data.setUsername(username);
			data.setGameName(gameName);
			data.setGameCode(gameCode);
			data.setIconUrl(iconUrl);
			data.setViewStatus(ProjectConstant.GAME.NON_FAV);
			data.setProvider(provider);
			data.setCountPlay(1);
			data.setCreatedBy(username);
		}else {
			Integer count = data.getCountPlay();
			data.setCountPlay(count+1);
			data.setUpdatedDate(new Date());
			data.setUpdatedBy(username);
		}
		gameFavoriteRepository.save(data);
	}
	
	public void changeGameFavorite(GameFavoriteRequest req) {
		GameFavorite data = gameFavoriteRepository.findByUsernameAndGameCodeAndProvider(req.getUsername(),req.getGameCode(),req.getProviderCode());
		if(data == null) {
			data = new GameFavorite();
			data.setUsername(req.getUsername());
			data.setGameCode(req.getGameCode());
			data.setGameName(req.getGameName());
			data.setIconUrl(req.getIconUrl());
			data.setViewStatus(req.getViewStatus());
			data.setProvider(req.getProviderCode());
			data.setCountPlay(1);
			data.setCreatedBy(req.getUsername());
		}else {
			data.setViewStatus(req.getViewStatus());
			data.setUpdatedDate(new Date());
			data.setUpdatedBy(req.getUsername());
		}
		gameFavoriteRepository.save(data);
	}
}
