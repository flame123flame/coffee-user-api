package coffee.backoffice.casino.service;

import coffee.backoffice.casino.model.GameTagMappingGame;
import coffee.backoffice.casino.model.GameTagMappingGameJoin;
import coffee.backoffice.casino.repository.jpa.GameTagMappingGameJoinJpa;
import coffee.backoffice.casino.repository.jpa.GameTagMappingGameJpa;
import coffee.backoffice.casino.vo.req.GameTagMappingGameReq;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service

public class GameTagMappingGameService {

    @Autowired
    GameTagMappingGameJpa gameTagMappingGameJpa;

    @Autowired
    GameTagMappingGameJoinJpa gameTagMappingGameJoinJpa;

    private ModelMapper modelMapper = new ModelMapper();

    public List<GameTagMappingGameJoin> getTop8ByGameGroupCode(String code) {
        return gameTagMappingGameJoinJpa.findTop8ByGameTagCode(code);
    }

    public Long countAllByGameGroupCode(String code) {
        return gameTagMappingGameJoinJpa.countAllByGameTagCode(code);
    }

    public List<GameTagMappingGameJoin> getALLByGameGroupCode(String gameGroup) {
        return gameTagMappingGameJoinJpa.findAllByGameTagCode(gameGroup);
    }

    public List<GameTagMappingGameJoin> getTop2ByGameGroupCode(String code) {
        return gameTagMappingGameJoinJpa.findTop2ByGameTagCode(code);
    }

    @Transactional
    public void saveAll(String gameCode, List<GameTagMappingGameReq> gameGroupMappingGamesReq) {
        List<GameTagMappingGame> gameTagMappingGames = gameTagMappingGameJpa.findAllByGameCode(gameCode);
        if (gameTagMappingGames != null)
            gameTagMappingGameJpa.deleteAll(gameTagMappingGames);
        List<GameTagMappingGame> saveGameTagMappingGames = new ArrayList<>();
        for (int i = 0; i < gameGroupMappingGamesReq.size(); i++) {
            GameTagMappingGame gameTagMappingGame = new GameTagMappingGame();
            gameGroupMappingGamesReq.get(i).setGameCode(gameCode);
            modelMapper.map(gameGroupMappingGamesReq.get(i), gameTagMappingGame);
            saveGameTagMappingGames.add(gameTagMappingGame);
        }
        gameTagMappingGameJpa.saveAll(saveGameTagMappingGames);
    }

    public List<GameTagMappingGameJoin> getByGameCode(String code) {
        return gameTagMappingGameJoinJpa.findAllByGameCode(code);
    }

    public List<GameTagMappingGame> getAllByGameTagCode(String code) {
        return gameTagMappingGameJpa.findAllByGameTagCode(code);
    }

    public List<GameTagMappingGame> getAllByGameCode(String code) {
        return gameTagMappingGameJpa.findAllByGameCode(code);
    }

    public void saveAllDirect(String code, List<GameTagMappingGameReq> req) {
        List<GameTagMappingGame> gameTagMappingGames = gameTagMappingGameJpa.findAllByGameTagCode(code);
        gameTagMappingGameJpa.deleteAll(gameTagMappingGames);
        if (req != null && req.size() > 0) {
            List<GameTagMappingGame> gameTagMappingGames1 = new ArrayList<>();
            for (GameTagMappingGameReq item : req
            ) {
                GameTagMappingGame gameTagMappingGame = new GameTagMappingGame();
                modelMapper.map(item, gameTagMappingGame);
                gameTagMappingGames1.add(gameTagMappingGame);
            }
            gameTagMappingGameJpa.saveAll(gameTagMappingGames1);
        }
    }
    
    @Transactional
    public void deleteGameMappingGroup(String gameCode) {
    	gameTagMappingGameJpa.deleteByGameCode(gameCode);
    }
}
