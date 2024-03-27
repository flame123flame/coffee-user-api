package coffee.backoffice.player.service;

import coffee.backoffice.player.model.BonusInformation;
import coffee.backoffice.player.repository.jpa.BonusInformationJpa;
import framework.utils.UserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BonusInformationService {

    @Autowired
    BonusInformationJpa bonusInformationJpa;

    public BonusInformation getByUsername(String username) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation == null) {
            bonusInformation = new BonusInformation();
            bonusInformation.setTotalRegistrationBonus(BigDecimal.valueOf(0));
            bonusInformation.setTotalManualBonus(BigDecimal.valueOf(0));
            bonusInformation.setTotalDepositBonus(BigDecimal.valueOf(0));
            bonusInformation.setTotalEventBonus(BigDecimal.valueOf(0));
            bonusInformation.setTotalRebateBonus(BigDecimal.valueOf(0));
            bonusInformation.setTotalCashbackBonus(BigDecimal.valueOf(0));
            bonusInformation.setTotalAmountOfManual(BigDecimal.valueOf(0));
            bonusInformation.setUsername(username);
            bonusInformationJpa.save(bonusInformation);
        }
        return bonusInformation;
    }

    public void addOrUpdateTotalRegistrationBonus(String username, BigDecimal bonus) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation != null) {
            if (bonusInformation.getCreatedBy() == null || bonusInformation.getCreatedBy().isEmpty()) {
                bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
                bonusInformation.setCreatedDate(new Date());
            }
            bonusInformation.setUpdatedBy(UserLoginUtil.getUsername());
            bonusInformation.setUpdatedDate(new Date());
            bonusInformation.setTotalRegistrationBonus(bonusInformation.getTotalRegistrationBonus().add(bonus));
        } else {
            bonusInformation = createNewBonus(username);
            bonusInformation.setTotalRegistrationBonus(bonus);
        }
        bonusInformationJpa.save(bonusInformation);
    }

    public void addOrUpdateTotalManualBonus(String username, BigDecimal bonus) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation != null) {
            if (bonusInformation.getCreatedBy() == null || bonusInformation.getCreatedBy().isEmpty()) {
                bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
                bonusInformation.setCreatedDate(new Date());
            }
            bonusInformation.setUpdatedBy(UserLoginUtil.getUsername());
            bonusInformation.setUpdatedDate(new Date());
            bonusInformation.setTotalManualBonus(bonusInformation.getTotalManualBonus().add(bonus));
        } else {
            bonusInformation = createNewBonus(username);
            bonusInformation.setTotalManualBonus(bonus);
        }
        bonusInformationJpa.save(bonusInformation);
    }

    public void addOrUpdateTotalDepositBonus(String username, BigDecimal bonus) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation != null) {
            if (bonusInformation.getCreatedBy() == null || bonusInformation.getCreatedBy().isEmpty()) {
                bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
                bonusInformation.setCreatedDate(new Date());
            }
            bonusInformation.setUpdatedBy(UserLoginUtil.getUsername());
            bonusInformation.setUpdatedDate(new Date());
            bonusInformation.setTotalDepositBonus(bonusInformation.getTotalDepositBonus().add(bonus));
        } else {
            bonusInformation = createNewBonus(username);
            bonusInformation.setTotalDepositBonus(bonus);
        }
        bonusInformationJpa.save(bonusInformation);
    }

    public void addOrUpdateTotalEventBonus(String username, BigDecimal bonus) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation != null) {
            if (bonusInformation.getCreatedBy() == null || bonusInformation.getCreatedBy().isEmpty()) {
                bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
                bonusInformation.setCreatedDate(new Date());
            }
            bonusInformation.setUpdatedBy(UserLoginUtil.getUsername());
            bonusInformation.setUpdatedDate(new Date());
            bonusInformation.setTotalEventBonus(bonusInformation.getTotalEventBonus().add(bonus));
        } else {
            bonusInformation = createNewBonus(username);
            bonusInformation.setTotalEventBonus(bonus);
        }
        bonusInformationJpa.save(bonusInformation);
    }

    public void addOrUpdateTotalRebateBonus(String username, BigDecimal bonus) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation != null) {
            if (bonusInformation.getCreatedBy() == null || bonusInformation.getCreatedBy().isEmpty()) {
                bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
                bonusInformation.setCreatedDate(new Date());
            }
            bonusInformation.setUpdatedBy(UserLoginUtil.getUsername());
            bonusInformation.setUpdatedDate(new Date());
            bonusInformation.setTotalRebateBonus(bonusInformation.getTotalRebateBonus().add(bonus));
        } else {
            bonusInformation = createNewBonus(username);
            bonusInformation.setTotalRebateBonus(bonus);
        }
        bonusInformationJpa.save(bonusInformation);
    }

    public void addOrUpdateTotalCashBackBonusBonus(String username, BigDecimal bonus) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation != null) {
            if (bonusInformation.getCreatedBy() == null || bonusInformation.getCreatedBy().isEmpty()) {
                bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
                bonusInformation.setCreatedDate(new Date());
            }
            bonusInformation.setUpdatedBy(UserLoginUtil.getUsername());
            bonusInformation.setUpdatedDate(new Date());
            bonusInformation.setTotalCashbackBonus(bonusInformation.getTotalCashbackBonus().add(bonus));
        } else {
            bonusInformation = createNewBonus(username);
            bonusInformation.setTotalCashbackBonus(bonus);
        }
        bonusInformationJpa.save(bonusInformation);
    }

    public void addOrUpdateTotalAmountOfManual(String username, BigDecimal bonus) {
        BonusInformation bonusInformation = bonusInformationJpa.findByUsername(username);
        if (bonusInformation != null) {
            if (bonusInformation.getCreatedBy() == null || bonusInformation.getCreatedBy().isEmpty()) {
                bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
                bonusInformation.setCreatedDate(new Date());
            }
            bonusInformation.setUpdatedBy(UserLoginUtil.getUsername());
            bonusInformation.setUpdatedDate(new Date());
            bonusInformation.setTotalAmountOfManual(bonusInformation.getTotalAmountOfManual().add(bonus));
        } else {
            bonusInformation = createNewBonus(username);
            bonusInformation.setTotalAmountOfManual(bonus);
        }
        bonusInformationJpa.save(bonusInformation);
    }

    private BonusInformation createNewBonus(String username) {
        BonusInformation bonusInformation = new BonusInformation();
        bonusInformation.setTotalRegistrationBonus(BigDecimal.valueOf(0));
        bonusInformation.setTotalManualBonus(BigDecimal.valueOf(0));
        bonusInformation.setTotalDepositBonus(BigDecimal.valueOf(0));
        bonusInformation.setTotalEventBonus(BigDecimal.valueOf(0));
        bonusInformation.setTotalRebateBonus(BigDecimal.valueOf(0));
        bonusInformation.setTotalCashbackBonus(BigDecimal.valueOf(0));
        bonusInformation.setTotalAmountOfManual(BigDecimal.valueOf(0));
        bonusInformation.setUsername(username);
        bonusInformation.setCreatedBy(UserLoginUtil.getUsername());
        bonusInformation.setCreatedDate(new Date());
        return bonusInformation;
    }
}
