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
@Table(name = "fw_constant")
@Data
public class FwConstant implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 9005839745905947221L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fw_constant_id")
	private Long fwConstantId;
	
	@Column(name = "constant_key", length = 25)
	private String constantKey;
	
	@Column(name = "constant_value", length = 255)
	private String constantValue;
	
	@Column(name = "create_by", length = 25)
	private String createBy;

	@Column(name = "create_date")
	private Date createDate = new Date();

	@Column(name = "update_by", length = 25)
	private String updateBy;

	@Column(name = "update_date")
	private Date updateDate;
}
