package coffee.backoffice.frontend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "landing")
public class Landing implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6523104796250289465L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "header")
	private String header;

	@Column(name = "detail")
	private String detail;

	@Column(name = "img")
	private String img;

	@Column(name = "config_path")
	private String configPath;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

}