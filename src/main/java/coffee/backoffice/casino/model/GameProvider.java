package coffee.backoffice.casino.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "game_provider")
@Data
public class GameProvider implements Serializable {
    private static final long serialVersionUID = 1482552164622948534L;
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

    @Column(name = "icon_portrait")
    private String iconPortrait;

    @Column(name = "icon_landscape")
    private String iconLandscape;

    @Column(name = "status_view")
    private Boolean statusView = true;

    @Column(name = "open_type")
    private String openType;
}
