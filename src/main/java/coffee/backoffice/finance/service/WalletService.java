package coffee.backoffice.finance.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.GameHistory;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.repository.jpa.WalletRepository;
import coffee.backoffice.finance.vo.req.ManualAndSubtractReq;
import coffee.backoffice.player.service.BonusInformationService;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.IssueSetting;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.model.RuleSetting;
import coffee.backoffice.promotion.service.PromotionService;
import coffee.website.providers.service.PlaygameService;
import framework.constant.ProjectConstant;
import framework.utils.GenerateRandomString;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PlaygameService playgameService;

    @Autowired
    private PromotionService promotionService;
    
    @Autowired
    private GameHistoryService gameHistoryService;

    @Autowired
    private WithdrawConditionService withdrawConditionService;

    @Autowired
    private BonusInformationService bonusInformationService;

    public void createWallet(String username, String walletName, String type) {
        Wallet result = walletRepository.findByUsernameAndWalletName(username, walletName);
        log.info("=== WalletService => createWallet() on Started ===");
        if (result == null) {
            Wallet data = new Wallet();
            log.info("Set [ username ] : " + username);
            data.setUsername(username);
            data.setWalletId(GenerateRandomString.generateUUID());
            data.setWalletType(type);
            data.setWalletName(walletName);
            data.setBalance(BigDecimal.ZERO);
            data.setBonus(BigDecimal.ZERO);
            walletRepository.save(data);
        } else {
            log.info("Duplicate wallet !!!");
        }
        log.info("=== WalletService => createWallet() Ending ===");
    }

    public BigDecimal findBalanceWithBonus(String username) {
//        String uuid = GenerateRandomString.generateUUID();
//        Customer customer = customerService.getByUsername(username);
//        playgameService.withdrawCreditFromProvider(customer, uuid);
        log.info("=== WalletService => findBalance() on Started ===");
        Wallet result = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);

        if (result != null) {
            log.info("Found Balance");
            log.info("=== WalletService => findBalance() Ending ===");
            return result.getBalance().add(result.getBonus());

        } else {
            log.info("Not Found Balance");
            log.info("=== WalletService => findBalance() Ending ===");
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal findBalanceWithoutBonus(String username) {
//        String uuid = GenerateRandomString.generateUUID();
//        Customer customer = customerService.getByUsername(username);
//        playgameService.withdrawCreditFromProvider(customer, uuid);
        log.info("=== WalletService => findBalanceWithoutBonus() on Started ===");
        Wallet result = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);

        if (result != null) {
            log.info("Found Balance");
            log.info("=== WalletService => findBalanceWithoutBonus() Ending ===");
            return result.getBalance();

        } else {
            log.info("Not Found Balance");
            log.info("=== WalletService => findBalanceWithoutBonus() Ending ===");
            return BigDecimal.ZERO;
        }
    }

    public void editWallet(Wallet wallet) {
        log.info("=== WalletService => editWalletBalance on Started ===");
        walletRepository.save(wallet);
        log.info("=== WalletService => editWalletBalance Ending ===");
    }

    public Wallet findWalletData(String username) {
        log.info("=== WalletService => findWalletData on Started ===");
        Wallet result = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);

        if (result != null) {
            log.info("Found WalletData");
            log.info("=== WalletService => findWalletData Ending ===");
            return result;
        } else {
            log.info("Not Found WalletData");
            log.info("=== WalletService => findWalletData Ending ===");
            return null;
        }
    }

    public Boolean checkUseBonus(String username, String providerCode) {
        log.info("=== WalletService => checkUseBonus on Started ===");
        List<PromotionMapping> mapping = promotionService.findMappingByUsernameAndStatus(username, ProjectConstant.STATUS.ACTIVE);
        if (mapping != null && mapping.size() > 0) {
            String promoCode = mapping.get(0).getPromoCode();
            Promotion promotion = promotionService.getByPromoCode(promoCode);
            if (PromotionConstant.Type.registration.equals(promotion.getPromoType())) {
                RuleSetting rule = promotionService.getRuleSettingByPromoCode(promoCode);
                if (PromotionConstant.WalletType.BONUS.equals(rule.getReceiveBonusWallet())) {
                    return true;
                }
            } else {
                List<IssueSetting> issueList = promotionService.getIssueSettingByPromoCode(promoCode);
                for (IssueSetting issue : issueList) {
                    if (issue.getProviderCode().equals(providerCode)) {
                        return true;
                    }
                }
            }
        }
        log.info("=== WalletService => checkUseBonus Ending ===");
        return false;
    }

    public Boolean checkUseBonus(String username, String providerCode, List<PromotionMapping> mapping) {
        log.info("=== WalletService => checkUseBonus on Started ===");
        if (mapping != null && mapping.size() > 0) {
            String promoCode = mapping.get(0).getPromoCode();
            Promotion promotion = promotionService.getByPromoCode(promoCode);
            if (PromotionConstant.Type.registration.equals(promotion.getPromoType())) {
                RuleSetting rule = promotionService.getRuleSettingByPromoCode(promoCode);
                if (PromotionConstant.WalletType.BONUS.equals(rule.getReceiveBonusWallet())) {
                	log.info("=== Return => true");
                    return true;
                }
                log.info("=== Return => PromotionConstant.Type.registration.equals(promotion.getPromoType())");
            } else {
                List<IssueSetting> issueList = promotionService.getIssueSettingByPromoCode(promoCode);
                for (IssueSetting issue : issueList) {
                    if (issue.getProviderCode().equals(providerCode)) {
                    	log.info("=== Return => true");
                        return true;
                    }
                }
            }
        }
        log.info("=== Return => false");
        log.info("=== WalletService => checkUseBonus Ending ===");
        return false;
    }

    public void updateWallet(String username, String provider, BigDecimal balance) {
        log.info("=== WalletService => updateWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + balance + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        GameHistory history = new GameHistory();
        if(ProjectConstant.PROVIDERS.SBO.equals(provider)) {
        	history = gameHistoryService.getGameHistoryUsernameAndProviderAndGameCode(username,provider,"SportsBook");
        }else if(ProjectConstant.PROVIDERS.MX.equals(provider)) {
        	history = gameHistoryService.getGameHistoryUsernameAndProviderAndGameCode(username,provider,"MX-LOBBY");
        }else {
        	history = gameHistoryService.getLastGameHistoryUsernameAndProvider(username,provider);
        }
        
        BigDecimal tempMainBalance = data.getBalance().subtract(history.getCreditIn());
        log.info("Provider ! => "+provider);
        log.info("History In Balance ! => "+history.getCreditIn());
        log.info("Wallet Main Balance ! => "+data.getBalance());
        log.info("Out Game Balance ! => "+balance);
        log.info("Temp Main Balance ! (Main - His) => "+tempMainBalance);
        
        List<PromotionMapping> mappingList = promotionService.findMappingByUsernameAndStatus(username, ProjectConstant.STATUS.ACTIVE);
        if (data != null) {
            log.info("Found wallet ! => Sum balance");
//            if (checkUseBonus(username, provider, mappingList)) {
            if (history.getUseBonus()) {
                BigDecimal calculate = balance.subtract(data.getBonus());
                if (calculate.compareTo(BigDecimal.ZERO) >= 0) {
                	if(tempMainBalance.compareTo(BigDecimal.ZERO) > 0) {
                		data.setBalance(calculate.add(tempMainBalance));
                	}else {
                		data.setBalance(calculate);
                	}
                } else {
                    BigDecimal sumBonus = data.getBonus().add(calculate);
                    data.setBalance(BigDecimal.ZERO);
                    data.setBonus(sumBonus);

                    if (sumBonus.compareTo(new BigDecimal("20")) <= 0) {
                        PromotionMapping mapping = mappingList.get(0);
                        mapping.setStatus(ProjectConstant.STATUS.INACTIVE);
                        mapping.setUpdatedBy("_system");
                        promotionService.editMapping(mapping);

                        List<WithdrawCondition> listCondition = withdrawConditionService.getWithdrawConditionByUsernameAndPromoCode(username, mapping.getPromoCode());
                        if (listCondition != null && listCondition.size() > 0) {
                            WithdrawCondition condition = listCondition.get(0);
                            condition.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
                            condition.setRemark("PASS by bonus below 20 unit");
                            condition.setUpdatedBy("_system");
                            condition.setUpdatedDate(new Date());
                            withdrawConditionService.saveWithdrawCondition(condition);
                        }
                        data.setBalance(data.getBonus());
                        data.setBonus(BigDecimal.ZERO);
                    }
                }
            } else {
            	log.info("Check tempMainBalance ! => "+(tempMainBalance.compareTo(BigDecimal.ZERO) > 0));
            	if(tempMainBalance.compareTo(BigDecimal.ZERO) > 0) {
            		data.setBalance(balance.add(tempMainBalance));
            	}else {
            		data.setBalance(balance);
            	}
            	
                if (mappingList != null && mappingList.size() > 0) {
                	String promoCode = mappingList.get(0).getPromoCode();
					RuleSetting rule = promotionService.getRuleSettingByPromoCode(promoCode);
					if(ProjectConstant.WALLET_TYPE.BALANCE.equals(rule.getReceiveBonusWallet())) {
						if(balance.compareTo(new BigDecimal("5"))<= 0) {
							List<WithdrawCondition> listCondition = withdrawConditionService.getWithdrawConditionByUsername(username, ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);
							for(WithdrawCondition condition:listCondition) {
								condition.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
								condition.setRemark("PASS by credit below 5 unit");
								condition.setUpdatedBy("_system");
								condition.setUpdatedDate(new Date());
								withdrawConditionService.saveWithdrawCondition(condition);
							}
						}
					}
                } else {
                    if (balance.compareTo(new BigDecimal("2")) <= 0) {
                        WithdrawCondition withdrawCondition = withdrawConditionService.getWithdrawConditionByUsernameAndConditionType(username, ProjectConstant.WITHDRAW_CONDITION.GENERAL, ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);

                        if (withdrawCondition != null) {
                            withdrawCondition.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
                            withdrawCondition.setRemark("PASS by credit below 2 unit");
                            withdrawCondition.setUpdatedBy("_system");
                            withdrawCondition.setUpdatedDate(new Date());
                            withdrawConditionService.saveWithdrawCondition(withdrawCondition);
                        }
                    }
                }
            }
            walletRepository.save(data);
        }
        log.info("=== WalletService => updateWallet() Ending ===");
    }


    public void updateWalletLotto(String username, String provider, BigDecimal cost) {
        log.info("=== WalletService => updateWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + cost + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        List<PromotionMapping> mappingList = promotionService.findMappingByUsernameAndStatus(username, ProjectConstant.STATUS.ACTIVE);
        if (data != null) {
            log.info("Found wallet ! => reduce balance");
            if (checkUseBonus(username, provider, mappingList)) {
                BigDecimal calculate = cost.subtract(data.getBalance());
                data.setBalance(BigDecimal.ZERO);
                data.setBonus(data.getBonus().subtract(calculate));
            } else {
                data.setBalance(data.getBalance().subtract(cost));
            }
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => updateWallet() Ending ===");
    }

    public void addBalanceWallet(String username, BigDecimal balance) {
        log.info("=== WalletService => addBalanceWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + balance + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => Sum balance");
            BigDecimal sum = balance.add(data.getBalance());
            log.info("Sum : " + sum);
            data.setBalance(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => addBalanceWallet() Ending ===");
    }

    public void addBonusWallet(String username, BigDecimal bonus) {
        log.info("=== WalletService => addBonusWallet() on Started ===");
        log.info("Parameters [username : " + username + " , bonus :" + bonus + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => Sum bonus");
            BigDecimal sum = bonus.add(data.getBonus());
            log.info("Sum : " + sum);
            data.setBonus(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => addBonusWallet() Ending ===");
    }

    public void subtractBalanceWallet(String username, BigDecimal balance) {
        log.info("=== WalletService => subtractBalanceWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + balance + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => Sum balance");
            BigDecimal sum = data.getBalance().subtract(balance);
            log.info("Sum : " + sum);
            data.setBalance(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => subtractBalanceWallet() Ending ===");
    }

    public void subtractBonusWallet(String username, BigDecimal bonus) {
        log.info("=== WalletService => subtractBonusWallet() on Started ===");
        log.info("Parameters [username : " + username + " , bonus :" + bonus + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => Sum bonus");
            BigDecimal sum = data.getBonus().subtract(bonus);
            log.info("Sum : " + sum);
            data.setBonus(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => subtractBonusWallet() Ending ===");
    }


    public void reduceBalance(String username, BigDecimal amount) throws Exception {
        log.info("=== WalletService => updateWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + amount + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => subtract amount");
            BigDecimal sum = data.getBalance().subtract(amount);
            if (sum.compareTo(BigDecimal.ZERO) == -1) {
                throw new Exception("OUT OF BALANCE");
            }
            log.info("Sum : " + sum);
            data.setBalance(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => updateWallet() Ending ===");
    }

    public void addBalance(String username, BigDecimal amount) {
        log.info("=== WalletService => updateWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + amount + "]");
        log.info("Calling walletRepository.findByUsernameAndWalletName()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => add amount");
            BigDecimal sum = data.getBalance().add(amount);
            log.info("Sum : " + sum);
            data.setBalance(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => updateWallet() Ending ===");
    }

    public void reducePendingBalance(String username, BigDecimal amount) throws Exception {
        log.info("=== WalletService => updatePaddingWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + amount + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => subtract amount");
            BigDecimal sum = data.getPendingWithdrawal().subtract(amount);
            if (sum.compareTo(BigDecimal.valueOf(0)) == -1) {
                throw new Exception("OUT OF BALANCE");
            }
            log.info("Sum : " + sum);
            data.setPendingWithdrawal(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => updateWallet() Ending ===");
    }

    public void addPendingBalance(String username, BigDecimal amount) {
        log.info("=== WalletService => updatePaddingWallet() on Started ===");
        log.info("Parameters [username : " + username + " , balance :" + amount + "]");
        log.info("Calling walletRepository.findByUsername()");
        Wallet data = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);
        if (data != null) {
            log.info("Found wallet ! => add amount");
            BigDecimal sum = data.getPendingWithdrawal().add(amount);
            log.info("Sum : " + sum);
            data.setPendingWithdrawal(sum);
            walletRepository.save(data);
        } else {
            log.info("Not found wallet !");
        }
        log.info("=== WalletService => updateWallet() Ending ===");
    }

    public void manualAddSubtract(String username, BigDecimal amount, String wallet, String walletType, Boolean add, Boolean subtract) {
        if (ProjectConstant.WALLET.MAIN_WALLET.equals(wallet)) {
            System.out.println(">>>>>>>>>> MAIN WALLET");
            if (ProjectConstant.WALLET_TYPE.BALANCE.equals(walletType)) {
                bonusInformationService.addOrUpdateTotalManualBonus(username, amount);
                if (add) {
                    addBalanceWallet(username, amount);
                } else if (subtract) {
                    subtractBalanceWallet(username, amount);
                }
            } else if (ProjectConstant.WALLET_TYPE.BONUS.equals(walletType)) {
                bonusInformationService.addOrUpdateTotalAmountOfManual(username, amount);
                if (add) {
                    addBonusWallet(username, amount);
                } else if (subtract) {
                    subtractBonusWallet(username, amount);
                }
            }
        } else if (ProjectConstant.WALLET.SUB_WALLET.equals(wallet)) {
            System.out.println(">>>>>>>>>> SUB WALLET");
        } else if (ProjectConstant.WALLET.AFFILIATE_WALLET.equals(wallet)) {
            System.out.println(">>>>>>>>>> AFFILIATE WALLET");
        }


    }

    public List<Wallet> findAllWalletData(String username) {
        log.info("=== WalletService => findWalletData on Started ===");
        List<Wallet> result = walletRepository.findAllByUsername(username);

        if (result != null) {
            log.info("Found WalletData");
            log.info("=== WalletService => findWalletData Ending ===");
            return result;
        } else {
            log.info("Not Found WalletData");
            log.info("=== WalletService => findWalletData Ending ===");
            return null;
        }
    }

    public void manualAdjustAddSubtract(ManualAndSubtractReq req) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> NEW ADD SUBTRACT" + req);
		List<ManualAndSubtractReq.ProviderReq> gameList = req.getGame();
		System.out.println(">>>>>>>>>> GAME LIST" + gameList);
		
		if (ProjectConstant.WALLET.MAIN_WALLET.equals(req.getWallet())) {
            System.out.println(">>>>>>>>>> MAIN WALLET");
            
            if (ProjectConstant.WALLET_TYPE.BALANCE.equals(req.getWalletType())) {
//                bonusInformationService.addOrUpdateTotalManualBonus(req.getUsername(), req.getBalance());
                if (req.getStatus().equals("ADD")) {
//                    addBalanceWallet(req.getUsername(), req.getBalance());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> NEW ADD " +req.getUsername() + " :: " + req.getBalance());
                } else if (req.getStatus().equals("SUBTRACT")) {
//                    subtractBalanceWallet(req.getUsername(), req.getBalance());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> NEW SUBTRACT" +req.getBalance() + " :: " + req.getBalance());
                }
                
            } else if (ProjectConstant.WALLET_TYPE.BONUS.equals(req.getWalletType())) {
//                bonusInformationService.addOrUpdateTotalAmountOfManual(req.getUsername(), req.getBalance());
                if (req.getStatus().equals("ADD")) {
//                    addBonusWallet(req.getUsername(), req.getBalance());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> NEW ADD " +req.getBalance() +" :: "+ req.getBalance());
                } else if (req.getStatus().equals("SUBTRACT")) {
//                    subtractBonusWallet(req.getUsername(), req.getBalance());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> NEW SUBTRACT" + req.getBalance()+ " :: " + req.getBalance());
                }
            }
        } else if (ProjectConstant.WALLET.SUB_WALLET.equals(req.getWallet())) {
            System.out.println(">>>>>>>>>> SUB WALLET");
        } else if (ProjectConstant.WALLET.AFFILIATE_WALLET.equals(req.getWallet())) {
            System.out.println(">>>>>>>>>> AFFILIATE WALLET");
        }
		
	}
}
