package coffee.backoffice.promotion.vo.res;

import coffee.backoffice.promotion.model.PromotionRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromotionRequestRes {
	private Long id;
    private String playerGroup;
    private String username;
    private String promoCode;
    private String promoName;
    private String promoType;
    private String requestId;
    private BigDecimal balanceAmount;
    private BigDecimal bonusAmount;
    private String statusRequest;
    private Date updatedAt;
    private String updatedBy;
private Date createdAt;
private String createdBy;
    public void setEntityToRes(PromotionRequest req) {
        this.setId(req.getId());
        this.setPlayerGroup(req.getPlayerGroup());
        this.setUsername(req.getUsername());
        this.setPromoCode(req.getPromoCode());
        this.setPromoName(req.getPromoName());
        this.setPromoType(req.getPromoType());
        this.setRequestId(req.getRequestId());
        this.setBalanceAmount(req.getBalanceAmount());
        this.setBonusAmount(req.getBonusAmount());
        this.setStatusRequest(req.getStatusRequest());
        this.setUpdatedAt(req.getUpdatedAt());
        this.setUpdatedBy(req.getUpdatedBy());
        this.setCreatedAt(req.getCreatedAt());
        this.setCreatedBy(req.getCreatedBy());
    }
}
