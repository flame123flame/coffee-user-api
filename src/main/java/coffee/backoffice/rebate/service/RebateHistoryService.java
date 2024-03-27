package coffee.backoffice.rebate.service;

import java.util.Date;
import java.util.List;

import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.vo.res.PromotionDatatableRes;
import coffee.backoffice.rebate.vo.res.RebateHistoryDatatableRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.rebate.model.RebateHistory;
import coffee.backoffice.rebate.repository.dao.RebateHistoryDao;
import coffee.backoffice.rebate.repository.jpa.RebateHistoryRepository;
import coffee.backoffice.rebate.vo.res.RebateHistoryRes;

@Service
public class RebateHistoryService {

	@Autowired
	private RebateHistoryDao rebateHistoryDao;

	@Autowired
	private RebateHistoryRepository rebateHistoryRepository;
	
	public List<RebateHistory> getRebateHistoryUsername(String username){
		return rebateHistoryRepository.findByUsername(username);
	}
	
	public List<RebateHistory> getRebateHistoryUsernameAfter(String username,Date createdDate) {
		return rebateHistoryRepository.findByUsernameAndCreatedDateAfter(username,createdDate);
	}
	
	public List<RebateHistory> getRebateHistoryUsernameBefore(String username,Date createdDate) {
		return rebateHistoryRepository.findByUsernameAndCreatedDateBefore(username,createdDate);
	}
	
	public List<RebateHistory> getRebateHistoryUsernameBetween(String username,Date start,Date end) {
		return rebateHistoryRepository.findByUsernameAndCreatedDateBetween(username,start,end);
	}

	public DataTableResponse<RebateHistoryDatatableRes> getPaginate(DatatableRequest req) {
		DataTableResponse<RebateHistoryDatatableRes> dataTable = new DataTableResponse<>();
		DataTableResponse<RebateHistoryDatatableRes> newData = new DataTableResponse<>();
		newData = rebateHistoryDao.paginate(req);
		List<RebateHistoryDatatableRes> data = newData.getData();
		dataTable.setRecordsTotal(newData.getRecordsTotal());
		dataTable.setDraw(newData.getDraw());
		dataTable.setData(newData.getData());
		return dataTable;
	}
}
