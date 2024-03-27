package coffee.backoffice.finance.vo.req;

import lombok.Data;

@Data
public class BankReq {
	private Long id;
	private String bankCode;
	private String bankNameEn;
	private String bankNameTh;
	private String bankUrl;
	private String bankImg;
	private Boolean enable;
}
