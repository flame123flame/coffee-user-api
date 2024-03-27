package coffee.backoffice.casino.model;

import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "game_tag_mapping_game")
@Data
public class GameTagMappingGameJoin implements Serializable {
    private static final long serialVersionUID = 3818080227919501236L;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_tag_code", referencedColumnName = "code" , insertable = false , updatable = false)
    private GameTag gameTag;
  
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_code", referencedColumnName = "game_code" , insertable = false , updatable = false)
    private Games game;
}
