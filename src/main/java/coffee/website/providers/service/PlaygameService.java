package coffee.website.providers.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import coffee.backoffice.casino.model.*;
import coffee.backoffice.casino.repository.jpa.GameTagMappingGameJpa;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.service.GameGroupService;
import coffee.backoffice.casino.service.GameProductTypeService;
import coffee.backoffice.casino.service.GameProviderService;
import coffee.backoffice.casino.service.GameTagMappingGameService;
import coffee.backoffice.casino.service.GameTagService;
import coffee.backoffice.casino.service.GamesService;
import coffee.backoffice.casino.service.ProductMappingProviderService;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.service.GameHistoryService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import coffee.provider.joker.service.JokerProviderService;
import coffee.provider.sa.service.SaGamingService;
import coffee.provider.sbobet.service.SboBetService;
import coffee.provider.sbobet.vo.req.SboBetRequest;
import coffee.provider.sexy.service.MxProviderService;
import coffee.provider.sexy.vo.req.MxRequest;
import coffee.website.gamefavorite.service.GameFavoriteService;
import coffee.website.providers.vo.request.PlayGameRequest;
import coffee.website.providers.vo.response.GameListResponse;
import coffee.website.providers.vo.response.GroupGameListResponse;
import coffee.website.providers.vo.response.PlayGameResponse;
import coffee.website.providers.vo.response.ProductListResponse;
import coffee.website.providers.vo.response.ProviderListResponse;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.utils.GenerateRandomString;

@Service
public class PlaygameService {

    @Value("${path.mxProvider.gameType}")
    private String mxGameType;

    @Value("${path.mxProvider.platform}")
    private String mxPlatform;

    @Value("${path.kmProvider.gameType}")
    private String kmGameType;

    @Value("${path.kmProvider.platform}")
    private String kmPlatform;

    @Value("${path.jiliProvider.gameType}")
    private String jiliGameType;

    @Value("${path.jiliProvider.platform}")
    private String jiliPlatform;

    @Value("${path.pgProvider.gameType}")
    private String pgGameType;

    @Value("${path.pgProvider.platform}")
    private String pgPlatform;

    @Value("${path.rtProvider.gameType}")
    private String rtGameType;

    @Value("${path.rtProvider.platform}")
    private String rtPlatform;

    @Autowired
    private JokerProviderService jokerProviderService;

    @Autowired
    private MxProviderService mxProviderService;

    @Autowired
    private SboBetService sboBetService;

    @Autowired
    private SaGamingService saGamingService;

    @Autowired
    private MxProviderService MxProviderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private GameFavoriteService gameFavoriteService;

    @Autowired
    private GameHistoryService gameHistoryService;

    @Autowired
    private ProductMappingProviderService productMappingProviderService;

    @Autowired
    private GameProductTypeService gameProductTypeService;

    @Autowired
    private GameProviderService gameProviderService;

    @Autowired
    private GamesService gamesService;

    @Autowired
    private GameGroupService gameGroupService;

    @Autowired
    private GameTagService gameTagService;

    @Autowired
    private GameTagMappingGameService gameTagMappingGameService;

    public List<GroupGameListResponse> gameList() {
        List<GameTag> allGroup = gameTagService.getAll();
        List<GroupGameListResponse> res = new ArrayList<GroupGameListResponse>();

        for (GameTag item : allGroup) {
            GroupGameListResponse gameListResponse = new GroupGameListResponse();
            gameListResponse.setNameEn(item.getNameEn());
            gameListResponse.setNameTh(item.getNameTh());
            gameListResponse.setGameGroupCode(item.getCode());
            List<GameTagMappingGameJoin> gameGroupMappingGames;
            if (item.getUserView().equals("2VIEW")) {
                gameGroupMappingGames = gameTagMappingGameService.getTop2ByGameGroupCode(item.getCode());
            } else {
                gameGroupMappingGames = gameTagMappingGameService.getTop8ByGameGroupCode(item.getCode());
            }
            List<GameListResponse> gameList = new ArrayList<GameListResponse>();
            List<Games> listGames = new ArrayList<>();
            for (GameTagMappingGameJoin ite : gameGroupMappingGames) {
                listGames.add(ite.getGame());
            }
            for (Games temp : listGames) {
                GameListResponse data = new GameListResponse();
                data.setGameCode(temp.getGameCode());
                data.setGameName(temp.getDisplayName());
                data.setGameType(temp.getGameProductTypeCode());
                data.setProviderCode(temp.getProviderCode());
                data.setImage1(temp.getImage1());
                data.setImage2(temp.getImage2());
                gameList.add(data);
            }
            gameListResponse.setTotalGame(gameTagMappingGameService.countAllByGameGroupCode(item.getCode()));
            gameListResponse.setGameList(gameList);
            gameListResponse.setUserView(item.getUserView());
            res.add(gameListResponse);
        }

        return res;
    }

