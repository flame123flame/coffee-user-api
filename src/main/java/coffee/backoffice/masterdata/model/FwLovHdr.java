package coffee.backoffice.masterdata.model;

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
@Table(name = "fw_lov_hdr")
@Data
public class FwLovHdr implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -7967199837814632889L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fw_lov_hdr_id")
	private Long fwLovHdrId;
	
	@Column(name = "lov_key", length = 25)
	private String lovKey;
	
	@Column(name = "lov_description", length = 255)
	private String lovDescription;
	
	@Column(name = "create_by", length = 25)
	private String createBy;

	@Column(name = "create_date")
	private Date createDate = new Date();

	@Column(name = "update_by", length = 25)
	private String updateBy;

	@Column(name = "update_date")
	private Date updateDate;
}
