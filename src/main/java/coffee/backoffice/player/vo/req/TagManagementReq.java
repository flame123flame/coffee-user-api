package coffee.backoffice.player.vo.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TagManagementReq {
    private Long id;
    private String name;
    private String description;
    private String remark;
    private Long totalPlayers;
}
