package coffee.backoffice.casino.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "provider_summary")
public class ProviderSummary implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2355370925098727672L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "username" , length = 255)
    private String username;
    
    @Column(name = "total_valids_bet")
    private BigDecimal totalValidsBet;
    
    @Column(name = "total_stake")
    private BigDecimal totalStake;
    
    @Column(name = "total_win")
    private BigDecimal totalWin;
    
    @Column(name = "total_loss")
    private BigDecimal totalLoss;

    @Column(name = "bet_daily")
    private BigDecimal betDaily;
    
    @Column(name = "updated_date")
    private Date updatedDate;
    
    @Column(name = "provider_code" , length = 255)
    private String providerCode;
    
    @Column(name = "win_loss")
    private BigDecimal winLoss;
    
    @Column(name = "loss_daily")
    private BigDecimal lossDaily;

}
