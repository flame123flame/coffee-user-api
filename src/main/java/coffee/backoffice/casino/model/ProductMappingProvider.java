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
@Table(name = "game_product_type_mapping_provider")
@Data
public class ProductMappingProvider implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6969244246367808276L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "created_at")
	private Date createdAt = new Date();
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;

}
