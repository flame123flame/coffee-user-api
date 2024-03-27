package coffee.backoffice.casino.vo.res;

import coffee.backoffice.casino.model.GameProvider;
import lombok.Data;

import java.util.Date;

@Data
public class GameProviderRes {
    private Long id;
    private String nameTh;
    private Date createdAt;
    private Date updatedAt;
    private String updatedBy;
    private String nameEn;
    private String code;
    private String openType;
    private Boolean statusView;
    private String iconPortrait;
    private String iconLandscape;
    public void setEntityToRes(GameProvider req) {
        this.setId(req.getId());
        this.setNameTh(req.getNameTh());
        this.setCreatedAt(req.getCreatedAt());
        this.setUpdatedAt(req.getUpdatedAt());
        this.setUpdatedBy(req.getUpdatedBy());
        this.setNameEn(req.getNameEn());
        this.setCode(req.getCode());
        this.setIconLandscape(req.getIconLandscape());
        this.setIconPortrait(req.getIconPortrait());
    }
}
