package coffee.backoffice.lotto.vo.res;

import coffee.backoffice.lotto.model.LottoGroupNumber;
import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import lombok.Data;

import java.util.Date;

@Data
public class LottoGroupNumberChildRes {
    private Long id;
    private String lottoNumber;
    private String lottoKind;
    private Date createdDate;
    private String createdBy;
    private String updatedBy;
    private Date updatedDate;
    private String lottoGroupNumberCode;
    private String lottoGroupNumberChildCode;

    public void setEntityToRes(LottoGroupNumberChild req) {
        this.id = req.getId();
        this.lottoNumber = req.getLottoNumber();
        this.lottoKind = req.getLottoKind();
        this.createdDate = req.getCreatedDate();
        this.createdBy = req.getCreatedBy();
        this.updatedBy = req.getUpdatedBy();
        this.updatedDate = req.getUpdatedDate();
        this.lottoGroupNumberCode = req.getLottoGroupNumberCode();
        this.lottoGroupNumberChildCode = req.getLottoGroupNumberChildCode();
    }
}
