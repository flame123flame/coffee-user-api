package coffee.backoffice.finance.vo.res;

import lombok.Data;

@Data
public class BankRes {
	private Long id;
	private String bankCode;
	private String bankNameEn;
	private String bankNameTh;
	private String bankUrl;
	private String bankImg;
	private Boolean enable;
}
