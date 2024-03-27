package coffee.backoffice.lotto.vo.req;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import coffee.backoffice.lotto.model.TimeSell;

@Getter
@Setter
public class LottoTimeDetailReq {
	
    private Long lottoClassId;
    private String lottoCategoryCode;
    private String lottoClassCode;
    private String lottoClassName;
    private String typeInstallment;
    private BigDecimal commissionPercent;
    private String ruleDes;
    private List<TimeSell> timeSell;
    
}
