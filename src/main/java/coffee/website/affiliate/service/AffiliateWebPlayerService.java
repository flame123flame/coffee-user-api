package coffee.website.affiliate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.model.Affiliate;
import coffee.backoffice.affiliate.model.AffiliateChannel;
import coffee.backoffice.affiliate.model.AffiliateIncomeChannel;
import coffee.backoffice.affiliate.model.AffiliateNetwork;
import coffee.backoffice.affiliate.repository.dao.AffiliateDao;
import coffee.backoffice.affiliate.repository.dao.AffiliateNetworkDao;
import coffee.backoffice.affiliate.repository.jpa.AffiliateIncomeChannelRepository;
import coffee.backoffice.affiliate.repository.jpa.AffiliateNetworkRepository;
import coffee.backoffice.affiliate.repository.jpa.AffiliateRepository;
import coffee.backoffice.affiliate.service.AffiliateChannelService;
import coffee.backoffice.affiliate.service.AffiliateService;
import coffee.backoffice.affiliate.vo.req.WithdrawReq;
import coffee.backoffice.casino.service.GameGroupMappingProviderService;
import coffee.backoffice.casino.service.GamesService;
import coffee.backoffice.casino.service.ProductMappingProviderService;
import coffee.backoffice.casino.service.ProviderSummaryService;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.service.PromotionService;
import coffee.website.affiliate.vo.model.ChannelDetail;
import coffee.website.affiliate.vo.model.PromotionDateList;
import coffee.website.affiliate.vo.model.WithdrawDetail;
import coffee.website.affiliate.vo.res.AffliateDetailResponse;
import coffee.website.affiliate.vo.res.AffliateDownlineResponse;
import framework.constant.ProjectConstant;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AffiliateWebPlayerService {
	
    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AffiliateNetworkRepository affiliateNetworkRepository;

    @Autowired
    private AffiliateDao affiliateDao;
    
    @Autowired
    private AffiliateChannelService affiliateChannelService;

    @Autowired
    private AffiliateService affiliateService;

    @Autowired
    private AffiliateNetworkDao affiliateNetworkDao;

    @Autowired
    private WalletService walletService;
    
    @Autowired
    private GameGroupMappingProviderService gameGroupMappingProviderService;

    @Autowired
    private AllTransactionService allTransactionService;
    
    @Autowired
    private ProductMappingProviderService productMappingProviderService;

    @Autowired
    private ProviderSummaryService providerSummaryService;
    
    @Autowired
    private PromotionService promotionService;
    
    @Autowired
    private GamesService gamesService;
    
    @Autowired
    private AffiliateIncomeChannelRepository affiliateIncomeChannelRepository;
    
    public String codeAffiliate(String username) {
    	AffiliateNetwork affliateNetwork = affiliateService.getAffiliateNetworkByUsername(username);
    	String affiliateCode = affliateNetwork.getAffiliateCode();
        return affiliateCode;
    }
    
    public WithdrawDetail witdrawDetailAffiliate(String username) {
    	AffiliateNetwork affliateNetwork = affiliateService.getAffiliateNetworkByUsername(username);
    	String affiliateCode = affliateNetwork.getAffiliateCode();
    	Affiliate result = affiliateRepository.findByAffiliateCode(affiliateCode);
    	WithdrawDetail res = new WithdrawDetail();
    	res.setTotalIncome(result.getIncomeOne().add(result.getIncomeTwo()));
    	res.setIncome(result.getTotalIncome());
    	List<TransactionList> list = allTransactionService.getTransactionByUsernameAndType(username, ProjectConstant.TRANSACTION_TYPE.WITHDRAW_AF);
    	res.setListHistory(list);
        return res;
    }

    public void setTrigger(String affiliateCode) {
        Affiliate result = affiliateRepository.findByAffiliateCode(affiliateCode);
        if (result != null) {
            int count = result.getClickCount();
            result.setClickCount(count + 1);
            affiliateRepository.save(result);
        }
    }

    public String withdrawAffiliate(WithdrawReq req) {
        
        AffiliateNetwork affliateNetwork = affiliateService.getAffiliateNetworkByUsername(req.getUsername());
        String affiliateCode = affliateNetwork.getAffiliateCode();
        Affiliate affliate = affiliateService.getDetailAffiliate(affiliateCode);
        
        if(req.getAmount().compareTo(affliate.getTotalIncome()) < 0) {
        	Wallet wallet = walletService.findWalletData(req.getUsername());
            BigDecimal mainBalance = wallet.getBalance();
            BigDecimal beforeAff = affliate.getTotalIncome();
            
        	wallet.setBalance(mainBalance.add(req.getAmount()));
        	affliate.setTotalIncome(affliate.getTotalIncome().subtract(req.getAmount()));
        	
            tranferTransaction(ProjectConstant.TRANSACTION_TYPE.WITHDRAW_AF, req.getUsername(),
                    ProjectConstant.WALLET.AFFILIATE_WALLET, ProjectConstant.WALLET.MAIN_WALLET, req.getAmount(),
                    wallet.getBalance(), beforeAff,affliate.getTotalIncome());
            
            affiliateService.update(affliate);
            walletService.editWallet(wallet);
            
            return "ถอนสำเร็จ";
        }else {
        	return "ยอดรายได้ไม่พอถอน";
        }

    }

    public void tranferTransaction(String transactionType, String username, String from, String to,
                                   BigDecimal tranferAmount, BigDecimal totalBalance, BigDecimal before, BigDecimal after) {
        TransactionList transaction = new TransactionList();
        transaction.setTransactionDate(new Date());
        transaction.setUsername(username);
        transaction.setTransactionType(transactionType);
        transaction.setFromSender(from);
        transaction.setTranferAmount(tranferAmount);
        transaction.setToRecive(to);
        transaction.setTotalBalance(totalBalance);
        transaction.setStatus(ProjectConstant.STATUS.SUCCESS);
        transaction.setUpdatedBy(UserLoginUtil.getUsername());
        transaction.setBeforeBalance(before);
        transaction.setAfterBalance(after);
        transaction.setTransactionAmount(tranferAmount);

        allTransactionService.createTransaction(transaction);
    }
    
    public AffliateDetailResponse getAffiliateDetail(String username) {
        AffliateDetailResponse resAf = new AffliateDetailResponse();
        AffiliateNetwork affliateNetwork = affiliateService.getAffiliateNetworkByUsername(username);
        if (affliateNetwork != null) {
            String affiliateCode = affliateNetwork.getAffiliateCode();
            Affiliate affliate = affiliateService.getDetailAffiliate(affiliateCode);

            if (affliate != null) {
                List<ChannelDetail> listChannelDetail = new ArrayList<ChannelDetail>();
                List<AffiliateChannel> affList = affiliateChannelService.getAffiliateChannelByGroup(affliate.getAffiliateGroupCode());
                List<AffiliateNetwork> affNetList = affiliateService.getAffiliateNetworkByUpline(affiliateCode);
                List<AffiliateNetwork> affNetLv2List = affiliateNetworkDao.findAffiliateNetworkTwoByCode(affiliateCode);

                calculateAffiliate(username, affliate,affliateNetwork, listChannelDetail, affList,affNetList, affNetLv2List);
                
                // Set detail
                resAf.setRecommendCode(affiliateCode);
                resAf.setDownLineOneCount(affNetList.size());
                resAf.setDownLineTwoCount(affNetLv2List.size());
                resAf.setCurrentOneIncome(affliate.getIncomeOne());
                resAf.setCurrentTwoIncome(affliate.getIncomeTwo());
                resAf.setTotalIncome(affliate.getTotalIncome());
                resAf.setChannelDetailList(listChannelDetail);
            }
        }
        return resAf;
    }

	private void calculateAffiliate(String username, Affiliate affliate,AffiliateNetwork affliateNetwork,List<ChannelDetail> listChannelDetail, List<AffiliateChannel> affList,
			List<AffiliateNetwork> affNetList, List<AffiliateNetwork> affNetLv2List) {
		
		BigDecimal totalIncome1 = BigDecimal.ZERO;
        BigDecimal totalIncome2 = BigDecimal.ZERO;

		for (AffiliateChannel channel : affList) {
	        ChannelDetail channelDetail = new ChannelDetail();
	        channelDetail.setChannelName(channel.getChannelName());
	        channelDetail.setShareRateOne(channel.getShareRateOne());
	        channelDetail.setShareRateTwo(channel.getShareRateTwo());

	        BigDecimal totalBetChannel1 = BigDecimal.ZERO;
	        BigDecimal totalBetChannel2 = BigDecimal.ZERO;
	        BigDecimal totalIncomeChannel = BigDecimal.ZERO;
	        
	        BigDecimal incomeChannel1 = BigDecimal.ZERO;
	        BigDecimal incomeChannel2 = BigDecimal.ZERO;

	        // Affiliate Network Level 1
	        for (AffiliateNetwork network : affNetList) {
	        	
	        	Affiliate affliateDown1 = affiliateService.getDetailAffiliate(network.getAffiliateCode());
	        	Date lastUpdateAff = affliateDown1.getUpdatedDate();
	    		
	    		if(lastUpdateAff == null) {
	    			lastUpdateAff = network.getRegisterDate();
	    		}
	    		List<PromotionMapping> promotionList = new ArrayList<PromotionMapping>();
	    		PromotionMapping promotion = promotionService.findLastMappingByUsernameAndStatusAndDateActiveAfterOrderByDateActive(network.getUsername(),ProjectConstant.STATUS.ACTIVE,lastUpdateAff);
	        	promotionList = promotionService.findMappingByUsernameAndStatusAndDateActiveBeforeOrderByDateActive(network.getUsername(),ProjectConstant.STATUS.INACTIVE,new Date());
        		List<PromotionDateList> promotionDateList = fillterPromotion(promotionList);
        		BigDecimal sumQuery = BigDecimal.ZERO;
        		Boolean checkRegister = true;
        		Date forQuery = new Date();
//				Date lastUpdateQuery = affliate.getUpdatedDate();
        		Date lastUpdateQuery = null;
        		List<String> promoCode = new ArrayList<String>();
        		
        		if(promotion != null) {
        			forQuery = promotion.getDateActive();
        			promoCode.add(promotion.getPromoCode());
        		}
        	
        		for(PromotionMapping temp:promotionList) {
        			promoCode.add(temp.getPromoCode());
        		}
        		
        		List<Promotion> findRegister = promotionService.getByPromoTypeAndPromoCodeIn(PromotionConstant.Type.registration,promoCode);
        		if(findRegister != null && findRegister.size() > 0) {
        			TransactionList transaction = allTransactionService.getFirstByUsernameAndTransactionType(network.getUsername(), ProjectConstant.TRANSACTION_TYPE.DEPOSIT);
        			if(transaction != null) {
        				lastUpdateQuery = transaction.getCreatedDate();
//						if(transaction.getCreatedDate().compareTo(lastUpdateQuery) > 0){
//							
//						}
        			}else {
        				checkRegister = false;
        			}
        		}
        		
        		if(checkRegister) {
        			sumQuery = summaryInNetwork(promotionDateList, lastUpdateQuery,forQuery, channel, network);
            		
    	            if (sumQuery != null) {
    	                BigDecimal percentage = channel.getShareRateOne().divide(new BigDecimal(100));
    	                totalBetChannel1 = totalBetChannel1.add(sumQuery);
    	                incomeChannel1 = sumQuery.multiply(percentage);
    	                totalIncome1 = totalIncome1.add(incomeChannel1);
    	                totalIncomeChannel = totalIncomeChannel.add(incomeChannel1);
    	            }
        		}
	        }

	        // Affiliate Network Level 2
	        for (AffiliateNetwork network : affNetLv2List) {
	        	
	        	Affiliate affliateDown2 = affiliateService.getDetailAffiliate(network.getAffiliateCode());
	        	Date lastUpdateAff = affliateDown2.getUpdatedDate();
	    		
	    		if(lastUpdateAff == null) {
	    			lastUpdateAff = network.getRegisterDate();
	    		}
	        	
	    		List<PromotionMapping> promotionList = new ArrayList<PromotionMapping>();
	    		PromotionMapping promotion = promotionService.findLastMappingByUsernameAndStatusAndDateActiveAfterOrderByDateActive(network.getUsername(),ProjectConstant.STATUS.ACTIVE,lastUpdateAff);
	        	promotionList = promotionService.findMappingByUsernameAndStatusAndDateActiveBeforeOrderByDateActive(network.getUsername(),ProjectConstant.STATUS.INACTIVE,new Date());
        		List<PromotionDateList> promotionDateList = fillterPromotion(promotionList);
        		BigDecimal sumQuery = BigDecimal.ZERO;
        		Boolean checkRegister = true;
        		Date forQuery = new Date();
//				Date lastUpdateQuery = affliate.getUpdatedDate();
        		Date lastUpdateQuery = null;
        		List<String> promoCode = new ArrayList<String>();
        		
        		if(promotion != null) {
        			forQuery = promotion.getDateActive();
        		}
        		
        		for(PromotionMapping temp:promotionList) {
        			promoCode.add(temp.getPromoCode());
        		}
        		
        		List<Promotion> findRegister = promotionService.getByPromoTypeAndPromoCodeIn(PromotionConstant.Type.registration,promoCode);
        		if(findRegister != null && findRegister.size() > 0) {
        			TransactionList transaction = allTransactionService.getFirstByUsernameAndTransactionType(network.getUsername(), ProjectConstant.TRANSACTION_TYPE.DEPOSIT);
        			if(transaction != null ) {
        				lastUpdateQuery = transaction.getCreatedDate();
//						if(transaction.getCreatedDate().compareTo(lastUpdateQuery) > 0){
//							lastUpdateQuery = transaction.getCreatedDate();
//						}
        			}else {
        				checkRegister = false;
        			}
        		}
        		
        		if(checkRegister) {
        			sumQuery = summaryInNetwork(promotionDateList, lastUpdateQuery,forQuery, channel, network);
            		
            		if (sumQuery != null) {
    	                BigDecimal percentage = channel.getShareRateTwo().divide(new BigDecimal(100));
    	                totalBetChannel2 = totalBetChannel2.add(sumQuery);
    	                incomeChannel2 = sumQuery.multiply(percentage);
    	                totalIncome2 = totalIncome2.add(incomeChannel2);
    	                totalIncomeChannel = totalIncomeChannel.add(incomeChannel2);
    	            }
        		}
	        }
	        
	        AffiliateIncomeChannel dataIncomeChannel = affiliateIncomeChannelRepository.findByAffiliateChannelCodeAndUsername(channel.getAffiliateChannelCode(), username);
	        if(dataIncomeChannel != null) {
//	        	dataIncomeChannel.setTotalBetOne(dataIncomeChannel.getTotalBetOne().add(totalBetChannel1));
//	        	dataIncomeChannel.setTotalBetTwo(dataIncomeChannel.getTotalBetTwo().add(totalBetChannel2));
//	        	dataIncomeChannel.setTotalIncome(dataIncomeChannel.getTotalIncome().add(totalIncomeChannel));
	        	dataIncomeChannel.setUpdatedBy("_system");
	        	dataIncomeChannel.setUpdatedDate(new Date());
	        }else {
	        	dataIncomeChannel = new AffiliateIncomeChannel();
//	        	dataIncomeChannel.setTotalBetOne(totalBetChannel1);
//	        	dataIncomeChannel.setTotalBetTwo(totalBetChannel2);
//	        	dataIncomeChannel.setTotalIncome(totalIncomeChannel);
	        	dataIncomeChannel.setAffiliateChannelCode(channel.getAffiliateChannelCode());
	        	dataIncomeChannel.setUsername(username);
	        	dataIncomeChannel.setCreatedBy("_system");
	        }
        	
        	dataIncomeChannel.setTotalBetOne(totalBetChannel1);
        	dataIncomeChannel.setTotalBetTwo(totalBetChannel2);
        	dataIncomeChannel.setTotalIncome(totalIncomeChannel);
	        affiliateIncomeChannelRepository.save(dataIncomeChannel);
	        
	        channelDetail.setTotalBetOne(dataIncomeChannel.getTotalBetOne());
	        channelDetail.setTotalBetTwo(dataIncomeChannel.getTotalBetTwo());
	        channelDetail.setTotalIncome(dataIncomeChannel.getTotalIncome());
	        listChannelDetail.add(channelDetail);
		}

//	    affliate.setTotalIncome(affliate.getTotalIncome().add(totalIncome1.add(totalIncome2)));
//	    affliate.setIncomeOne(affliate.getIncomeOne().add(totalIncome1));
//	    affliate.setIncomeTwo(affliate.getIncomeTwo().add(totalIncome2));
		affliate.setIncomeOne(totalIncome1);
		affliate.setIncomeTwo(totalIncome2);

		BigDecimal wdAff = allTransactionService.getSumTransactionByUsernameAndType(username,ProjectConstant.TRANSACTION_TYPE.WITHDRAW_AF);
	    if(wdAff != null) {
	    	affliate.setTotalIncome(totalIncome1.add(totalIncome2).subtract(wdAff));
	    }else {
	    	affliate.setTotalIncome(totalIncome1.add(totalIncome2));
	    }
	    affiliateService.update(affliate);
		    
	}

	private BigDecimal summaryInNetwork(List<PromotionDateList> oldPromotionDateList, Date lastUpdateAff, Date nowDate,
			AffiliateChannel channel, AffiliateNetwork network) {
		List<String> providers;
		List<String> gameGroupCodes;
		List<String> gameCodes;
		BigDecimal sumQuery = BigDecimal.ZERO;
		
		if(StringUtils.isNoneBlank(channel.getProviderCode()) && StringUtils.isNoneBlank(channel.getGameGroupCode())) { 
      
			providers = new ArrayList<String>();
			providers.add(channel.getProviderCode());
			
			gameGroupCodes = new ArrayList<String>();
			gameGroupCodes.add(channel.getGameGroupCode());
			
			gameCodes = gamesService.getGameCodeByGameGroup(gameGroupCodes);
			
			sumQuery = allTransactionService.sumTransactionAffiliate(network.getUsername(),gameCodes,providers,lastUpdateAff,nowDate,oldPromotionDateList);

		}else if(StringUtils.isNoneBlank(channel.getProviderCode()) && StringUtils.isAllBlank(channel.getGameGroupCode())) {
			
			providers = new ArrayList<String>();
			providers.add(channel.getProviderCode());
			
			gameGroupCodes = gameGroupMappingProviderService.getGameGroupCodeByProviderCode(providers);
			
			gameCodes = gamesService.getGameCodeByGameGroup(gameGroupCodes);
     
			sumQuery = allTransactionService.sumTransactionAffiliate(network.getUsername(),gameCodes,providers,lastUpdateAff,nowDate,oldPromotionDateList);
			
		}else if(StringUtils.isAllBlank(channel.getProviderCode()) && !StringUtils.isAllBlank(channel.getGameGroupCode())) {
			
			providers = productMappingProviderService.getListProviderByProduct(channel.getProductTypeCode());
			
			gameGroupCodes = gameGroupMappingProviderService.getGameGroupCodeByProviderCode(providers);
			
			gameCodes = gamesService.getGameCodeByGameGroup(gameGroupCodes);
			
			sumQuery = allTransactionService.sumTransactionAffiliate(network.getUsername(),gameCodes,null,lastUpdateAff,nowDate,oldPromotionDateList);
		}
		return sumQuery;
	}

	public List<PromotionDateList> fillterPromotion(List<PromotionMapping> promotionList){
		List<PromotionDateList> res = new ArrayList<PromotionDateList>();
		PromotionDateList date = null;
		for(PromotionMapping temp:promotionList) {
			date = new PromotionDateList();
			date.setStart(temp.getDateActive());
			date.setEnd(temp.getUpdatedDate());
			res.add(date);
		}
		
		return res;
	}

    public List<AffliateDownlineResponse> getAffiliateDownline(String username) {
        List<AffliateDownlineResponse> resAf = new ArrayList<AffliateDownlineResponse>();
        AffliateDownlineResponse downline = null;
        AffiliateNetwork affliateNetwork = affiliateService.getAffiliateNetworkByUsername(username);
        if (affliateNetwork != null) {
            String affiliateCode = affliateNetwork.getAffiliateCode();
            Affiliate affliate = affiliateService.getDetailAffiliate(affiliateCode);

            if (affliate != null) {
                List<ChannelDetail> listChannelDetail = null;
                List<AffiliateChannel> affChaList = affiliateChannelService.getAffiliateChannelByGroup(affliate.getAffiliateGroupCode());
                List<AffiliateNetwork> affNetList = affiliateService.getAffiliateNetworkByUpline(affiliateCode);

                for (AffiliateNetwork network : affNetList) {
                    listChannelDetail = new ArrayList<ChannelDetail>();
                    BigDecimal totalIncome = BigDecimal.ZERO;
                    BigDecimal totalBet = BigDecimal.ZERO;
                    downline = new AffliateDownlineResponse();
                    downline.setUsername(network.getUsername());
            		Date lastUpdateAff = new Date();
                    
                    Affiliate affliateDownline = affiliateService.getDetailAffiliate(network.getAffiliateCode());
                    if(affliateDownline.getUpdatedDate() !=null) {
                    	lastUpdateAff = affliateDownline.getUpdatedDate();
                    }else {
                    	lastUpdateAff = network.getRegisterDate();
                    }
                    
                    PromotionMapping promotion = promotionService.findLastMappingByUsernameAndStatusAndDateActiveAfterOrderByDateActive(network.getUsername(),ProjectConstant.STATUS.ACTIVE,lastUpdateAff);
                    List<PromotionMapping> promotionList = promotionService.findMappingByUsernameAndStatusAndDateActiveBeforeOrderByDateActive(network.getUsername(),ProjectConstant.STATUS.INACTIVE,new Date());
		        	List<PromotionDateList> promotionDateList = fillterPromotion(promotionList);
            		BigDecimal sumQuery = BigDecimal.ZERO;
            		Boolean checkRegister = true;
            		Date forQuery = new Date();
//            		Date lastUpdateQuery = affliate.getUpdatedDate();
            		Date lastUpdateQuery = null;
            		List<String> promoCode = new ArrayList<String>();

            		if(promotion != null) {
            			forQuery = promotion.getDateActive();
            		}
            		
            		for(PromotionMapping temp:promotionList) {
            			promoCode.add(temp.getPromoCode());
            		}
            		
            		List<Promotion> findRegister = promotionService.getByPromoTypeAndPromoCodeIn(PromotionConstant.Type.registration,promoCode);
            		if(findRegister != null && findRegister.size() > 0) {
            			TransactionList transaction = allTransactionService.getFirstByUsernameAndTransactionType(network.getUsername(), ProjectConstant.TRANSACTION_TYPE.DEPOSIT);
            			if(transaction != null) {
							lastUpdateQuery = transaction.getCreatedDate();
//            				if(transaction.getCreatedDate().compareTo(lastUpdateQuery) > 0){
//							}
            			}else {
            				checkRegister = false;
            			}
            		}
            		
            		for (AffiliateChannel channel : affChaList) {
                        ChannelDetail channelDetail = new ChannelDetail();
                        channelDetail.setChannelName(channel.getChannelName());
                        channelDetail.setShareRateOne(channel.getShareRateOne());
                        channelDetail.setShareRateTwo(channel.getShareRateTwo());
                        
                        if(checkRegister) {
                        	sumQuery = summaryInNetwork(promotionDateList, lastUpdateQuery,forQuery, channel, network);

                            if (sumQuery != null) {
                                BigDecimal betChannel = sumQuery;
                                totalBet = totalBet.add(betChannel);

                                BigDecimal percentage = channel.getShareRateOne().divide(new BigDecimal(100));
                                BigDecimal incomeChannel = betChannel.multiply(percentage);
                                totalIncome = totalIncome.add(incomeChannel);

                                channelDetail.setTotalBetOne(betChannel);
                                channelDetail.setTotalIncome(incomeChannel);
                                listChannelDetail.add(channelDetail);
                            }
                        }
                    }
            		
                    downline.setChannelDetailList(listChannelDetail);
                    downline.setTotalBet(totalBet);
                    downline.setTotalIncome(totalIncome);
                    downline.setRegisterDate(network.getRegisterDate());
                    resAf.add(downline);
                }
                
            }
        }
        return resAf;
    }
    
    public List<AffliateDownlineResponse> getAffiliateDownlineNew(String username) {
        List<AffliateDownlineResponse> resAf = new ArrayList<AffliateDownlineResponse>();
        AffiliateNetwork affliateNetwork = affiliateService.getAffiliateNetworkByUsername(username);
        if (affliateNetwork != null) {
            String affiliateCode = affliateNetwork.getAffiliateCode();
            Affiliate affliate = affiliateService.getDetailAffiliate(affiliateCode);

            if (affliate != null) {
               
                List<AffiliateChannel> affChaList = affiliateChannelService.getAffiliateChannelByGroup(affliate.getAffiliateGroupCode());
                List<AffiliateNetwork> affNetList = affiliateService.getAffiliateNetworkByUpline(affiliateCode);

                for (AffiliateNetwork network : affNetList) {
                	List<ChannelDetail> listChannelDetail = new ArrayList<ChannelDetail>();
                	AffliateDownlineResponse downline = new AffliateDownlineResponse();
                	BigDecimal totalBet = BigDecimal.ZERO;
                	BigDecimal totalIncome = BigDecimal.ZERO;
                	
                	for (AffiliateChannel channel : affChaList) {
                		AffiliateIncomeChannel dataIncomeChannel = affiliateIncomeChannelRepository.findByAffiliateChannelCodeAndUsername(channel.getAffiliateChannelCode(), network.getUsername());
                		
                		ChannelDetail channelDetail = new ChannelDetail();
                		channelDetail.setChannelName(channel.getChannelName());
                		channelDetail.setShareRateOne(channel.getShareRateOne());
                		channelDetail.setShareRateTwo(channel.getShareRateTwo());
        		        if(dataIncomeChannel != null) {
        		        	totalBet = totalBet.add(dataIncomeChannel.getTotalBetOne());
        		        	totalIncome = totalIncome.add(dataIncomeChannel.getTotalIncome());
        		        	channelDetail.setTotalBetOne(dataIncomeChannel.getTotalBetOne());
        			        channelDetail.setTotalIncome(dataIncomeChannel.getTotalIncome());
        		        }else {
        		        	channelDetail.setTotalBetOne(BigDecimal.ZERO);
        			        channelDetail.setTotalIncome(BigDecimal.ZERO);
        		        }
        		        listChannelDetail.add(channelDetail);
                    }
                    downline.setChannelDetailList(listChannelDetail);
                    downline.setTotalBet(totalBet);
                    downline.setTotalIncome(totalIncome);
                    resAf.add(downline);
                }
                
            }
        }
        return resAf;
    }
}
