package coffee.backoffice.casino.model;

import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "game_tag_mapping_game")
@Data
public class GameTagMappingGame implements Serializable {

    private static final long serialVersionUID = 4777633939740313143L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "game_tag_code")
    private String gameTagCode;

    @Column(name = "game_code")
    private String gameCode;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt= new Date();

    @Column(name = "updated_by")
    private String updatedBy = UserLoginUtil.getUsername();

}
