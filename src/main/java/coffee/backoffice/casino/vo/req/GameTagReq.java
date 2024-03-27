package coffee.backoffice.casino.vo.req;

import lombok.Data;

import java.util.Date;

@Data
public class GameTagReq {
    private String code;
    private String nameTh;
    private String nameEn;
    private String userView;
    private Long priority;
    private Boolean isActive;

}
