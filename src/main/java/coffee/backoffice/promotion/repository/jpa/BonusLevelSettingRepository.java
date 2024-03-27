package coffee.backoffice.promotion.repository.jpa;

import coffee.backoffice.promotion.model.BonusLevelSetting;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusLevelSettingRepository extends JpaRepository<BonusLevelSetting,Long> {
	
	public List<BonusLevelSetting> findByPromoCodeOrderByBonusLevelDesc(String promoCode);
	public List<BonusLevelSetting> findByPromoCodeOrderByBonusLevelAsc(String promoCode);
	public List<BonusLevelSetting> findAllByPromoCode(String promoCode);
	public List<BonusLevelSetting> findByDepositAmountLessThanEqual(BigDecimal depositAmount);

}
