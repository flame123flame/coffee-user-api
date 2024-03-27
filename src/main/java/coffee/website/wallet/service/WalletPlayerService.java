package coffee.website.wallet.service;

import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.repository.jpa.WalletRepository;
import coffee.backoffice.player.model.Customer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class WalletPlayerService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PlaygameService playgameService;

    @Autowired
    private PromotionService promotionService;


    public BigDecimal findBalanceWithBonus(String username) {
        String uuid = GenerateRandomString.generateUUID();
        Customer customer = customerService.getByUsername(username);
        playgameService.withdrawCreditFromProvider(customer, uuid);
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
        String uuid = GenerateRandomString.generateUUID();
        Customer customer = customerService.getByUsername(username);
        playgameService.withdrawCreditFromProvider(customer, uuid);
        log.info("=== WalletService => findBalance() on Started ===");
        Wallet result = walletRepository.findByUsernameAndWalletName(username, ProjectConstant.WALLET.MAIN_WALLET);

        if (result != null) {
            log.info("Found Balance");
            log.info("=== WalletService => findBalance() Ending ===");
            return result.getBalance();

        } else {
            log.info("Not Found Balance");
            log.info("=== WalletService => findBalance() Ending ===");
            return BigDecimal.ZERO;
        }
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


}
