package coffee.backoffice.casino.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import coffee.backoffice.casino.vo.req.GamesReq;
import lombok.Data;

@Entity
@Table(name = "games")
@Data
public class Games implements Serializable {
	private static final long serialVersionUID = 9156825211988530475L;
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

	@Column(name = "game_product_type_code", length = 255)
	private String gameProductTypeCode;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "remark")
	private String remark;

	@Column(name = "game_code")
	private String gameCode;

	@Column(name = "enable")
	private Boolean enable;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "image1")
	private String image1;
	
	@Column(name = "image2")
	private String image2;

	@Column(name = "game_group_code")
	private String gameGroupCode;
}
