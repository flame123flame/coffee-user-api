package coffee.backoffice.casino.vo.res;

import coffee.backoffice.casino.model.GameProductTypeNoIcon;
import coffee.backoffice.casino.model.GameProviderNoIcon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GamesDatatableRes {
    private Long id;
    private String nameTh;
    private Date createdAt;
    private Date updatedAt;
    private String updatedBy;
    private String nameEn;
    private String code;
    private String gameProductTypeCode;
    private String providerCode;
    private Boolean platformMapp;
    private Boolean platformMhFive;
    private Boolean platformMini;
    private BigDecimal minRtp;
    private BigDecimal maxRtp;
    private String displayName;
    private Boolean status;
    private String remark;
    private String image1;
    private String image2;
    private String gameCode;
    private Boolean platformPcDl;
    private Boolean platformPc;
    private Boolean enable;
    private String gameGroupCode;

    private String providerName;
    private String productTypeName;
    private String gameTag;

}