    public List<ProviderListResponse> providerList(String product) {
        List<ProviderListResponse> res = new ArrayList<ProviderListResponse>();
        List<String> providerList = productMappingProviderService.getListProviderByProduct(product);
        ProviderListResponse temp = null;
        for (String code : providerList) {
            GameProvider provider = gameProviderService.getGameProviderByCode(code);
            temp = new ProviderListResponse();
            temp.setProviderCode(provider.getCode());
            temp.setDisplayName(provider.getNameTh());
            temp.setOpenType(provider.getOpenType());
            temp.setPortraitImgUrl(provider.getIconPortrait());
            temp.setLandscapeImgUrl(provider.getIconLandscape());
            temp.setTotalGame(gamesService.countAllGameProviderByCode(code));
            res.add(temp);
        }
        return res;
    }

    public List<ProductListResponse> productList() {
        List<ProductListResponse> res = new ArrayList<ProductListResponse>();
        List<GameProductType> productList = gameProductTypeService.getAll();
        for (GameProductType temp : productList) {
            ProductListResponse product = new ProductListResponse();
            product.setProductCode(temp.getCode());
            product.setNameEn(temp.getNameEn());
            product.setNameTh(temp.getNameTh());
            product.setIconUrl(temp.getIconUrl());
            res.add(product);
        }
        return res;
    }

    private PlayGameRequest calculateBalance(PlayGameRequest req) {

        Wallet temp = walletService.findWalletData(req.getUsername());
        BigDecimal balance = BigDecimal.ZERO;
        Boolean checkBonus = false;
        checkBonus = walletService.checkUseBonus(req.getUsername(), req.getProviderCode());
        if (checkBonus) {
            balance = temp.getBalance().add(temp.getBonus());
        } else {
            balance = temp.getBalance();
        }
        req.setCheckBonus(checkBonus);
        req.setBalance(balance.toString());

        return req;
    }

    public Boolean withdrawCreditFromProvider(Customer customer, String orderId) {
        Boolean status = true;
        if (StringUtils.isNoneBlank(customer.getLastProvider())) {

            switch (customer.getLastProvider()) {
                case PROVIDERS.JOKER:
                    status = jokerProviderService.updateCredit(customer, orderId);
                    break;
                case PROVIDERS.MX:
                    MxProviderService.updateCredit(customer, PROVIDERS.MX, orderId);
                    break;
                case PROVIDERS.KM:
                    MxProviderService.updateCredit(customer, PROVIDERS.KM, orderId);
                    break;
                case PROVIDERS.JILI:
                    MxProviderService.updateCredit(customer, PROVIDERS.JILI, orderId);
                    break;
                case PROVIDERS.PG:
                    MxProviderService.updateCredit(customer, PROVIDERS.PG, orderId);
                    break;
                case PROVIDERS.RT:
                    MxProviderService.updateCredit(customer, PROVIDERS.RT, orderId);
                    break;
                case PROVIDERS.SBO:
                    sboBetService.updateCredit(customer, orderId);
                    break;
                case PROVIDERS.SA:
                    saGamingService.updateCredit(customer, PROVIDERS.SA);
                    break;
            }
        }
        return status;
    }

