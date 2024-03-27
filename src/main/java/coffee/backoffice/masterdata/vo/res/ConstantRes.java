package coffee.backoffice.masterdata.vo.res;

import lombok.Data;

@Data
public class ConstantRes {
	
	private Long fwConstantId;
	private String constantKey;
	private String constantValue;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
}
