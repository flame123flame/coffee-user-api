package coffee.backoffice.affiliate.vo.res;

import coffee.backoffice.affiliate.model.Affiliate;
import coffee.backoffice.affiliate.model.AffiliateNetwork;
import coffee.backoffice.player.model.Customer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class AffiliateNetworkRes {
    private Long id;
    private String username;
    private String affiliateCode;
    private String affiliateCodeUp;
    private Date registerDate;
    private Customer customerDetail;
    private BigDecimal playAmount = BigDecimal.ZERO;

    public void setEntityToRes(AffiliateNetwork req) {
        this.setId(req.getId());
        this.setUsername(req.getUsername());
        this.setAffiliateCode(req.getAffiliateCode());
        this.setAffiliateCodeUp(req.getAffiliateCodeUp());
        this.setRegisterDate(req.getRegisterDate());
    }
}
