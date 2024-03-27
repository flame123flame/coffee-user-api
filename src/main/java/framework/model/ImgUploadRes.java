package framework.model;

import coffee.backoffice.lotto.vo.res.LottoBuyRes;
import lombok.Data;

@Data
public class ImgUploadRes {
    private String status;
    private String massage;
    private imgRes data;

    @Data
    public static class imgRes {
        private String savedPath;
    }
}
