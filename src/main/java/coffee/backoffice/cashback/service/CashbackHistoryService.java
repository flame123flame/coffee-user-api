package coffee.backoffice.cashback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.cashback.model.CashbackHistory;
import coffee.backoffice.cashback.repository.dao.CashbackHistoryDao;
import coffee.backoffice.cashback.repository.jpa.CashbackHistoryRepository;
import coffee.backoffice.cashback.vo.res.CashbackHistoryRes;
import coffee.backoffice.cashback.vo.res.CashbackHistoryUserRes;
import framework.utils.ConvertDateUtils;

@Service
public class CashbackHistoryService {

	@Autowired
	private CashbackHistoryDao cashbackHistoryDao;

	@Autowired
	private CashbackHistoryRepository cashbackHistoryRepository;

	public List<CashbackHistoryRes> getAll() {
		List<CashbackHistoryRes> dataRes = cashbackHistoryDao.listRebateHistoryRes();
		return dataRes;
	}

}
