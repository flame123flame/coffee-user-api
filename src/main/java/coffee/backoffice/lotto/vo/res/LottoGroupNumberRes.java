package coffee.backoffice.lotto.vo.res;

import coffee.backoffice.lotto.model.LottoGroupNumber;
import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LottoGroupNumberRes {
    private Long id;
    private Date createdDate;
    private Date updatedDate;
    private String updatedBy;
    private String createdBy;
    private String name;
    private String lottoNumberGroupCode;
    private String usernameOwner;
    private List<LottoGroupNumberChild> lottoGroupNumberChildList;
    private Long lottoGroupNumberChildCount = Long.valueOf(0);

    public void setEntityToRes(LottoGroupNumber req){
        this.id = req.getId();
        this.createdDate = req.getCreatedDate();
        this.updatedDate = req.getUpdatedDate();
        this.updatedBy = req.getUpdatedBy();
        this.usernameOwner = req.getUsernameOwner();
        this.createdBy = req.getCreatedBy();
        this.name = req.getName();
        this.lottoNumberGroupCode = req.getLottoNumberGroupCode();
    }
}
