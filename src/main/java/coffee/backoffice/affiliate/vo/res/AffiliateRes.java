package coffee.backoffice.affiliate.vo.res;

import coffee.backoffice.affiliate.model.Affiliate;
import coffee.backoffice.affiliate.model.AffiliateNetwork;
import coffee.backoffice.affiliate.vo.model.ChannelDetail;
import coffee.backoffice.player.vo.res.CustomerRes;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AffiliateRes {
    private Long id;
    private String username;
    private String affiliateCode;
    private String affiliateGroupCode;
    private String groupName;
    private String detail;
    private String banner;
    private int clickCount;
    private BigDecimal totalIncome;
    private BigDecimal incomeOne;
    private BigDecimal incomeTwo;
    private BigDecimal withdraw;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String affiliateCodeUp;
    private List<AffiliateNetworkRes> affiliateNetworkList;
    private List<ChannelDetail> channelDetailList;
    private CustomerRes selfDetail;
    private CustomerRes upLineDetail;

    public void setEntityToRes(Affiliate req) {
        this.setId(req.getId());
        this.setAffiliateCode(req.getAffiliateCode());
        this.setAffiliateGroupCode(req.getAffiliateGroupCode());
        this.setDetail(req.getDetail());
        this.setBanner(req.getBanner());
        this.setClickCount(req.getClickCount());
        this.setTotalIncome(req.getTotalIncome());
        this.setIncomeOne(req.getIncomeOne());
        this.setIncomeTwo(req.getIncomeTwo());
        this.setWithdraw(req.getWithdraw());
        this.setCreatedBy(req.getCreatedBy());
        this.setCreatedDate(req.getCreatedDate());
        this.setUpdatedBy(req.getUpdatedBy());
        this.setUpdatedDate(req.getUpdatedDate());
    }
}
