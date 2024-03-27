package coffee.backoffice.finance.model;

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
@Table(name = "bank")
public class Bank implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1939843918781275813L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "bank_code")
	private String bankCode;
	
	@Column(name = "bank_name_en")
	private String bankNameEn;
	
	@Column(name = "bank_name_th")
	private String bankNameTh;
	
	@Column(name = "bank_url")
	private String bankUrl;
	
	@Column(name = "bank_img")
	private String bankImg;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "enable")
	private Boolean enable;
}
