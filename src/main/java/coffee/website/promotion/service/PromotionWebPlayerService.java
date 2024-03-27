package coffee.website.promotion.service;

import coffee.backoffice.promotion.model.PostSetting;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.repository.dao.PromotionDao;
import coffee.backoffice.promotion.repository.jpa.*;
import coffee.backoffice.promotion.vo.res.PromotionPlayerRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionWebPlayerService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PostSettingRepository postSettingRepository;

    public List<PromotionPlayerRes> getAllToPlayer() {
        List<PromotionPlayerRes> res = new ArrayList<PromotionPlayerRes>();
        List<Promotion> result = promotionRepository.findAllByViewStatus("SHOW");
        for (Promotion obj : result) {
            PostSetting postSetting = postSettingRepository.findByPromoCode(obj.getPromoCode());
            PromotionPlayerRes temp = new PromotionPlayerRes();
            temp.setPromoCode(obj.getPromoCode());
            temp.setPromoTitle(obj.getPromoTitle());
            temp.setPromoBanner(postSetting.getDeskBanner());
            temp.setPromoDetail(postSetting.getDeskDetail());
            temp.setPromoType(obj.getPromoType());
            res.add(temp);
        }
        return res;
    }
}
