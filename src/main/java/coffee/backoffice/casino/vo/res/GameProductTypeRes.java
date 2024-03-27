package coffee.backoffice.casino.vo.res;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GameProductTypeRes {
    private Long id;
    private String nameTh;
    private Date createdAt;
    private Date updatedAt;
    private String updatedBy;
    private String nameEn;
    private String code;
    private String iconUrl;

}
