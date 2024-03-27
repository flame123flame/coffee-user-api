package coffee.backoffice.finance.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.GameHistory;
import coffee.backoffice.finance.repository.jpa.GameHistoryRepository;
import coffee.website.providers.vo.request.PlayGameRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameHistoryService {

	@Autowired
	private GameHistoryRepository gameHistoryRepository;
	
	public void createGameHistory(PlayGameRequest req) {
		GameHistory entity = new GameHistory();
		entity.setOrderNo(req.getOrderNo());
		entity.setUsername(req.getUsername());
		entity.setGameCode(req.getGameCode());
		entity.setGameName(req.getGameName());
		entity.setProvider(req.getProviderCode());
		entity.setUseBonus(req.getCheckBonus());
		entity.setRemark(req.getRemark());
		entity.setCreditIn(new BigDecimal(req.getBalance()));
		entity.setCreditOut(BigDecimal.ZERO);
		entity.setCreditResult(BigDecimal.ZERO);
		entity.setPlayStatus("ONLINE");
		entity.setCreatedBy("_system");
		gameHistoryRepository.save(entity);
	}
	
	public void createGameHistory(PlayGameRequest req,BigDecimal result) {
		GameHistory entity = new GameHistory();
		entity.setOrderNo(req.getOrderNo());
		entity.setUsername(req.getUsername());
		entity.setGameCode(req.getGameCode());
		entity.setGameName(req.getGameName());
		entity.setProvider(req.getProviderCode());
		entity.setRemark(req.getRemark());
		entity.setCreditIn(new BigDecimal(req.getBalance()));
		entity.setCreditOut(BigDecimal.ZERO);
		entity.setCreditResult(result);
		entity.setPlayStatus("ONLINE");
		entity.setCreatedBy("_system");
		gameHistoryRepository.save(entity);
	}
	
	public void updateGameHistory(String username, String provider ,BigDecimal creditOut,String remark) {
		List<GameHistory> list = gameHistoryRepository.findByUsernameAndProviderAndPlayStatusOrderByCreatedDateDesc(username, provider, "ONLINE");
		if(list != null && list.size() >0) {
			GameHistory entity = list.get(0);
			entity.setPlayStatus("OFFLINE");
			entity.setCreditOut(creditOut);
			entity.setCreditResult(creditOut.subtract(entity.getCreditIn()));
			entity.setRemark(remark);
			entity.setUpdatedBy(username);
			entity.setUpdatedDate(new Date());
			if(entity.getCreditResult().compareTo(BigDecimal.ZERO) != 0) {
				gameHistoryRepository.save(entity);
			}else {
				gameHistoryRepository.deleteAll(list);
			}
		}
	}
	
	public void updateGameHistory(String username, String provider ,BigDecimal creditOut,String remark,String gameCode) {
		List<GameHistory> list = gameHistoryRepository.findByUsernameAndGameCodeAndProviderAndPlayStatusOrderByCreatedDateDesc(username,gameCode, provider, "ONLINE");
		if(list != null && list.size() >0) {
			GameHistory entity = list.get(0);
			entity.setPlayStatus("OFFLINE");
			entity.setCreditOut(creditOut);
			entity.setCreditResult(creditOut.subtract(entity.getCreditIn()));
			entity.setRemark(remark);
			entity.setUpdatedBy(username);
			entity.setUpdatedDate(new Date());
			if(entity.getCreditResult().compareTo(BigDecimal.ZERO) != 0) {
				gameHistoryRepository.save(entity);
			}else {
				gameHistoryRepository.deleteAll(list);
			}
		}
	}
	
	public GameHistory getGameHistoryByOrder(String orderNo,String provider) {
		return gameHistoryRepository.findByOrderNoAndProvider(orderNo, provider);
	}
	
	
	public void updateGameHistory(String orderNo,String username, String provider ,BigDecimal creditOut,String remark,String transactionResult) {
		GameHistory entity = gameHistoryRepository.findByOrderNoAndProvider(orderNo, provider);
		if(entity != null) {
			entity.setPlayStatus("OFFLINE");
			entity.setCreditOut(creditOut);
			entity.setCreditResult(creditOut.subtract(entity.getCreditIn()));
			entity.setRemark(remark);
			entity.setUpdatedBy(username);
			entity.setUpdatedDate(new Date());
			entity.setTransactionResult(transactionResult);
			gameHistoryRepository.save(entity);
		}
	}
	
	public GameHistory getLastGameHistoryUsernameAndProvider(String username,String provider) {
		return gameHistoryRepository.findFirstByUsernameAndProviderOrderByCreatedDateDesc(username,provider);
	}
	
	public GameHistory getGameHistoryUsernameAndProviderAndGameCode(String username,String provider,String gameCode) {
		return gameHistoryRepository.findFirstByUsernameAndProviderAndGameCodeOrderByCreatedDateDesc(username,provider,gameCode);
	}
	
	public List<GameHistory> getGameHistoryUsername(String username) {
		return gameHistoryRepository.findByUsername(username);
	}
	
	public List<GameHistory> getGameHistoryUsernameAfter(String username,Date createdDate) {
		return gameHistoryRepository.findByUsernameAndCreatedDateAfter(username,createdDate);
	}
	
	public List<GameHistory> getGameHistoryUsernameBefore(String username,Date createdDate) {
		return gameHistoryRepository.findByUsernameAndCreatedDateBefore(username,createdDate);
	}
	
	public List<GameHistory> getGameHistoryUsernameAndProviderAfter(String username,String provider,Date createdDate) {
		return gameHistoryRepository.findByUsernameAndProviderAndCreatedDateAfter(username,provider,createdDate);
	}
	
	public List<GameHistory> getGameHistoryUsernameAndProviderBefore(String username,String provider,Date createdDate) {
		return gameHistoryRepository.findByUsernameAndProviderAndCreatedDateBefore(username,provider,createdDate);
	}
	
	public List<GameHistory> getGameHistoryUsernameBetween(String username,Date start,Date end) {
		return gameHistoryRepository.findByUsernameAndCreatedDateBetween(username,start,end);
	}
	
}
