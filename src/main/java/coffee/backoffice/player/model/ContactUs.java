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
@Table(name = "contact_us")
public class ContactUs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2044575767486335774L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "real_name")
	private String realName;
	
	@Column(name = "group_name")
	private String groupName;
	
	@Column(name = "email")
	private String email;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "bank_account")
	private String bankAccount;
	
	@Column(name = "bank_status")
	private String bankStatus;

	@Column(name = "registered_date")
	private Date registeredDate;

	@Column(name = "last_login")
	private Date lastLogin;

	@Column(name = "deposit_count")
	private Integer depositCount = 0;
	
	@Column(name = "withdraw_count")
	private Integer withdrawCount = 0;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "status")
	private String status;

}
