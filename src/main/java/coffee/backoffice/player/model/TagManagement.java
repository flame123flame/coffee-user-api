package coffee.backoffice.player.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "tag_management")
public class TagManagement implements Serializable {

    private static final long serialVersionUID = 8850258512480089921L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name" ,length=255)
    private String name;
    @Column(name = "description" ,length=255)
    private String description;
    @Column(name = "remark" ,length=255)
    private String remark;
    @Column(name = "total_players")
    private Long totalPlayers;
    @Column(name = "updated_by" ,length=255)
    private String updatedBy;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "tag_code",length=255)
    private String tagCode;
}
