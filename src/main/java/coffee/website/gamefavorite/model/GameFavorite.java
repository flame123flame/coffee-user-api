package coffee.website.gamefavorite.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import coffee.backoffice.promotion.vo.req.AppSettingReq;
import lombok.Data;

@Data
@Entity
@Table(name = "game_favorite")
public class GameFavorite implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -394512725662168536L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "game_code")
    private String gameCode;
    
    @Column(name = "game_name")
    private String gameName;

    @Column(name = "provider")
    private String provider;

    @Column(name = "count_play")
    private int countPlay;

    @Column(name = "view_status")
    private String viewStatus;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

}
