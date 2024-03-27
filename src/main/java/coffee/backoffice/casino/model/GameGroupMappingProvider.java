package coffee.backoffice.casino.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "game_group_mapping_provider")
@Data
public class GameGroupMappingProvider implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4895069001527809216L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "game_group_code")
	private String gameGroupCode;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "created_at")
	private Date createdAt = new Date();
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
}
