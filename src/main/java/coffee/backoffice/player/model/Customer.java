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
@Table(name = "customer")
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4345218495826645556L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "tag_code")
	private String tagCode;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "bank_account")
	private String bankAccount;

	@Column(name = "real_name")
	private String realName;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "enable")
	private Boolean enable = true;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "register_status")
	private Long registerStatus;

	@Column(name = "last_login_date")
	private Date lastLoginDate;

	@Column(name = "login_status")
	private Boolean loginStatus = false;

	@Column(name = "deposit_count")
	private Integer depositCount = 0;

	@Column(name = "bank_status")
	private String bankStatus;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "email")
	private String email;

	@Column(name = "last_provider")
	private String lastProvider;

	@Column(name = "mark")
	private String mark;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "token")
	private String token;

	@Column(name = "withdraw_count")
	private Integer withdrawCount = 0;

}
