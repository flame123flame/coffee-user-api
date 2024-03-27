package coffee.backoffice.casino.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "game_product_type")
@Data
public class GameProductTypeNoIcon implements Serializable{
    private static final long serialVersionUID = 1470907264678448248L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_th", length = 255)
    private String nameTh;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by", length = 255)
    private String updatedBy;

    @Column(name = "name_en", length = 255)
    private String nameEn;

    @Column(name = "code", length = 255)
    private String code;
}
