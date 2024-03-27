package coffee.backoffice.lotto.vo.res;

import lombok.Data;

import java.util.List;

@Data
public class LottoSyncRes {
    private String status;
    private String massage;
    private List<BoSyncRes> data;

    @Data
    public static class BoSyncRes {
        private String lottoClassCode;
        private String className;
        private String lottoCategoryCode;
    }
}
