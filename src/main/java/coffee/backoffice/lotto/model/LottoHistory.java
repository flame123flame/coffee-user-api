package coffee.backoffice.lotto.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "lotto_history")
public class LottoHistory implements Serializable {

    private static final long serialVersionUID = 81315385899379836L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "lotto_history_code" , length = 255)
    private String lottoHistoryCode;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "prize")
    private BigDecimal prize;
	@Column(name = "number" , length = 10)
    private String number;
    @Column(name = "lotto_kind_code" , length = 255)
    private String lottoKindCode;
    @Column(name = "lotto_class_code" , length = 255)
    private String lottoClassCode;
    @Column(name = "created_by" , length = 255)
    private String createdBy;
    @Column(name = "updated_by" , length = 255)
    private String updatedBy;
    @Column(name = "transaction_group_code" , length = 255)
    private String transactionGroupCode;
    @Column(name = "username" , length = 255)
    private String username;
}
