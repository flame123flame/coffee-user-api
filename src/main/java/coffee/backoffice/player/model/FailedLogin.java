package coffee.backoffice.player.model;

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
@Table(name = "failed_login")
public class FailedLogin implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -1644118745259114563L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "failed_login_code")
	private String failedLoginCode;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "real_name")
	private String realName;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "last_failed_login")
	private Date lastFailedLogin;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "count_failed_login")
	private int countFailedLogin;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@Column(name = "platform")
	private String platform;
	
	@Column(name = "browser_name")
	private String browserName;
	
	@Column(name = "browser_version")
	private String browserVersion;
	
	@Column(name = "created_date")
	private Date createdDate;
	
}
