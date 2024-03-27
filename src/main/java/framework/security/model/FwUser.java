package framework.security.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.utils.UserLoginUtil;
import lombok.Data;

@Entity
@Table(name = "fw_user")
@Data
public class FwUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -725000429402385783L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = " fw_users_id")
	private Long fwUsersId;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "last_login_ip")
	private String lastLoginIp;
	@Column(name = "is_disable")
	private Boolean isDisable;
	@Column(name = "is_active")
	private Boolean isActive;
	@Column(name = "created_by")
	private String createdBy = UserLoginUtil.getUsername();
	@Column(name = "updated_by")
	private String updatedBy = UserLoginUtil.getUsername();
	@Column(name = "updated_date")
	private Date updatedDate = new Date();
	@Column(name = "last_login_time")
	private Date lastLoginTime;
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
}
