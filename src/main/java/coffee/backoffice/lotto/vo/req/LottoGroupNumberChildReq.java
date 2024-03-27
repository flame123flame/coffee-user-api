package coffee.backoffice.lotto.vo.req;

import lombok.Data;

import java.util.Date;

@Data
public class LottoGroupNumberChildReq {
    private Long id;
    private String lottoNumber;
    private String lottoKind;
    private String lottoGroupNumberCode;
}
