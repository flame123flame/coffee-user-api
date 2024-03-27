package coffee.website.gamefavorite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coffee.website.gamefavorite.service.GameFavoriteService;
import coffee.website.gamefavorite.vo.req.GameFavoriteRequest;
import coffee.website.gamefavorite.vo.res.GameFavoriteResponse;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/game-favorite/")
@Slf4j
public class GameFavoriteController {

	@Autowired
	private GameFavoriteService gameFavoriteService;

	@GetMapping("get-favorite/{username}")
	@ResponseBody
	public ResponseData<List<GameFavoriteResponse>> getFavorite(@PathVariable("username") String username) {
		ResponseData<List<GameFavoriteResponse>> response = new ResponseData<List<GameFavoriteResponse>>();
		try {
			response.setData(gameFavoriteService.getGameFavoriteList(username));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameFavoriteController.getFavorite()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => GameFavoriteController.getFavorite() : " + e);
		}
		return response;
	}
	
	@GetMapping("get-frequent/{username}")
	@ResponseBody
	public ResponseData<List<GameFavoriteResponse>> getFrequent(@PathVariable("username") String username) {
		ResponseData<List<GameFavoriteResponse>> response = new ResponseData<List<GameFavoriteResponse>>();
		try {
			response.setData(gameFavoriteService.getGameFrequentList(username));
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameFavoriteController.getFrequent()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.info("Error Calling API => GameFavoriteController.getFrequent() : " + e);
		}
		return response;
	}

	@PostMapping("change-favorite")
	@ResponseBody
	public ResponseData<?> changeFavorite(@RequestBody GameFavoriteRequest req) {
		ResponseData<?> response = new ResponseData<>();
		try {
			gameFavoriteService.changeGameFavorite(req);
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API => GameFavoriteController.changeFavorite()");
		} catch (Exception e) {
			response.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
			log.error("Error Calling API => GameFavoriteController.changeFavorite() : " + e);
		}
		return response;
	}
    
}
