package coffee.backoffice.casino.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GamesReq {
	private Long id;
	private String nameTh;
	private Date createdAt;
	private Date updatedAt;
	private String updatedBy;
	private String nameEn;
	private String code;
	private String gameProductTypeCode;
	private String providerCode;
	private Boolean platformMapp;
	private Boolean platformMhFive;
	private Boolean platformMini;
	private BigDecimal minRtp;
	private BigDecimal maxRtp;
	private String gameGroupCode;
	private String displayName;
	private Boolean status;
	private String remark;
	private String gameCode;
	private String image1;
	private String image2;
	private Boolean platformPcDl;
	private Boolean platformPc;
	private Boolean enable;
	private List<GameTagMappingGameReq> gameTagMappingGame;

}
