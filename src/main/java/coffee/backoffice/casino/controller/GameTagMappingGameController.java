package coffee.backoffice.casino.controller;

import coffee.backoffice.casino.model.GameTagMappingGame;
import coffee.backoffice.casino.model.GameTagMappingGameJoin;
import coffee.backoffice.casino.service.GameTagMappingGameService;
import coffee.backoffice.casino.vo.req.GameTagMappingGameReq;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/game-tag-mapping-game")
@Slf4j
public class GameTagMappingGameController {
    @Autowired
    private GameTagMappingGameService gameTagMappingGameService;

    // GET PAGINATE
    @GetMapping("/get-by-game-code/{gameCode}")
    public ResponseData<List<GameTagMappingGameJoin>> getByGameCode(@PathVariable("gameCode")String code) {
        ResponseData<List<GameTagMappingGameJoin>> response = new ResponseData<List<GameTagMappingGameJoin>>();
        try {
            response.setData(gameTagMappingGameService.getByGameCode(code));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameProductTypeController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameProductTypeController.getDataPaginate() :" + e);
        }
        return response;
    }

    // GET PAGINATE
    @GetMapping("/get-by-game-tag-code/{gameCode}")
    public ResponseData<List<GameTagMappingGame>> getByGameTagCode(@PathVariable("gameCode")String code) {
        ResponseData<List<GameTagMappingGame>> response = new ResponseData<List<GameTagMappingGame>>();
        try {
            response.setData(gameTagMappingGameService.getAllByGameTagCode(code));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameProductTypeController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameProductTypeController.getDataPaginate() :" + e);
        }
        return response;
    }

    // GET PAGINATE
    @PostMapping("/{gameCode}")
    public ResponseData<?> getByGameTagCode(@PathVariable("gameCode")String gameCode,@RequestBody List<GameTagMappingGameReq> req) {
        ResponseData<?> response = new ResponseData<>();
        try {
            gameTagMappingGameService.saveAllDirect(gameCode,req);
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API => GameProductTypeController.getDataPaginate()");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API GameProductTypeController.getDataPaginate() :" + e);
        }
        return response;
    }
}
