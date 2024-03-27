package coffee.website.cashback.service;

import coffee.backoffice.cashback.model.CashbackHistory;
import coffee.backoffice.cashback.repository.jpa.CashbackHistoryRepository;
import coffee.backoffice.cashback.vo.res.CashbackHistoryUserRes;
import framework.utils.ConvertDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CashbackHistoryWebPlayerService {

    @Autowired
    private CashbackHistoryRepository cashbackHistoryRepository;

    public List<CashbackHistoryUserRes> getCashbackHistoryByUser(String username) {
        List<CashbackHistory> dataRes = cashbackHistoryRepository.findAllByUsernameOrderByCreatedDateDesc(username);
        List<CashbackHistoryUserRes> chRes = new ArrayList<CashbackHistoryUserRes>();
        CashbackHistoryUserRes historyRes;
        for(CashbackHistory res:dataRes) {
            historyRes = new CashbackHistoryUserRes();
            historyRes.setCashbackTitle(res.getCashbackTitle());
            historyRes.setActualCashback(res.getActualCashback());
            historyRes.setCreatedDate(ConvertDateUtils.formatDateToStringEn(res.getCreatedDate(), ConvertDateUtils.YYYY_MM_DD_HHMMSS));
            historyRes.setRemark(res.getRemark());
            chRes.add(historyRes);
        }
        return chRes;

    }

}
