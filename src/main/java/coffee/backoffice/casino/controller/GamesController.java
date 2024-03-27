package coffee.backoffice.casino.controller;

import java.util.List;

import coffee.backoffice.lotto.service.LottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.casino.model.Games;
import coffee.backoffice.casino.model.GamesNoIcon;
import coffee.backoffice.casino.service.GamesService;
import coffee.backoffice.casino.vo.req.GamesReq;
import coffee.backoffice.casino.vo.res.GamesDatatableRes;
import coffee.backoffice.casino.vo.res.GamesRes;
import framework.constant.ResponseConstant;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.DELETE;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.EDIT;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/games/")
@Slf4j
public class GamesController {
	@Autowired
	private GamesService gamesService;

	// GET PAGINATE
	@PostMapping("paginate")
	public ResponseData<DataTableResponse<GamesRes>> getDataPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<GamesRes>> response = new ResponseData<DataTableResponse<GamesRes>>();
		try {
//			response.setData(gamesService.getPaginateModel(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getDataPaginate()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.getDataPaginate() :" + e);
		}
		return response;
	}

	// UPDATE
	@PutMapping
	public ResponseData<GamesRes> update(@RequestBody GamesReq param) {
		ResponseData<GamesRes> response = new ResponseData<GamesRes>();
		try {
//			response.setData(gamesService.updateOne(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.update()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.update() :" + e);
		}
		return response;
	}

	// DELETE
	@DeleteMapping("delete-games/{gameCode}")
	public ResponseData<?> deleteGame(@PathVariable("gameCode") String gameCode) {
		ResponseData<?> response = new ResponseData<>();
		try {
			gamesService.deleteGameByGameCode(gameCode);;
			response.setMessage(DELETE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.deleteGame()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.deleteGame() :" + e);
		}
		return response;
	}

	// GET BY CODE
	@GetMapping("get-by-code/{code}")
	public ResponseData<List<GamesRes>> getId(@PathVariable("code") String code) {
		ResponseData<List<GamesRes>> response = new ResponseData<List<GamesRes>>();
		try {
//			response.setData(gamesService.getByGameGroupCode(code));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getId()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => GamesController.getId() : " + e);
		}
		return response;
	}

//	GET ALL
	@GetMapping("get-all-games")
	public ResponseData<List<Games>> getAllGames() {
		ResponseData<List<Games>> response = new ResponseData<List<Games>>();
		try {
			response.setData(gamesService.getAll());
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getAllGames()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.getAllGames() :" + e);
		}
		return response;
	}

	//	GET ALL
	@GetMapping("get-all-games-no-icon")
	public ResponseData<List<GamesNoIcon>> getAllGamesNoIcon() {
		ResponseData<List<GamesNoIcon>> response = new ResponseData<List<GamesNoIcon>>();
		try {
			response.setData(gamesService.getAllNoIcon());
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getAllGames()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.getAllGames() :" + e);
		}
		return response;
	}

//	ALL SYNC
	@GetMapping("get-sync-games")
	public ResponseData<?> getSyncGames() {
		ResponseData<?> response = new ResponseData<>();
		try {
			gamesService.syncGames();
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getSyncGames()");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.getSyncGames() :" + e);
		}
		return response;
	}
	// SAVE
	@PostMapping("save-game")
	public ResponseData<String> saveGame(@RequestBody GamesReq req) {
		ResponseData<String> response = new ResponseData<>();
		try {
			response.setData(gamesService.saveGame(req));
			response.setMessage(response.getData());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.saveGame()");
		} catch (Exception e) {
			response.setMessage(SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.saveGame() :" + e);
		}
		return response;
	}
	// EDIT
	@PutMapping("edit-game")
	public ResponseData<String> editGame(@RequestBody GamesReq req) {
		ResponseData<String> response = new ResponseData<>();
		try {
			response.setData(gamesService.editGame(req));
			response.setMessage(response.getData());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.editGame()");
		} catch (Exception e) {
			response.setMessage(EDIT.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.editGame() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@PostMapping("get-games")
	public ResponseData<DataTableResponse<GamesRes>> getGames(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<GamesRes>> response = new ResponseData<DataTableResponse<GamesRes>>();
		try {
			response.setData(gamesService.getPaginateGames(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getGames()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.getGames() :" + e);
		}
		return response;
	}
	
	// GET LIST GAME
	@PostMapping("get-game-list")
	public ResponseData<DataTableResponse<GamesDatatableRes>> getPaginateGamesList(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<GamesDatatableRes>> response = new ResponseData<DataTableResponse<GamesDatatableRes>>();
		try {
			response.setData(gamesService.getPaginateGamesList(param));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getPaginateGamesList()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.getPaginateGamesList() :" + e);
		}
		return response;
	}

	// GET PAGINATE
	@GetMapping("get-games-by-product-type/{code}")
	public ResponseData<List<GamesRes>> getGames(@PathVariable String code) {
		ResponseData<List<GamesRes>> response = new ResponseData<List<GamesRes>>();
		try {
			response.setData(gamesService.getGamesByProductTypeCode(code));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GamesController.getGames()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API GamesController.getGames() :" + e);
		}
		return response;
	}

}
