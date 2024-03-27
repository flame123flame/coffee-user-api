package coffee.backoffice.affiliate.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "affiliate_network")
public class AffiliateNetwork implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3711868902025525311L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "username")
    private String username;
    
    @Column(name = "affiliate_code")
    private String affiliateCode;
    
    @Column(name = "affiliate_code_up")
    private String affiliateCodeUp;
    
    @Column(name = "register_date")
    private Date registerDate = new Date();

}
