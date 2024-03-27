package coffee.backoffice.finance.service;

import coffee.backoffice.casino.model.GameProductType;
import coffee.backoffice.casino.model.Games;
import coffee.backoffice.casino.model.ProviderSummary;
import coffee.backoffice.casino.service.GameProductTypeService;
import coffee.backoffice.casino.service.GamesService;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.finance.model.*;
import coffee.backoffice.finance.repository.dao.AllTransactionDao;
import coffee.backoffice.finance.repository.dao.PlayerValidBetDao;
import coffee.backoffice.finance.repository.jpa.TransactionGameRepository;
import coffee.backoffice.finance.repository.jpa.TransactionListRepository;
import coffee.backoffice.finance.vo.req.AllTransactionReq;
import coffee.backoffice.finance.vo.req.PlayerValidBetReq;
import coffee.backoffice.finance.vo.res.*;
import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.IssueSetting;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.model.RuleSetting;
import coffee.backoffice.promotion.service.PromotionService;
import coffee.backoffice.report.vo.res.ValidBestsWinLossRes;
import coffee.provider.joker.vo.res.TransactionGameListRes;
import coffee.provider.lotto.vo.model.LottoTransaction;
import coffee.provider.sa.vo.model.BetDetail;
import coffee.provider.sbobet.service.SboBetService;
import coffee.provider.sbobet.vo.model.BetList;
import coffee.provider.sexy.vo.model.Transaction;
import coffee.website.affiliate.vo.model.PromotionDateList;
import coffee.website.providers.vo.request.PlayGameRequest;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.LOTTO_STATUS;
import framework.constant.ProjectConstant.PROVIDERS;
import framework.constant.ProjectConstant.TRANSACTION_TYPE;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.ConvertDateUtils;
import framework.utils.GenerateRandomString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AllTransactionService {

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private AllTransactionDao allTransactionDao;

	@Autowired
	private WalletService walletService;

	@Autowired
	private TransactionListRepository transactionListRepository;

	@Autowired
	private TransactionGameRepository transactionGameRepository;

	@Autowired
	private WithdrawConditionService withdrawConditionService;

	@Autowired
	private GamesService gamesService;

	@Autowired
	private GameProductTypeService gameProductTypeService;

	@Autowired
	private GameHistoryService gameHistoryService;

	@Autowired
	private ProviderSummaryService providerSummaryService;

	@Autowired
	private PlayerValidBetDao playerValidBetDao;

	@Autowired
	private SboBetService sboBetService;

	@Value("${path.mxProvider.platform}")
	private String mxPlatform;

	@Value("${path.kmProvider.platform}")
	private String kmPlatform;

	@Value("${path.jiliProvider.platform}")
	private String jiliPlatform;

	@Value("${path.pgProvider.platform}")
	private String pgPlatform;

	@Value("${path.rtProvider.platform}")
	private String rtPlatform;

	public void createTransaction(TransactionList req) {
		if (req != null) {
			transactionListRepository.save(req);
		}
	}
	
	public TransactionList getFirstByUsernameAndTransactionType(String username,String transactionType) {
		return transactionListRepository.findFirstByUsernameAndTransactionTypeOrderByCreatedDateAsc(username,transactionType);
	}

	public List<TransactionList> getAll() {
		return transactionListRepository.findAll();
	}

	public List<TransactionList> getTransactionByUsername(String username) {
		return transactionListRepository.findByUsername(username);
	}

	public List<TransactionList> getTransactionByUsernameAfter(String username) {
		return transactionListRepository.findByUsername(username);
	}

	public List<TransactionList> getTransactionByUsernameAfter(String username, Date after) {
		return transactionListRepository.findByUsernameAndCreatedDateAfter(username, after);
	}

	public List<TransactionList> getTransactionByUsernameBefore(String username, Date before) {
		return transactionListRepository.findByUsernameAndCreatedDateBefore(username, before);
	}

	public List<TransactionList> getTransactionByUsernameBetween(String username, Date start, Date end) {
		return transactionListRepository.findByUsernameAndCreatedDateBetween(username, start, end);
	}

	public List<TransactionList> getTransactionByUsernameAndType(String username, String transactionType) {
		return transactionListRepository.findByUsernameAndTransactionType(username, transactionType);
	}

	public BigDecimal getSumTransactionByUsernameAndType(String username, String transactionType) {
		return transactionListRepository.findSumByUsernameAndTransactionType(username, transactionType);
	}

	public List<TransactionList> getTransactionByUsernameAndTypeAfter(String username, String transactionType,
			Date after) {
		return transactionListRepository.findByUsernameAndTransactionTypeAndCreatedDateAfter(username, transactionType,
				after);
	}

	public List<TransactionList> getTransactionByUsernameAndTypeBefore(String username, String transactionType,
			Date before) {
		return transactionListRepository.findByUsernameAndTransactionTypeAndCreatedDateBefore(username, transactionType,
				before);
	}

	public List<TransactionList> getTransactionByUsernameAndTypeBetween(String username, String transactionType,
			Date start, Date end) {
		return transactionListRepository.findByUsernameAndTransactionTypeAndCreatedDateBetween(username,
				transactionType, start, end);
	}

	public DataTableResponse<TransactionLogRes> getTransactionLog(DatatableRequest req) {
		DataTableResponse<TransactionLogRes> paginateData = allTransactionDao.transactionLogResPaginate(req);
		DataTableResponse<TransactionLogRes> dataTable = new DataTableResponse<>();
		List<TransactionLogRes> data = paginateData.getData();
		dataTable.setRecordsTotal(paginateData.getRecordsTotal());
		dataTable.setDraw(paginateData.getDraw());
		dataTable.setData(data);
		dataTable.setPage(req.getPage());
		return paginateData;
	}

	public List<TransactionGame> getAllGame() {
		return transactionGameRepository.findAll();
	}

	public List<TransactionGame> getGameTransactionByUsername(String username) {
		return transactionGameRepository.findByUsername(username);
	}

	public List<TransactionGame> getGameTransactionByUsernameBefore(String username, Date before) {
		return transactionGameRepository.findByUsernameAndCreatedDateBefore(username, before);
	}

	public List<TransactionGame> getGameTransactionByUsernameAfter(String username, Date after) {
		return transactionGameRepository.findByUsernameAndCreatedDateAfter(username, after);
	}

	public List<TransactionGame> getGameTransactionByUsernameBetween(String username, Date start, Date end) {
		return transactionGameRepository.findByUsernameAndCreatedDateBetween(username, start, end);
	}

	public DataTableResponse<TransactionGameRes> getTransactionGame(DatatableRequest req) {
		DataTableResponse<TransactionGameRes> paginateData = allTransactionDao.transactionGameResPaginate(req);
		DataTableResponse<TransactionGameRes> dataTable = new DataTableResponse<>();
		List<TransactionGameRes> data = paginateData.getData();
		for (TransactionGameRes item : data) {
			Games dataGame = gamesService.getByGameCode(item.getGameCode());
			if (dataGame != null){
				item.setGames(dataGame);
				item.setGameCode(item.getGameCode());
				
				GameProductType dataProduct = gameProductTypeService
						.getGameProductTypeByCode(dataGame.getGameProductTypeCode());
				if (dataProduct != null) {
					item.setGameProductType(dataProduct);
				}
			}
		}
		dataTable.setRecordsTotal(paginateData.getRecordsTotal());
		dataTable.setDraw(paginateData.getDraw());
		dataTable.setData(data);
		dataTable.setPage(req.getPage());
		return paginateData;
	}


	public TransactionGame findLastTransationProviderByCreatedDate(String provider) {
		return allTransactionDao.findTopOneProviderCreatedDateDesc(provider);
	}
	
	public TransactionGame findLastTransationProviderByUpdatedDate(String provider) {
		return allTransactionDao.findTopOneProviderUpdatedDateDesc(provider);
	}

	public List<TransactionGame> getGameTransactionByUsernameAndGameCode(String username, String gameCode) {
		return transactionGameRepository.findByUsernameAndGameCode(username, gameCode);
	}

	public List<TransactionGame> getGameTransactionByUsernameAndGameProvider(String username, String gameProvider) {
		return transactionGameRepository.findByUsernameAndGameProvider(username, gameProvider);
	}

	public List<TransactionGame> getGameTransactionByUsernameAndGameProviderAfer(String username, String gameProvider,
			Date createdDate) {
		return transactionGameRepository.findByUsernameAndGameProviderAndCreatedDateAfter(username, gameProvider,
				createdDate);
	}

	public List<TransactionGame> getGameTransactionByUsernameAndGameProviderBetween(String username,
			String gameProvider, Date start, Date end) {
		return transactionGameRepository.findByUsernameAndGameProviderAndCreatedDateBetween(username, gameProvider,
				start, end);
	}

	public List<TransactionGame> getGameTransactionByGameProviderBetween(String gameProvider, Date start, Date end) {
		return transactionGameRepository.findByGameProviderAndCreatedDateBetween(gameProvider,start, end);
	}

	public BigDecimal getGameBetsTransactionByUsernameAfterDate(String username, Date after) {
		List<TransactionGame> transactionList = transactionGameRepository.findByUsername(username);
		BigDecimal sum = BigDecimal.ZERO;
		for (TransactionGame tranasction : transactionList) {
			if (tranasction.getCreatedDate().compareTo(after) >= 0) {
				sum = sum.add(tranasction.getBet());
			}
		}
		return sum;
	}

	public void createTransactionJokerUser(List<TransactionGameListRes> gameList, Date start, Date end) {
		TransactionGame data = null;
		List<TransactionGame> tempTransactionGameList = allTransactionDao.findLastMinuteProvider(PROVIDERS.JOKER, start,
				end);
		if (gameList != null) {
			for (TransactionGameListRes dataTGLR : gameList) {
				String username = dataTGLR.getUsername().toLowerCase();
				Calendar date1 = Calendar.getInstance();
				date1.setTime(dataTGLR.getTime());
				date1.set(Calendar.SECOND, 0);
				date1.set(Calendar.MILLISECOND, 0);

				boolean checDup = false;
				for (TransactionGame temp : tempTransactionGameList) {

					Calendar date2 = Calendar.getInstance();
					date2.setTime(temp.getCreatedDate());
					date2.set(Calendar.SECOND, 0);
					date2.set(Calendar.MILLISECOND, 0);

					if (username.equals(temp.getUsername()) && date1.getTime().compareTo(date2.getTime()) == 0) {
						checDup = true;
						break;
					}
				}

				if (checDup) {
					continue;
				}

				BigDecimal winLoss = dataTGLR.getResult().subtract(dataTGLR.getAmount());
				data = new TransactionGame();
				data.setBet(dataTGLR.getAmount());
				data.setValidBet(dataTGLR.getAmount());
				data.setBetResult(dataTGLR.getResult());
				data.setCreatedDate(dataTGLR.getTime());
				data.setGameCode(dataTGLR.getGameCode());
				data.setGameSessionId(dataTGLR.getRoundID());
				data.setWinLoss(winLoss);
				data.setUsername(username);
				data.setGameProvider(PROVIDERS.JOKER);
				data.setCreatedBy("_system");
				transactionGameRepository.save(data);

				ProviderSummary summary = providerSummaryService.getProviderSummary(PROVIDERS.JOKER, username);
				if (summary != null) {
					summary.setWinLoss(summary.getWinLoss().add(winLoss));
					summary.setLossDaily(summary.getLossDaily().add(winLoss));
					if (winLoss.compareTo(BigDecimal.ZERO) < 0) {
						summary.setTotalLoss(summary.getTotalLoss().add(winLoss));
					} else {
						summary.setTotalWin(summary.getTotalWin().add(winLoss));
					}
					providerSummaryService.updateProviderSummary(PROVIDERS.JOKER, username, data.getBet(),
							dataTGLR.getAmount(), summary);
				} else {
					providerSummaryService.createProviderSummary(PROVIDERS.JOKER, username, data.getBet(),
							dataTGLR.getAmount(), winLoss);
				}
				List<PromotionMapping> mappingList = promotionService.findMappingByUsernameAndStatus(username,
						ProjectConstant.STATUS.ACTIVE);
				checkTurnover(dataTGLR.getAmount(), username, PROVIDERS.JOKER, mappingList, data.getCreatedDate());
			}
		}
	}

	public void createTransactionMxUser(List<Transaction> gameList) {
		TransactionGame data = null;
		if (gameList != null) {
			for (Transaction game : gameList) {
				String username = game.getUserId().toLowerCase();
				Calendar date1 = ConvertDateUtils.ISO8601ToCalendar(game.getTxTime());
				String providerCode = "";

				if (game.getPlatform().equals(mxPlatform)) {
					providerCode = PROVIDERS.MX;
				}
				if (game.getPlatform().equals(kmPlatform)) {
					providerCode = PROVIDERS.KM;
				}
				if (game.getPlatform().equals(jiliPlatform)) {
					providerCode = PROVIDERS.JILI;
				}
				if (game.getPlatform().equals(pgPlatform)) {
					providerCode = PROVIDERS.PG;
				}
				if (game.getPlatform().equals(rtPlatform)) {
					providerCode = PROVIDERS.RT;
				}
				Boolean tempBet = true;
				data = transactionGameRepository.findByGameSessionIdAndGameProvider(game.getPlatformTxId(),
						providerCode);
				if (data == null) {
					data = new TransactionGame();
				}else{
					tempBet = data.getBet().compareTo(game.getRealBetAmount()) != 0;
				}

				BigDecimal winLoss = game.getRealWinAmount().subtract(game.getRealBetAmount());
				data.setBet(game.getRealBetAmount());
				data.setValidBet(game.getTurnover());
				data.setBetResult(game.getRealWinAmount());
				data.setCreatedDate(date1.getTime());
				data.setGameCode(game.getGameCode());
				data.setGameSessionId(game.getPlatformTxId());
				data.setWinLoss(winLoss);
				data.setUsername(username);
				data.setGameProvider(providerCode);
				data.setCreatedBy("_system");
				transactionGameRepository.save(data);

				if (winLoss.compareTo(BigDecimal.ZERO) != 0 || PROVIDERS.JILI.equals(providerCode)
						|| PROVIDERS.RT.equals(providerCode) || PROVIDERS.PG.equals(providerCode)
						|| PROVIDERS.KM.equals(providerCode) && tempBet) {
					ProviderSummary summary = providerSummaryService.getProviderSummary(providerCode, username);
					if (summary != null) {
						summary.setWinLoss(summary.getWinLoss().add(winLoss));
						summary.setLossDaily(summary.getLossDaily().add(winLoss));
						if (winLoss.compareTo(BigDecimal.ZERO) < 0) {
							summary.setTotalLoss(summary.getTotalLoss().add(winLoss));
						} else {
							summary.setTotalWin(summary.getTotalWin().add(winLoss));
						}
						providerSummaryService.updateProviderSummary(providerCode, username, data.getBet(),
								game.getTurnover(), summary);
					} else {
						providerSummaryService.createProviderSummary(providerCode, username, data.getBet(),
								game.getTurnover(), winLoss);
					}
					List<PromotionMapping> mappingList = promotionService.findMappingByUsernameAndStatus(username,
							ProjectConstant.STATUS.ACTIVE);
					checkTurnover(game.getTurnover(), username, providerCode, mappingList, data.getCreatedDate());
				}
			}
		}
	}

	public void createTransactionSboUser(List<BetList> result, Date start, Date end) {
		TransactionGame data = null;
		PlayGameRequest gameHis = null;
		if (result != null) {
			for (BetList game : result) {
				String username = game.getUsername().toLowerCase();
				Calendar date1 = Calendar.getInstance();
				String changeDate = game.getModifyDate().substring(0, 19);
				LocalDateTime dateTime = LocalDateTime.parse(changeDate);
				date1.set(dateTime.getYear(), dateTime.getMonthValue() - 1, dateTime.getDayOfMonth(),
						dateTime.getHour() + 11, dateTime.getMinute() + 1, dateTime.getSecond());

				Boolean isUpdate = false;
				BigDecimal winLoss = game.getWinLost();
				String tempGameResult = "";
				data = transactionGameRepository.findByGameSessionIdAndGameProvider(game.getRefNo(), PROVIDERS.SBO);
				if (data != null) {
					tempGameResult = data.getGameResult();
					data.setBet(game.getStake());
					data.setValidBet(winLoss.abs());
					data.setBetResult(winLoss.add(game.getStake()));
//					data.setCreatedDate(date1.getTime());
					data.setWinLoss(winLoss);
					data.setGameResult(game.getStatus().trim());

					log.info(" [SBO Transaction] set old data");
					isUpdate = !tempGameResult.equals(game.getStatus().trim());
					log.info(" [SBO Transacion] Refno =" + game.getRefNo() + " , isUpdate =" + isUpdate
							+ " , gameStatus=" + game.getStatus() + " , tempGameStatus=" + tempGameResult);
					if (isUpdate) {
						String remark = game.getStatus() + " | คะแนนจบเกมส์ FT("
								+ game.getSubBet().get(0).getFtScore() + ")";
						log.info(" [SBO Transaction] set remark");
						GameHistory temp = gameHistoryService.getGameHistoryByOrder(game.getRefNo(), PROVIDERS.SBO);
						BigDecimal creditResult = temp.getCreditResult().add(winLoss.add(game.getStake()));
						gameHistoryService.updateGameHistory(game.getRefNo(), username, PROVIDERS.SBO, creditResult,
								remark,game.getStatus().trim());
						log.info(" [SBO Transaction] update history by refno");

//						GameHistory lasgHisProvider = gameHistoryService.getLastGameHistoryUsernameAndProvider(username,PROVIDERS.SBO);
						GameHistory lasgHisProvider = gameHistoryService
								.getGameHistoryUsernameAndProviderAndGameCode(username, PROVIDERS.SBO, "SportsBook");
						log.info(" [SBO Transaction] find last login provider");
						if (data.getBetResult().compareTo(BigDecimal.ZERO) > 0
								&& lasgHisProvider.getPlayStatus().equals("OFFLINE")) {
							log.info(" [SBO Transaction] update wallet on exit game");
							sboBetService.updateCredit(username, GenerateRandomString.generateUUID(),
									data.getBetResult());
						}
					}

				} else {
					data = new TransactionGame();
					data.setBet(game.getStake());
					data.setValidBet(winLoss.abs());
					data.setBetResult(winLoss.add(game.getStake()));
					data.setCreatedDate(date1.getTime());
					data.setGameCode(game.getSportsType());
					data.setGameSessionId(game.getRefNo());
					data.setWinLoss(winLoss);
					data.setUsername(username);
					data.setGameProvider(PROVIDERS.SBO);
					data.setCreatedBy("_system");
					data.setGameResult(game.getStatus());

					gameHis = new PlayGameRequest();
					gameHis.setOrderNo(game.getRefNo());
					gameHis.setGameName(game.getSubBet().get(0).getMatch());
					gameHis.setGameCode(game.getSportsType());
					gameHis.setBalance(game.getStake().toString());
					gameHis.setProviderCode(PROVIDERS.SBO);
					gameHis.setUsername(username);
					gameHis.setRemark(" กำลังแข่ง");

					log.info(" [SBO Transaction] set new data");

					GameHistory gameHisProvider = gameHistoryService
							.getGameHistoryUsernameAndProviderAndGameCode(username, PROVIDERS.SBO, "SportsBook");
					log.info(" [SBO Transaction] gameHisProvider : " + gameHisProvider);
					BigDecimal creditBalance = gameHisProvider.getCreditIn().subtract(game.getStake());
					Calendar gameHisDate = Calendar.getInstance();
					gameHisDate.setTime(gameHisProvider.getCreatedDate());
					gameHisDate.add(Calendar.SECOND, 1);

					List<GameHistory> gameHisList = gameHistoryService.getGameHistoryUsernameAndProviderAfter(username,
							PROVIDERS.SBO, gameHisDate.getTime());
					for (GameHistory temp : gameHisList) {
						creditBalance = creditBalance.subtract(temp.getCreditIn());
					}
					gameHistoryService.createGameHistory(gameHis, creditBalance);

				}
				transactionGameRepository.save(data);

				if (isUpdate) {

					ProviderSummary summary = providerSummaryService.getProviderSummary(PROVIDERS.SBO, username);
					if (summary != null) {
						summary.setWinLoss(summary.getWinLoss().add(winLoss));
						summary.setLossDaily(summary.getLossDaily().add(winLoss));
						if (game.getWinLost().compareTo(BigDecimal.ZERO) < 0) {
							summary.setTotalLoss(summary.getTotalLoss().add(winLoss));
						} else {
							summary.setTotalWin(summary.getTotalWin().add(winLoss));
						}
						providerSummaryService.updateProviderSummary(PROVIDERS.SBO, username, game.getStake(),
								winLoss.abs(), summary);
					} else {
						providerSummaryService.createProviderSummary(PROVIDERS.SBO, username, game.getStake(),
								winLoss.abs(), winLoss);
					}
					List<PromotionMapping> mappingList = promotionService.findMappingByUsernameAndStatus(username,
							ProjectConstant.STATUS.ACTIVE);
					checkTurnover(winLoss.abs(), username, PROVIDERS.SBO, mappingList, data.getCreatedDate());
				}
			}
		}
	}

	public void createTransactionLottoUser(List<LottoTransaction> result) {
		TransactionGame data = null;
		if (result != null) {
			for (LottoTransaction game : result) {
				data = transactionGameRepository.findByGameSessionIdAndGameProvider(game.getLottoGroupTransactionCode(),
						PROVIDERS.LOTTO);
				
				if (data != null) {
					BigDecimal winLoss = BigDecimal.ZERO;
					BigDecimal turnOver = BigDecimal.ZERO;
					Boolean isUpdate = !data.getGameResult().equals(game.getStatus());
					
					if (LOTTO_STATUS.WIN.equals(game.getStatus())) {
						winLoss = data.getWinLoss().add((game.getPrizeCorrect().subtract(game.getPayCost())));
						turnOver = game.getPayCost();
						data.setBetResult(data.getBetResult().add(game.getPrizeCorrect()));
						data.setValidBet(data.getValidBet().add(turnOver));
						
					}
					
					if (LOTTO_STATUS.LOSE.equals(game.getStatus())) {
						winLoss = data.getWinLoss().subtract(game.getPayCost());
						turnOver = game.getPayCost();
						data.setBetResult(data.getBetResult().add(BigDecimal.ZERO));
						data.setValidBet(data.getValidBet().add(turnOver));
					}
					
					if (LOTTO_STATUS.CANCEL.equals(game.getStatus())) {
						data.setBet(BigDecimal.ZERO);
						data.setValidBet(turnOver);
						data.setBetResult(BigDecimal.ZERO);
						data.setBalance(data.getBalance().add(game.getPayCost()));
						winLoss = BigDecimal.ZERO;
					}
					
					data.setWinLoss(winLoss);
					data.setGameResult(game.getStatus());
					data.setUpdatedDate(game.getUpdatedDate());
					transactionGameRepository.save(data);
					
					if(isUpdate) {
						ProviderSummary summary = providerSummaryService.getProviderSummary(PROVIDERS.LOTTO,
								game.getUsername());
						if (summary != null) {
							summary.setWinLoss(summary.getWinLoss().add(winLoss));
							summary.setLossDaily(summary.getLossDaily().add(winLoss));
							if (winLoss.compareTo(BigDecimal.ZERO) < 0) {
								summary.setTotalLoss(summary.getTotalLoss().add(winLoss));
							} else {
								summary.setTotalWin(summary.getTotalWin().add(winLoss));
							}
							providerSummaryService.updateProviderSummary(PROVIDERS.LOTTO, game.getUsername(),
									game.getPayCost(), turnOver, summary);
						} else {
							providerSummaryService.createProviderSummary(PROVIDERS.LOTTO, game.getUsername(),
									game.getPayCost(), turnOver, winLoss);
						}
						List<PromotionMapping> mappingList = promotionService
								.findMappingByUsernameAndStatus(game.getUsername(), ProjectConstant.STATUS.ACTIVE);
						checkTurnover(turnOver, game.getUsername(), PROVIDERS.LOTTO, mappingList,
								game.getUpdatedDate());
					}
				}
			}
		}
	}

	public void createTransactionSaUser(List<BetDetail> gameList) {
		TransactionGame data = null;
		if (gameList != null) {
			for (BetDetail game : gameList) {
				String username = game.getUsername().toLowerCase();
				String subStringTime = game.getPayoutTime().substring(0, 19);
				subStringTime = subStringTime.replace("T", " ");
				Date betTime = ConvertDateUtils.parseStringToDate(subStringTime, ConvertDateUtils.YYYY_MM_DD_HHMMSS,
						ConvertDateUtils.LOCAL_EN);

				data = transactionGameRepository.findByGameSessionIdAndGameProvider(String.valueOf(game.getBetID()),
						PROVIDERS.SA);
				if (data != null) {
					continue;
				}

				BigDecimal winLoss = game.getResultAmount();
				data = new TransactionGame();
				data.setBet(game.getBetAmount());
				data.setValidBet(game.getRolling());
				data.setBetResult(winLoss.add(game.getBetAmount()));
				data.setCreatedDate(betTime);
				data.setGameCode(game.getGameType());
				data.setGameSessionId(String.valueOf(game.getBetID()));
				data.setWinLoss(winLoss);
				data.setUsername(username);
				data.setGameProvider(PROVIDERS.SA);
				data.setCreatedBy("_system");
				transactionGameRepository.save(data);

				if (winLoss.compareTo(BigDecimal.ZERO) != 0) {
					ProviderSummary summary = providerSummaryService.getProviderSummary(PROVIDERS.SA, username);
					if (summary != null) {
						summary.setWinLoss(summary.getWinLoss().add(winLoss));
						summary.setLossDaily(summary.getLossDaily().add(winLoss));
						if (winLoss.compareTo(BigDecimal.ZERO) < 0) {
							summary.setTotalLoss(summary.getTotalLoss().add(winLoss));
						} else {
							summary.setTotalWin(summary.getTotalWin().add(winLoss));
						}
						providerSummaryService.updateProviderSummary(PROVIDERS.SA, username, data.getBet(),
								game.getRolling(), summary);
					} else {
						providerSummaryService.createProviderSummary(PROVIDERS.SA, username, data.getBet(),
								game.getRolling(), winLoss);
					}
					List<PromotionMapping> mappingList = promotionService.findMappingByUsernameAndStatus(username,
							ProjectConstant.STATUS.ACTIVE);
					checkTurnover(game.getRolling(), username, PROVIDERS.SA, mappingList, data.getCreatedDate());
				}
			}
		}
	}

	private void checkTurnover(BigDecimal turnOverIn, String username, String providerCode,
			List<PromotionMapping> mappingList, Date transactionDate) {
		PromotionMapping mapping = new PromotionMapping();
		WithdrawCondition withdrawCondition = null;

		if (mappingList.size() > 0) {
			mapping = mappingList.get(0);
			withdrawCondition = withdrawConditionService.getWithdrawConditionByUsernameAndConditionType(username,
					ProjectConstant.WITHDRAW_CONDITION.PROMOTION, ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);

			if (withdrawCondition != null) {

				RuleSetting rule = promotionService.getRuleSettingByPromoCode(mapping.getPromoCode());
				if (PromotionConstant.WalletType.BALANCE.equals(rule.getReceiveBonusWallet())) {

					BigDecimal turnOver = turnOverIn.add(withdrawCondition.getCurrentTurnover());
					withdrawCondition.setCurrentTurnover(turnOver);
					withdrawCondition.setUpdatedDate(transactionDate);
					if (turnOver.compareTo(withdrawCondition.getTargetTurnover()) >= 0) {
						withdrawCondition.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
						mapping.setStatus(ProjectConstant.STATUS.INACTIVE);
						mapping.setUpdatedBy("_system");
						mapping.setUpdatedDate(transactionDate);

						promotionService.editMapping(mapping);
					}
					withdrawConditionService.saveWithdrawCondition(withdrawCondition);
				} else {
					boolean checkInPromotion = false;
					List<IssueSetting> listProvider = promotionService
							.getIssueSettingByPromoCode(mapping.getPromoCode());
					for (IssueSetting temp : listProvider) {
						if (providerCode.equals(temp.getProviderCode())) {
							checkInPromotion = true;
							break;
						}
					}

					if (checkInPromotion) {

						BigDecimal turnOver = turnOverIn.add(withdrawCondition.getCurrentTurnover());
						withdrawCondition.setCurrentTurnover(turnOver);
						withdrawCondition.setUpdatedDate(transactionDate);
						if (turnOver.compareTo(withdrawCondition.getTargetTurnover()) >= 0) {
							withdrawCondition.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
							mapping.setStatus(ProjectConstant.STATUS.INACTIVE);
							mapping.setUpdatedBy("_system");
							mapping.setUpdatedDate(transactionDate);

							promotionService.editMapping(mapping);
							Wallet temp = walletService.findWalletData(username);
							if (temp.getBonus().compareTo(BigDecimal.ZERO) > 0) {
								BigDecimal balance = temp.getBalance().add(temp.getBonus());
								temp.setBalance(balance);
								temp.setBonus(BigDecimal.ZERO);
								walletService.editWallet(temp);
							}
						}
						withdrawConditionService.saveWithdrawCondition(withdrawCondition);
					}
				}
			}
		} else {
			withdrawCondition = withdrawConditionService.getWithdrawConditionByUsernameAndConditionType(username,
					ProjectConstant.WITHDRAW_CONDITION.GENERAL, ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);
			if (withdrawCondition != null) {

				BigDecimal turnOver = turnOverIn.add(withdrawCondition.getCurrentTurnover());
				withdrawCondition.setCurrentTurnover(turnOver);
				withdrawCondition.setUpdatedDate(transactionDate);
				if (turnOver.compareTo(withdrawCondition.getTargetTurnover()) >= 0) {
					withdrawCondition.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
				}
				withdrawConditionService.saveWithdrawCondition(withdrawCondition);
			}
		}
	}

	public BigDecimal totalAddBalanceByType(String transactionType, Date startDate, Date endDate) {
		BigDecimal temp = transactionListRepository.totalAddBalanceByType(startDate, endDate, transactionType);
		if (temp == null)
			temp = BigDecimal.ZERO;
		return temp;
	}

	public BigDecimal totalSubBalanceByType(String transactionType, Date startDate, Date endDate) {
		BigDecimal temp = transactionListRepository.totalSubBalanceByType(startDate, endDate, transactionType);
		if (temp == null)
			temp = BigDecimal.ZERO;
		return temp;
	}

	public BigDecimal getTotalValidBets(Date startDate, Date endDate) {
		BigDecimal temp = transactionGameRepository.findTotalValidBet(startDate, endDate);
		if (temp == null)
			temp = BigDecimal.ZERO;
		return temp;
	}

	public BigDecimal getTotalWinLoss(Date startDate, Date endDate) {
		BigDecimal temp = transactionGameRepository.findTotalWinLoss(startDate, endDate);
		if (temp == null)
			temp = BigDecimal.ZERO;
		return temp;
	}

	@Transactional
	public List<PlayerValidBetRes> getPlayerValidBetProvider(PlayerValidBetReq req) {
		System.out.println(req.getStartDate());
		System.out.println(req.getEndDate());
		System.out.println(req.getUsername());
		List<PlayerValidBetRes> res = playerValidBetDao.findTotalValidBet(req.getStartDate(), req.getEndDate(),
				req.getUsername());
		return res;
	}

	public List<PlayerValidBetProductRes> getPlayerValidBetProduct(PlayerValidBetReq req) {
		System.out.println(req.getStartDate());
		System.out.println(req.getEndDate());
		System.out.println(req.getUsername());
		List<PlayerValidBetProductRes> res = playerValidBetDao.findTotalValidBetProduct(req.getStartDate(), req.getEndDate(),
				req.getUsername());
		return res;
	}

	public ValidBestsWinLossRes getValidBestsWinLoss(Date firstDayDate, Date lastDayDate, String providerCode) {
		ValidBestsWinLossRes res = new ValidBestsWinLossRes();
		BigDecimal totalBet = transactionGameRepository.totalBetByProvider(firstDayDate, lastDayDate, providerCode);
		BigDecimal totalWinLoss = transactionGameRepository.totalWinLossByProvider(firstDayDate, lastDayDate,
				providerCode);
		if (totalBet != null) {
			res.setValidbet(totalBet);
		} else {
			res.setValidbet(BigDecimal.ZERO);
		}
		if (totalWinLoss != null) {
			res.setTotalwinloss(totalWinLoss);
		} else {
			res.setTotalwinloss(BigDecimal.ZERO);
		}
		return res;
	}

	public BigDecimal getCompanyProfitLoss(Date startDate, Date endDate) {
		BigDecimal totalCompany = BigDecimal.ZERO;
		BigDecimal totalDeposit = transactionListRepository.findTotalAddBalanceByType(startDate, endDate,
				TRANSACTION_TYPE.DEPOSIT);
		if (totalDeposit == null)
			totalDeposit = BigDecimal.ZERO;
		BigDecimal totalCompanyProfit = totalDeposit;
		BigDecimal totalPromotionBalance = transactionListRepository.findTotalAddBalanceByType(startDate, endDate,
				TRANSACTION_TYPE.PROMOTION_BALANCE);
		if (totalPromotionBalance == null)
			totalPromotionBalance = BigDecimal.ZERO;
		BigDecimal totalPromotionBonus = transactionListRepository.findTotalAddBalanceByType(startDate, endDate,
				TRANSACTION_TYPE.PROMOTION_BONUS);
		if (totalPromotionBonus == null)
			totalPromotionBonus = BigDecimal.ZERO;
		BigDecimal totalRebate = transactionListRepository.findTotalAddBalanceByType(startDate, endDate,
				TRANSACTION_TYPE.REBATE);
		if (totalRebate == null)
			totalRebate = BigDecimal.ZERO;
		BigDecimal totalCahsback = transactionListRepository.findTotalAddBalanceByType(startDate, endDate,
				TRANSACTION_TYPE.CASHBACK);
		if (totalCahsback == null)
			totalCahsback = BigDecimal.ZERO;
		BigDecimal totalWithdraw = transactionListRepository.totalSubBalanceByType(startDate, endDate,
				TRANSACTION_TYPE.WITHDRAW);
		if (totalWithdraw == null)
			totalWithdraw = BigDecimal.ZERO;
		BigDecimal totalWithdrawAf = transactionListRepository.totalSubBalanceByType(startDate, endDate,
				TRANSACTION_TYPE.WITHDRAW_AF);
		if (totalWithdrawAf == null)
			totalWithdrawAf = BigDecimal.ZERO;
		BigDecimal totalCompanyLoss = totalPromotionBalance.add(totalPromotionBonus).add(totalRebate).add(totalCahsback)
				.add(totalWithdraw).add(totalWithdrawAf);
		totalCompany = totalCompanyProfit.subtract(totalCompanyLoss);
		return totalCompany;
	}

	public Integer getCountPlayer(Date firstDayDate, Date lastDayDate, String providerCode) {
		Integer countPlayer = transactionGameRepository.countPlayer(firstDayDate, lastDayDate, providerCode);
		return countPlayer;
	}

	public Integer getCountTxn(Date firstDayDate, Date lastDayDate, String providerCode) {
		Integer countTxn = transactionGameRepository.countTxn(firstDayDate, lastDayDate, providerCode);
		return countTxn;
	}

	public BigDecimal sumTransactionAffiliate(String username, List<String> gameCode, List<String> providers,
			Date lastUpdate, Date nowDate, List<PromotionDateList> oldPromotionDateList) {
		return allTransactionDao.sumTransactionByUsernameAndGameCodeAndProvider(username, gameCode, providers,
				lastUpdate, nowDate, oldPromotionDateList);
	}

	public AllTransactionRes getAllTransaction(AllTransactionReq req) {
		AllTransactionRes res = new AllTransactionRes();

		BigDecimal sumDeposit = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.DEPOSIT);
		res.setCompanyDeposit(sumDeposit);
		Integer countDeposit = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.DEPOSIT);
		res.setCountDeposit(countDeposit);
		Integer peopleDeposit = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.DEPOSIT);
		res.setPeopleDeposit(peopleDeposit);

		BigDecimal sumManual = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.MANUAL_ADD);
		res.setManualAdd(sumManual);
		Integer countManual = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.MANUAL_ADD);
		res.setCountManualAdd(countManual);
		Integer peopleManual = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.MANUAL_ADD);
		res.setPeopleManualAdd(peopleManual);

		BigDecimal sumBalance = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.PROMOTION_BALANCE);
		res.setPromotionBalance(sumBalance);
		Integer countBalance = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.PROMOTION_BALANCE);
		res.setCountPromotionBalance(countBalance);
		Integer peopleBalance = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.PROMOTION_BALANCE);
		res.setPeoplePromotionBalance(peopleBalance);

		BigDecimal sumBonus = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.PROMOTION_BONUS);
		res.setPromotionBonus(sumBonus);
		Integer countBonus = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.PROMOTION_BONUS);
		res.setCountPromotionBonus(countBonus);
		Integer peopleBonus = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.PROMOTION_BONUS);
		res.setPeoplePromotionBonus(peopleBonus);

		BigDecimal sumWithdraw = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.WITHDRAW);
		res.setWithdraw(sumWithdraw);
		Integer countWithdraw = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.WITHDRAW);
		res.setCountWithdraw(countWithdraw);
		Integer peopleWithdraw = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.WITHDRAW);
		res.setPeopleWithdraw(peopleWithdraw);

		BigDecimal sumAf = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.WITHDRAW_AF);
		res.setWithdrawAf(sumAf);
		Integer countAf = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.WITHDRAW_AF);
		res.setCountWithdrawAf(countAf);
		Integer peopleAf = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.WITHDRAW_AF);
		res.setPeopleWithdrawAf(peopleAf);

		BigDecimal sumSub = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.MANUAL_SUB);
		res.setManualSub(sumSub);
		Integer countSub = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.MANUAL_SUB);
		res.setCountManualSub(countSub);
		Integer peopleSub = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.MANUAL_SUB);
		res.setPeopleManualSub(peopleSub);

		BigDecimal sumRebate = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.REBATE);
		res.setRebate(sumRebate);
		Integer countRebate = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.REBATE);
		res.setCountRebate(countRebate);
		Integer peopleRebate = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.REBATE);
		res.setPeopleRebate(peopleRebate);

		BigDecimal sumCashback = transactionListRepository.findBySum(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.CASHBACK);
		res.setCashback(sumCashback);
		Integer countCashback = transactionListRepository.findByCount(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.CASHBACK);
		res.setCountCashback(countCashback);
		Integer peopleCashback = transactionListRepository.findByPeople(req.getStartDate(), req.getEndDate(),
				TRANSACTION_TYPE.CASHBACK);
		res.setPeopleCashback(peopleCashback);

		BigDecimal sumTotal = transactionListRepository.findBySumTotal(req.getStartDate(), req.getEndDate());
		res.setTotal(sumTotal);

		return res;
	}

	public Integer countUsernameByTypeGroupByUsername(Date startDate, Date endDate, String transactionType) {
		Integer countUsername = transactionListRepository.countUsernameByTypeGroupByUsername(startDate, endDate,
				transactionType);
		return countUsername;
	}

	public Integer countTransactionTypeByType(Date startDate, Date endDate, String transactionType) {
		Integer countTransaction = transactionListRepository.countTransactionTypeByType(startDate, endDate,
				transactionType);
		return countTransaction;
	}
}
