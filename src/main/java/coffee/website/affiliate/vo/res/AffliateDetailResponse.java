package coffee.website.affiliate.vo.res;

import java.math.BigDecimal;
import java.util.List;

import coffee.website.affiliate.vo.model.ChannelDetail;
import lombok.Data;

@Data
public class AffliateDetailResponse {

	private Integer downLineOneCount;
	private Integer downLineTwoCount;
	private BigDecimal currentOneIncome;
	private BigDecimal currentTwoIncome;
	private BigDecimal totalIncome;
	private String recommendCode;
	private List<ChannelDetail> channelDetailList;
}
