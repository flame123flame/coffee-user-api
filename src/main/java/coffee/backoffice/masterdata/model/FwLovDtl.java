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
@Table(name = "fw_lov_dtl")
@Data
public class FwLovDtl implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6029883860584501012L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fw_lov_dtl_id")
	private Long fwLovDtlId;

	@Column(name = "lov_key", length = 25)
	private String lovKey;
	
	@Column(name = "value_th1", length = 255)
	private String valueTh1;

	@Column(name = "value_en1", length = 255)
	private String valueEn1;

	@Column(name = "value_th2", length = 255)
	private String valueTh2;

	@Column(name = "value_en2", length = 255)
	private String valueEn2;
	
	@Column(name = "code_detail", length = 255)
	private String codeDetail;

	@Column(name = "seq")
	private Integer seq;

	@Column(name = "create_by", length = 25)
	private String createBy;

	@Column(name = "create_date")
	private Date createDate = new Date();

	@Column(name = "update_by", length = 25)
	private String updateBy;

	@Column(name = "update_date")
	private Date updateDate;
}
