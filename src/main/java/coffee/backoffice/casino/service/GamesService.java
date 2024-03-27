package coffee.backoffice.casino.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import coffee.backoffice.casino.model.*;
import coffee.backoffice.casino.repository.jpa.GameProductTypeNoIconJpa;
import coffee.backoffice.casino.repository.jpa.GameProviderNoIconJpa;
import coffee.backoffice.casino.repository.jpa.GamesNoIconJpa;
import coffee.backoffice.casino.vo.res.GamesDatatableRes;
import coffee.backoffice.lotto.service.LottoService;
import framework.model.ImgUploadRes;
import framework.utils.ImgUploadUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.repository.dao.GamesDao;
import coffee.backoffice.casino.repository.jpa.GamesJpa;
import coffee.backoffice.casino.vo.req.GamesReq;
import coffee.backoffice.casino.vo.res.GamesRes;
import coffee.provider.joker.service.JokerProviderService;
import coffee.provider.joker.vo.model.JokerGamesList;
import coffee.provider.joker.vo.res.JokerGamesListRes;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.EDIT;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE.SAVE;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class GamesService {
    @Autowired
    private GamesDao gamesDao;

    @Autowired
    private GamesJpa gamesJpa;
    @Autowired
    private GamesNoIconJpa gamesNoIconJpa;
    @Autowired
    private GameProviderNoIconJpa gameProviderNoIconJpa;
    @Autowired
    private GameProductTypeNoIconJpa gameProductTypeNoIconJpa;

    @Autowired
    private ProductMappingProviderService productMappingProviderService;

    @Autowired
    private GameProviderService gameProviderService;

    @Autowired
    private GameProductTypeService gameProductTypeService;

    @Autowired
    private JokerProviderService jokerProviderService;


    @Autowired
    private GameTagMappingGameService gameTagMappingGameService;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ImgUploadUtils imgUploadUtils;

    @Autowired
    private LottoService lottoService;

    public void syncGames() throws Exception {
        // Provider JOKER
        getJoker();
        lottoService.syncLotto();
    }

    public void getJoker() {
        String productType = "";
        List<String> ptList = productMappingProviderService.getProductByProvider(PROVIDERS.JOKER);

        JokerGamesList dataGamePV = jokerProviderService.listGames();
        if (dataGamePV.getListGames() != null) {
            for (JokerGamesListRes listGame : dataGamePV.getListGames()) {
                Games saveGame = gamesJpa.findByGameCode(listGame.getGameCode());
                if (ptList.size() > 0) {
                    productType = ptList.get(0);
                } else {
                    productType = listGame.getGameType();
                }
                if (saveGame != null) {
                    saveGame.setProviderCode(PROVIDERS.JOKER);
                    saveGame.setNameEn(listGame.getGameName());
                    saveGame.setDisplayName(listGame.getGameName());
                    saveGame.setGameCode(listGame.getGameCode());
                    saveGame.setGameProductTypeCode(productType);
                    saveGame.setImage1(listGame.getImage1());
                    saveGame.setImage2(listGame.getImage2());
                    saveGame.setUpdatedAt(new Date());
                    saveGame.setUpdatedBy(UserLoginUtil.getUsername());
                } else {
                    saveGame = new Games();
                    saveGame.setProviderCode(PROVIDERS.JOKER);
                    saveGame.setNameEn(listGame.getGameName());
                    saveGame.setDisplayName(listGame.getGameName());
                    saveGame.setGameCode(listGame.getGameCode());
                    saveGame.setGameProductTypeCode(productType);
                    saveGame.setImage1(listGame.getImage1());
                    saveGame.setImage2(listGame.getImage2());
                    saveGame.setCreatedAt(new Date());
                    saveGame.setUpdatedBy(UserLoginUtil.getUsername());
                    saveGame.setStatus(true);
                    saveGame.setEnable(true);
                }
                gamesJpa.save(saveGame);
            }
        }
    }

    public DataTableResponse<GamesRes> getPaginateGames(DatatableRequest req) throws Exception {
        DataTableResponse<GamesRes> dataTable = new DataTableResponse<>();
        DataTableResponse<GamesRes> tag = new DataTableResponse<>();
        tag = gamesDao.getPaginateGames(req);
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        dataTable.setData(tag.getData());
        return dataTable;
    }

    public List<Games> getAll() {
        return gamesJpa.findAll();
    }

    public List<Games> getAllByProviderCode(String providerCode) {
        return gamesJpa.findByProviderCodeAndStatus(providerCode, true);
    }

    @Transactional
    public String saveGame(GamesReq req) throws Exception {
        Games tempData = gamesJpa.findByGameCode(req.getGameCode());
        if (tempData == null) {
            Games data = new Games();
            data.setGameCode(req.getGameCode());
            data.setGameGroupCode(req.getGameGroupCode());
            data.setGameProductTypeCode(req.getGameProductTypeCode());
            data.setProviderCode(req.getProviderCode());
            data.setNameEn(req.getNameEn());
            data.setNameTh(req.getNameTh());
            data.setDisplayName(req.getDisplayName());
            data.setStatus(req.getStatus());
            data.setEnable(true);
            data.setCreatedAt(new Date());
            data.setUpdatedBy(UserLoginUtil.getUsername());
            gamesJpa.save(data);
            gameTagMappingGameService.saveAll(data.getGameCode(), req.getGameTagMappingGame());
            if (req.getImage1() != null) {
                ImgUploadRes path = imgUploadUtils.uploadImg("game-img", req.getImage1(), "games");
                data.setImage1(path.getData().getSavedPath());
            }
            if (req.getImage2() != null) {
                ImgUploadRes path = imgUploadUtils.uploadImg("game-img", req.getImage2(), "games");
                data.setImage2(path.getData().getSavedPath());
            }
        } else {
            return SAVE.DUPLICATE_DATA;
        }
        return SAVE.SUCCESS;
    }

    @Transactional
    public String editGame(GamesReq req) throws Exception {
        Games data = gamesJpa.findByGameCode(req.getGameCode());
        data.setGameProductTypeCode(req.getGameProductTypeCode());
        data.setProviderCode(req.getProviderCode());
        data.setNameEn(req.getNameEn());
        data.setNameTh(req.getNameTh());
        data.setDisplayName(req.getDisplayName());
        data.setStatus(req.getStatus());
        data.setGameGroupCode(req.getGameGroupCode());
//		data.setEnable(req.getEnable());
        data.setUpdatedAt(new Date());
        data.setUpdatedBy(UserLoginUtil.getUsername());
        gamesJpa.save(data);
        gameTagMappingGameService.saveAll(req.getGameCode(), req.getGameTagMappingGame());
        if (req.getImage1() != null) {
            ImgUploadRes path = imgUploadUtils.uploadImg("game-img", req.getImage1(), "games");
            data.setImage1(path.getData().getSavedPath());
        }
        if (req.getImage2() != null) {
            ImgUploadRes path = imgUploadUtils.uploadImg("game-img", req.getImage2(), "games");
            data.setImage2(path.getData().getSavedPath());
        }
        return EDIT.SUCCESS;
    }

    @Transactional
    public void deleteGameByGameCode(String gameCode) {
        gamesJpa.deleteByGameCode(gameCode);
        gameTagMappingGameService.deleteGameMappingGroup(gameCode);
    }

    public DataTableResponse<GamesDatatableRes> getPaginateGamesList(DatatableRequest req) throws Exception {
        DataTableResponse<GamesDatatableRes> dataTable = new DataTableResponse<>();
        DataTableResponse<GamesDatatableRes> tag = gamesDao.getPaginateGamesList(req);
        List<GamesDatatableRes> data = tag.getData();
        data.stream().forEach(item -> {
            List<GameTagMappingGameJoin> gameTagMappingGames = gameTagMappingGameService.getByGameCode(item.getCode());
            if (gameTagMappingGames != null && gameTagMappingGames.size() != 0) {
                List<String> stringList = new ArrayList<>();
                gameTagMappingGames.stream().forEach(i -> {
                    if (i.getGameTag() != null) {
                        stringList.add(i.getGameTag().getNameTh());
                    }
                });
                item.setGameTag(String.join(",", stringList));
            }
        });
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        dataTable.setData(data);
        return dataTable;
    }

    public List<GamesRes> getGamesByProductTypeCode(String code) {
        List<GamesRes> gamesRes = gamesDao.findAllByProductType(code);
        return gamesRes;
    }

    public Games getByGameCode(String code) {
        return gamesJpa.findByGameCode(code);
    }

    public Long countAllGameProviderByCode(String code) {
        return gamesJpa.countAllByProviderCode(code);
    }

    public List<GamesNoIcon> getAllNoIcon() {
        return gamesNoIconJpa.findAll();
    }

    public List<String> getGameCodeByGameGroup(List<String> gameGroupCode) {
        return gamesJpa.findGameCodeByGameGroupCodeIn(gameGroupCode);
    }

}
