package coffee.backoffice.promotion.model;

import coffee.backoffice.promotion.vo.req.PromotionRequestReq;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "promotion_request")
public class PromotionRequest implements Serializable {
    private static final long serialVersionUID = -5918987164110735270L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "request_id")
    private String requestId;
    
    @Column(name = "player_group")
    private String playerGroup;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "promo_code")
    private String promoCode;
    
    @Column(name = "promo_name")
    private String promoName;
    
    @Column(name = "promo_type")
    private String promoType;
    
    @Column(name = "balance_amount")
    private BigDecimal balanceAmount;
    
    @Column(name = "bonus_amount")
    private BigDecimal bonusAmount;

    @Column(name = "status_request")
    private String statusRequest;
    
    @Column(name = "remark")
    private String remark;
    
    @Column(name = "bonus_level")
    private Integer bonusLevel;
    
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "created_by" , length = 255)
    private String createdBy = UserLoginUtil.getUsername();
    
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by" , length = 255)
    private String updatedBy;
    
}
