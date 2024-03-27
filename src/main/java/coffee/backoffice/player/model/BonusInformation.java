package coffee.backoffice.player.model;

import framework.utils.GenerateRandomString;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "bonus_information")
public class BonusInformation implements Serializable {
    private static final long serialVersionUID = 2885881983526876645L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code = GenerateRandomString.generateUUID();
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_date")
    private Date updatedDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "total_registration_bonus")
    private BigDecimal totalRegistrationBonus;
    @Column(name = "total_manual_bonus")
    private BigDecimal totalManualBonus;
    @Column(name = "total_deposit_bonus")
    private BigDecimal totalDepositBonus;
    @Column(name = "total_event_bonus")
    private BigDecimal totalEventBonus;
    @Column(name = "total_rebate_bonus")
    private BigDecimal totalRebateBonus;
    @Column(name = "total_cashback_bonus")
    private BigDecimal totalCashbackBonus;
    @Column(name = "total_amount_of_manual")
    private BigDecimal totalAmountOfManual;
    @Column(name = "username")
    private String username;
}
