package coffee.backoffice.casino.vo.req;

import lombok.Data;

import java.util.Date;

@Data
public class GameProductTypeReq {
    private String nameTh;
    private String nameEn;
    private String code;
    private String icon;
}
