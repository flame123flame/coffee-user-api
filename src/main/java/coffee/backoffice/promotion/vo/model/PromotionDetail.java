package coffee.backoffice.promotion.vo.model;

import coffee.backoffice.promotion.model.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PromotionDetail {
	
	private Promotion promotion;
    private PostSetting postSetting;
    private AppSetting appSetting;
    private RuleSetting ruleSetting;
    private List<IssueSetting> issueSetting;
    private List<BonusLevelSetting> bonusLevelSettings;

}
