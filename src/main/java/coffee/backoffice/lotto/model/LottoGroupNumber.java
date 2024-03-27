package coffee.backoffice.lotto.model;

import coffee.backoffice.casino.vo.req.GamesReq;
import coffee.backoffice.lotto.vo.req.LottoGroupNumberReq;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "lotto_group_number")
public class LottoGroupNumber implements Serializable {
    private static final long serialVersionUID = 1647433724668787289L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "updated_date")
    private Date updatedDate= new Date();
    @Column(name = "updated_by", length = 255)
    private String updatedBy = UserLoginUtil.getUsername();
    @Column(name = "created_by", length = 255)
    private String createdBy= UserLoginUtil.getUsername();
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "lotto_number_group_code", length = 255)
    private String lottoNumberGroupCode = GenerateRandomString.generateUUID();
    @Column(name = "username_owner", length = 255)
    private String usernameOwner;

    public void setReqToEntity(LottoGroupNumberReq req) {
        id = req.getId() == null ? id : req.getId();
        name = req.getName() == null ? name : req.getName();
    }
}
