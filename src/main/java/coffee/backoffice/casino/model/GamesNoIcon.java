package coffee.backoffice.casino.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "games")
@Data
public class GamesNoIcon implements Serializable {
    private static final long serialVersionUID = 8828788383583503128L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_th")
    private String nameTh;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "game_code")
    private String gameCode;

    @Column(name = "game_product_type_code")
    private String gameProductTypeCode;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "provider_code")
    private String providerCode;
    @Column(name = "game_group_code")
    private String gameGroupCode;

}
