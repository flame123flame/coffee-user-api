package coffee.backoffice.casino.vo.req;

import lombok.Data;

import java.util.Date;

@Data
public class GameProviderReq {
    private String nameTh;
    private String nameEn;
    private String code;
    private String iconPortrait;
    private String iconLandscape;
    private Boolean status;
    private String openType;
}
