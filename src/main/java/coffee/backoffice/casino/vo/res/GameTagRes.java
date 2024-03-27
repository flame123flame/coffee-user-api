package coffee.backoffice.casino.vo.res;

import lombok.Data;

import java.util.Date;

@Data
public class GameTagRes {
    private Long id;
    private String code;
    private String nameTh;
    private String nameEn;
    private String userView;
    private Date createdAt;
    private Date updatedAt;
    private String updatedBy;
    private Boolean isActive;
    private Long priority;

}
