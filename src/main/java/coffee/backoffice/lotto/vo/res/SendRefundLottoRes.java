package coffee.backoffice.lotto.vo.res;


import lombok.Data;

@Data
public class SendRefundLottoRes {
    private String status;
    private String massage;
    private RefundLottoRes data;
}
