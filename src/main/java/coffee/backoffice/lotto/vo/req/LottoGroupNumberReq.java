package coffee.backoffice.lotto.vo.req;

import coffee.backoffice.lotto.model.LottoGroupNumberChild;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LottoGroupNumberReq {
    private Long id;
    private String name;
    private String usernameOwner;
    private List<LottoGroupNumberChildReq> lottoGroupNumberChildList;

}
