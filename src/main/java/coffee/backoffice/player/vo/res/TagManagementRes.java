package coffee.backoffice.player.vo.res;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;

import coffee.backoffice.player.model.TagManagement;

@Getter
@Setter
public class TagManagementRes {
    private Long id;
    private String name;
    private String description;
    private String remark;
    private Long totalPlayers;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
    private String tagCode;

    public void setEntityToRes(TagManagement entity){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        setUpdatedBy(entity.getUpdatedBy());
        setCreatedAt(simpleDateFormat.format(entity.getCreatedAt()));
        setDescription(entity.getDescription());
        setId(entity.getId());
        setName(entity.getName());
        setTagCode(entity.getTagCode());
        setRemark(entity.getRemark());
        setTotalPlayers(entity.getTotalPlayers());
        setUpdatedAt(simpleDateFormat.format(entity.getUpdatedAt()));
    }
}
