package coffee.backoffice.casino.vo.res;

import java.math.BigDecimal;
import java.util.Date;

import coffee.backoffice.casino.model.*;
import lombok.Data;

@Data
public class GamesRes {
    private Long id;
    private String nameTh;
    private Date createdAt;
    private Date updatedAt;
    private String updatedBy;
    private String nameEn;
    private String gameProductTypeCode;
    private String providerCode;
    private String displayName;
    private Boolean status;
    private String remark;
    private String gameCode;
    private Boolean enable;
    private String gameGroupCode;

    private String gameProviderNameEn;
    private String gameGroupNameEn;

    public void setEntityToRes(Games req) {
        this.setId(req.getId());
        this.setNameTh(req.getNameTh());
        this.setCreatedAt(req.getCreatedAt());
        this.setUpdatedAt(req.getUpdatedAt());
        this.setUpdatedBy(req.getUpdatedBy());
        this.setNameEn(req.getNameEn());
        this.setGameProductTypeCode(req.getGameProductTypeCode());
        this.setDisplayName(req.getDisplayName());
        this.setStatus(req.getStatus());
        this.setRemark(req.getRemark());
        this.setGameCode(req.getGameCode());
        this.setEnable(req.getEnable());
        this.setGameGroupCode(req.getGameGroupCode());
    }
}
