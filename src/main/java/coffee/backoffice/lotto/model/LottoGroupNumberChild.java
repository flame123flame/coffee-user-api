package coffee.backoffice.lotto.model;

import coffee.backoffice.lotto.vo.req.LottoGroupNumberChildReq;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberReq;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "lotto_group_number_child")
public class LottoGroupNumberChild implements Serializable {
    private static final long serialVersionUID = 2039126702143963889L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	@Column(name = "lotto_number" , length = 255)
    private String lottoNumber;
    @Column(name = "lotto_kind" , length = 255)
    private String lottoKind;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "created_by" , length = 255)
    private String createdBy = UserLoginUtil.getUsername();
    @Column(name = "updated_by" , length = 255)
    private String updatedBy = UserLoginUtil.getUsername();
    @Column(name = "updated_date")
    private Date updatedDate = new Date();
    @Column(name = "lotto_group_number_code" , length = 255)
    private String lottoGroupNumberCode;
    @Column(name = "lotto_group_number_child_code" , length = 255)
    private String lottoGroupNumberChildCode= GenerateRandomString.generateUUID();

    public void setReqToEntity(LottoGroupNumberChildReq req) {
        id = req.getId() == null ? id : req.getId();
        lottoNumber = req.getLottoNumber() == null ? lottoNumber : req.getLottoNumber();
        lottoKind = req.getLottoKind() == null ? lottoKind : req.getLottoKind();
    }
}
