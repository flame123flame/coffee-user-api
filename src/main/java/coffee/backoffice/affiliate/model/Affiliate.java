package coffee.backoffice.affiliate.model;

import coffee.backoffice.affiliate.vo.req.AffiliateReq;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "affiliate")
public class Affiliate implements Serializable {
	
    private static final long serialVersionUID = 8043494722869997935L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "affiliate_code" , length = 255)
    private String affiliateCode;
    
    @Column(name = "affiliate_group_code" , length = 255)
    private String affiliateGroupCode;
    
    @Column(name = "detail" , length = 255)
    private String detail;
    
    @Column(name = "banner" , length = 255)
    private String banner;
    
    @Column(name = "click_count")
    private int clickCount;
    
    @Column(name = "total_income")
    private BigDecimal totalIncome;
    
    @Column(name = "income_one")
    private BigDecimal incomeOne;
    
    @Column(name = "income_two")
    private BigDecimal incomeTwo;
    
    @Column(name = "withdraw")
    private BigDecimal withdraw;
    
    @Column(name = "created_by" , length = 255)
    private String createdBy;
    
    @Column(name = "created_date")
    private Date createdDate = new Date();
    
    @Column(name = "updated_by" , length = 255)
    private String updatedBy;
    
    @Column(name = "updated_date")
    private Date updatedDate;
    
    @Column(name = "updated_income_date")
    private Date updatedIncomeDate;
    

    public void setReqToEntity(AffiliateReq req) {
        id = req.getId() == null ? id : req.getId();
        affiliateCode = req.getAffiliateCode() == null ? affiliateCode : req.getAffiliateCode();
        affiliateGroupCode = req.getAffiliateGroupCode() == null ? affiliateGroupCode : req.getAffiliateGroupCode();
        detail = req.getDetail() == null ? detail : req.getDetail();
        banner = req.getBanner() == null ? banner : req.getBanner();
        totalIncome = req.getTotalIncome() == null ? totalIncome : req.getTotalIncome();
        incomeOne = req.getIncomeOne() == null ? incomeOne : req.getIncomeOne();
        incomeTwo = req.getIncomeTwo() == null ? incomeTwo : req.getIncomeTwo();
        withdraw = req.getWithdraw() == null ? withdraw : req.getWithdraw();
    }
}
