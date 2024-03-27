package coffee.backoffice.affiliate.vo.res;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AffiliatePlayAmountRes {

        private String productTypeNameTh;
        private String productTypeNameEn;
        private String iconUrl;
        private BigDecimal playAmount;
}
