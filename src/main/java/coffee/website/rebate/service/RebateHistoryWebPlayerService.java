package coffee.website.rebate.service;

import coffee.backoffice.rebate.repository.dao.RebateHistoryDao;
import coffee.backoffice.rebate.repository.jpa.RebateHistoryRepository;
import coffee.backoffice.rebate.vo.res.RebateHistoryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RebateHistoryWebPlayerService {

    @Autowired
    private RebateHistoryDao rebateHistoryDao;

    public List<RebateHistoryRes> getAll() {
        List<RebateHistoryRes> dataRes = rebateHistoryDao.listRebateHistoryRes();
        return dataRes;
    }
}
