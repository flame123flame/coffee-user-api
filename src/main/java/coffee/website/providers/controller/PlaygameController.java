package coffee.website.providers.controller;

import java.util.List;

import coffee.website.providers.vo.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.website.providers.service.PlaygameService;
import coffee.website.providers.vo.request.PlayGameRequest;
import framework.constant.ResponseConstant;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/playgame")
@Slf4j
public class PlaygameController {
	
	@Autowired
	private PlaygameService playgameService;

	@PostMapping("/launch-game")
	public ResponseData<PlayGameResponse> contractProvider(@RequestBody PlayGameRequest req){
		ResponseData<PlayGameResponse> responseData = new ResponseData<PlayGameResponse>();
		try {
			responseData.setData(playgameService.playGame(req));
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		}catch (Exception e) {
			log.error("ProvidersController: contractProvider ", e);
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/game-list")
	public ResponseData<List<GroupGameListResponse>> gameList(){
		ResponseData<List<GroupGameListResponse>> responseData = new ResponseData<List<GroupGameListResponse>>();
		try {
			responseData.setData(playgameService.gameList());
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("ProvidersController: gameList SUCCESS CALLING");
		}catch (Exception e) {
			log.error("ProvidersController: gameList ", e);
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	
	
	@GetMapping("/game-list/provider/{provider}")
	public ResponseData<List<GameListResponse>> gameProviderList(@PathVariable("provider") String provider){
		ResponseData<List<GameListResponse>> responseData = new ResponseData<List<GameListResponse>>();
		try {
			responseData.setData(playgameService.gameProviderList(provider));
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("ProvidersController: gameList SUCCESS CALLING");
		}catch (Exception e) {
			log.error("ProvidersController: gameList ", e);
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/game-list/group/{gameGroup}")
	public ResponseData<List<GameListResponse>> gameGroupList(@PathVariable("gameGroup") String gameGroup){
		ResponseData<List<GameListResponse>> responseData = new ResponseData<List<GameListResponse>>();
		try {
			responseData.setData(playgameService.gameGroupList(gameGroup));
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("ProvidersController: gameList SUCCESS CALLING");
		}catch (Exception e) {
			log.error("ProvidersController: gameList ", e);
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("/provider-list/{product}")
	public ResponseData<List<ProviderListResponse>> providerList(@PathVariable("product") String product){
		ResponseData<List<ProviderListResponse>> responseData = new ResponseData<List<ProviderListResponse>>();
		try {
			responseData.setData(playgameService.providerList(product));
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("ProvidersController: providerList SUCCESS CALLING");
		}catch (Exception e) {
			log.error("ProvidersController: providerList ", e);
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	
	
	@GetMapping("/product-list")
	public ResponseData<List<ProductListResponse>> productList(){
		ResponseData<List<ProductListResponse>> responseData = new ResponseData<List<ProductListResponse>>();
		try {
			responseData.setData(playgameService.productList());
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("ProvidersController: productList SUCCESS CALLING");
		}catch (Exception e) {
			log.error("ProvidersController: productList ", e);
			responseData.setMessage(ResponseConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
