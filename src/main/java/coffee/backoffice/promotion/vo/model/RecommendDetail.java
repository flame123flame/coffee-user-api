package coffee.backoffice.promotion.vo.model;

import java.util.List;

import coffee.backoffice.promotion.vo.res.PromotionPlayerRes;
import lombok.Data;

@Data
public class RecommendDetail {
	private List<PromotionPlayerRes> list;

}