    public PlayGameResponse playGame(PlayGameRequest req) {

        PlayGameResponse res = new PlayGameResponse();
        String mainUrl = "";

        Customer customer = customerService.getByUsername(req.getUsername());
        String uuid = GenerateRandomString.generateUUID();
        req.setOrderNo(uuid);
        Boolean status = withdrawCreditFromProvider(customer, uuid);
        if (!status) {
            return res;
        }

        calculateBalance(req);

        switch (req.getProviderCode()) {
            case PROVIDERS.JOKER:
                mainUrl = jokerProviderService.playGame(req.getUsername(), req.getGameCode(), req.getBalance(), req.getOrderNo());

                break;
            case PROVIDERS.MX:
                MxRequest mx = new MxRequest();
                mx.setAmount(req.getBalance());
                mx.setUserId(req.getUsername());
                mx.setProvider(req.getProviderCode());
                mx.setOrderId(req.getOrderNo());
                mx.setGameType(mxGameType);
                mx.setPlatform(mxPlatform);
                mx.setGameCode("MX-LIVE-001");
                mainUrl = mxProviderService.playGame(mx, PROVIDERS.MX);

//			req.setGameName("Baccarat Classic");
//			req.setGameCode("MX-LIVE-001");

                req.setGameName("Casino Classic");
                req.setGameCode("MX-LOBBY");

                break;
            case PROVIDERS.KM:
                MxRequest km = new MxRequest();
                km.setAmount(req.getBalance());
                km.setUserId(req.getUsername());
                km.setProvider(req.getProviderCode());
                km.setOrderId(req.getOrderNo());
                km.setGameType(kmGameType);
                km.setPlatform(kmPlatform);
                km.setGameCode(req.getGameCode());
                mainUrl = mxProviderService.playGame(km, PROVIDERS.KM);

                req.setGameName("TABLE");
                req.setGameCode("KINGMAKER");

                break;
            case PROVIDERS.JILI:
                MxRequest jili = new MxRequest();
                jili.setAmount(req.getBalance());
                jili.setUserId(req.getUsername());
                jili.setProvider(req.getProviderCode());
                jili.setOrderId(req.getOrderNo());
                jili.setGameType(jiliGameType);
                jili.setPlatform(jiliPlatform);
                jili.setGameCode(req.getGameCode());
                mainUrl = mxProviderService.playGame(jili, PROVIDERS.JILI);

                break;
            case PROVIDERS.PG:
                MxRequest pg = new MxRequest();
                pg.setAmount(req.getBalance());
                pg.setUserId(req.getUsername());
                pg.setProvider(req.getProviderCode());
                pg.setOrderId(req.getOrderNo());
                pg.setGameType(pgGameType);
                pg.setPlatform(pgPlatform);
                pg.setGameCode(req.getGameCode());
                mainUrl = mxProviderService.playGame(pg, PROVIDERS.PG);

                break;
            case PROVIDERS.RT:
                MxRequest rt = new MxRequest();
                rt.setAmount(req.getBalance());
                rt.setUserId(req.getUsername());
                rt.setProvider(req.getProviderCode());
                rt.setOrderId(req.getOrderNo());
                rt.setGameType(rtGameType);
                rt.setPlatform(rtPlatform);
                rt.setGameCode(req.getGameCode());
                mainUrl = mxProviderService.playGame(rt, PROVIDERS.RT);

                break;
            case PROVIDERS.SBO:
//			sboBetService.updateCredit(customer, uuid);
                SboBetRequest sbo = new SboBetRequest();
                sbo.setUsername(req.getUsername());
                sbo.setBalance(new BigDecimal(req.getBalance()));
                sbo.setOrderId(req.getOrderNo());
                mainUrl = sboBetService.playGame(sbo);

                req.setGameName("SportsBook");
                req.setGameCode("SportsBook");

                break;
            case PROVIDERS.SA:
                mainUrl = saGamingService.playGame(req.getUsername(), new BigDecimal(req.getBalance()));

                req.setGameName("Live Game");
                req.setGameCode("");

                break;
            default:
                break;
        }

        GameProvider pd = gameProviderService.getGameProviderByCode(req.getProviderCode());
        if (pd != null) {
            req.setIconUrl(pd.getIconPortrait());
        }

        if (StringUtils.isNoneBlank(mainUrl) && mainUrl.length() > 10) {

            customer.setLastProvider(req.getProviderCode());
            customerService.saveCustomer(customer);
            gameHistoryService.createGameHistory(req);
            gameFavoriteService.countGame(req.getUsername(), req.getGameName(), req.getGameCode(),
                    req.getProviderCode(), req.getIconUrl());
        }
        res.setUrl(mainUrl);

        return res;
    }

    public List<GameListResponse> gameProviderList(String provider) {
        List<GameListResponse> res = new ArrayList<>();
        List<Games> listGames = gamesService.getAllByProviderCode(provider);
        for (Games temp : listGames) {
            GameListResponse data = new GameListResponse();
            data.setGameCode(temp.getGameCode());
            data.setGameName(temp.getDisplayName());
            data.setGameType(temp.getGameProductTypeCode());
            data.setProviderCode(temp.getProviderCode());
            data.setImage1(temp.getImage1());
            data.setImage2(temp.getImage2());
            List<GameTagMappingGame> gameTagMappingGames = gameTagMappingGameService.getAllByGameCode(temp.getGameCode());
            if (gameTagMappingGames != null && gameTagMappingGames.size() > 0) {
                List<String> strings = gameTagMappingGames.stream().map(GameTagMappingGame::getGameTagCode).collect(Collectors.toList());
                data.setGameTag(String.join(",",strings));
            }
            res.add(data);
        }
        return res;
    }

    public List<GameListResponse> gameGroupList(String gameGroup) {

        List<GameTagMappingGameJoin> gameGroupMappingGames = gameTagMappingGameService.getALLByGameGroupCode(gameGroup);
        List<GameListResponse> gameList = new ArrayList<GameListResponse>();
        List<Games> listGames = new ArrayList<>();
        for (GameTagMappingGameJoin ite : gameGroupMappingGames) {
            listGames.add(ite.getGame());
        }
        for (Games temp : listGames) {
            GameListResponse data = new GameListResponse();
            data.setGameCode(temp.getGameCode());
            data.setGameName(temp.getDisplayName());
            data.setGameType(temp.getGameProductTypeCode());
            data.setProviderCode(temp.getProviderCode());
            data.setImage1(temp.getImage1());
            data.setImage2(temp.getImage2());
            gameList.add(data);
        }
        return gameList;
    }
}
