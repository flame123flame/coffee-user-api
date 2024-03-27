package coffee.backoffice.casino.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "game_provider")
@Data
public class GameProviderNoIcon implements Serializable {


    private static final long serialVersionUID = 1010750557181346657L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_th", length = 255)
    private String nameTh;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by", length = 255)
    private String updatedBy;

    @Column(name = "name_en", length = 255)
    private String nameEn;

    @Column(name = "code", length = 255)
    private String code;

    @Column(name = "status_view")
    private Boolean statusView = true;

    @Column(name = "open_type")
    private String openType;


}
