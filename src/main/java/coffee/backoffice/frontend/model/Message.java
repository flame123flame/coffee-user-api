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
@Table(name = "message")
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 43657024773714442L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "message_code")
	private String messageCode;

	@Column(name = "group_codes")
	private String groupCodes;

	@Column(name = "subject")
	private String subject;

	@Column(name = "web_message")
	private String webMessage;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate = new Date();

	@Column(name = "promo_code")
	private String promoCode;

	@Column(name = "message_type")
	private String messageType;
}
