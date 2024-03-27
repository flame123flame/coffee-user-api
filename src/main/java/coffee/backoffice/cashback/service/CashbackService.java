package coffee.backoffice.cashback.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import coffee.backoffice.cashback.model.CashbackBatchWaiting;
import coffee.backoffice.cashback.repository.jpa.CashBackBatchWaitingJpa;
import coffee.backoffice.cashback.vo.res.CashbackStatMonthDailyRes;
import coffee.backoffice.cashback.vo.res.CashbackStatTitleRes;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.cashback.model.Cashback;
import coffee.backoffice.cashback.model.CashbackCondition;
import coffee.backoffice.cashback.repository.dao.CashbackDao;
import coffee.backoffice.cashback.repository.jpa.CashbackConditionJpa;
import coffee.backoffice.cashback.repository.jpa.CashbackJpa;
import coffee.backoffice.cashback.vo.req.CashbackConditionReq;
import coffee.backoffice.cashback.vo.req.CashbackReq;
import coffee.backoffice.cashback.vo.res.CashbackConditionRes;
import coffee.backoffice.cashback.vo.res.CashbackRes;
import coffee.backoffice.casino.vo.res.GameProductTypeRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;

@Service
public class CashbackService {
	@Autowired
	private CashbackDao cashbackDao;
	@Autowired
	private CashbackJpa cashbackJpa;
	@Autowired
	private CashbackConditionJpa cashbackConditionJpa;
	@Autowired
	private CashBackBatchWaitingJpa cashBackBatchWaitingJpa;

	private ModelMapper modelMapper = new ModelMapper();

	public CashbackRes getOne(String code) throws Exception {
		Cashback data = cashbackJpa.findByCode(code);
		CashbackRes returnData = new CashbackRes();
		returnData.setEntityToRes(data);
		List<CashbackCondition> cashbackConditions = cashbackConditionJpa.findAllByCashbackCode(code);
		List<CashbackConditionRes> conditionRes = new ArrayList<CashbackConditionRes>();
		CashbackConditionRes setCC;
		for (CashbackCondition cc : cashbackConditions) {
			setCC = new CashbackConditionRes();
			setCC.setEntityToRes(cc);
			conditionRes.add(setCC);

		}
		returnData.setCashbackConditionResList(conditionRes);
		return returnData;
	}

	public List<CashbackRes> getAll() throws Exception {
		List<Cashback> data = cashbackJpa.findAll();
		if (data.isEmpty())
			return null;
		List<CashbackRes> returnData = new ArrayList<>();
		CashbackRes oneData;
		for (Cashback i : data) {
			oneData = new CashbackRes();
			oneData.setEntityToRes(i);
			returnData.add(oneData);
		}
		return returnData;
	}

	public DataTableResponse<CashbackRes> getPaginateModel(DatatableRequest req) throws Exception {
		DataTableResponse<CashbackRes> dataTable = new DataTableResponse<>();
		DataTableResponse<CashbackRes> tag = new DataTableResponse<>();
		tag = cashbackDao.paginate(req);
		List<CashbackRes> data = tag.getData();
		for (CashbackRes item : data) {
			GameProductTypeRes res = new GameProductTypeRes();
//			res = gameProductTypeService.getByCode(item.getProductTypeCode());
			item.setGameProductTypeRes(res);
		}
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public DataTableResponse<CashbackStatTitleRes> getPaginateModelCashbackStatTitle(DatatableRequest req) throws Exception {
		DataTableResponse<CashbackStatTitleRes> dataTable = new DataTableResponse<>();
		DataTableResponse<CashbackStatTitleRes> tag = new DataTableResponse<>();
		tag = cashbackDao.paginateCashbackStatTitle(req);
		List<CashbackStatTitleRes> data = tag.getData();
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public DataTableResponse<CashbackStatMonthDailyRes> getPaginateModelCashbackStatMonth(DatatableRequest req) throws Exception {
		DataTableResponse<CashbackStatMonthDailyRes> dataTable = new DataTableResponse<>();
		DataTableResponse<CashbackStatMonthDailyRes> tag = new DataTableResponse<>();
		tag = cashbackDao.paginateCashbackStatMonth(req);
		List<CashbackStatMonthDailyRes> data = tag.getData();
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public DataTableResponse<CashbackStatMonthDailyRes> getPaginateModelCashbackStatDaily(DatatableRequest req) throws Exception {
		DataTableResponse<CashbackStatMonthDailyRes> dataTable = new DataTableResponse<>();
		DataTableResponse<CashbackStatMonthDailyRes> tag = new DataTableResponse<>();
		tag = cashbackDao.paginateCashbackStatDaily(req);
		List<CashbackStatMonthDailyRes> data = tag.getData();
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
	}

	public void insertOne(CashbackReq form) throws Exception {
		// create insert
		Cashback dataInsert = new Cashback();
		dataInsert.setReqToEntity(form);

		if (!form.getCashbackConditionList().isEmpty()) {
			List<CashbackConditionReq> CashbackCondition = form.getCashbackConditionList();
			for (CashbackConditionReq item : CashbackCondition) {
				// create insert
				CashbackCondition newCondition = new CashbackCondition();
				newCondition.setReqToEntity(item);
				newCondition.setCashbackCode(dataInsert.getCode());
				cashbackConditionJpa.save(newCondition);
			}
		}
		cashbackJpa.save(dataInsert);
	}

	public void updateOne(Long id, CashbackReq form) throws Exception {
		// create insert
		Cashback dataUpdate = cashbackJpa.findById(id).get();
		dataUpdate.setReqToEntity(form);
		dataUpdate.setUpdatedBy(UserLoginUtil.getUsername());
		dataUpdate.setUpdatedDate(new Date());

		List<CashbackCondition> cashbackConditions = cashbackConditionJpa.findAllByCashbackCode(dataUpdate.getCode());
		cashbackConditionJpa.deleteAll(cashbackConditions);
		if (!form.getCashbackConditionList().isEmpty()) {
			List<CashbackConditionReq> rebateCondition = form.getCashbackConditionList();
			for (CashbackConditionReq item : rebateCondition) {
				// create insert
				CashbackCondition newCondition = new CashbackCondition();
				newCondition.setReqToEntity(item);
				newCondition.setCashbackCode(dataUpdate.getCode());
				cashbackConditionJpa.save(newCondition);
			}
		}
		cashbackJpa.save(dataUpdate);
	}

	@Transactional
	public void delete(String code) throws Exception {
		cashbackJpa.deleteByCode(code);
		cashbackConditionJpa.deleteByCashbackCode(code);
	}


	public CashbackRes getDetail(String code) {
		Cashback cashback = cashbackJpa.findByCode(code);
		CashbackRes cashbackRes = new CashbackRes();
		modelMapper.map(cashback,cashbackRes);
		List<CashbackBatchWaiting> cashbackBatchWaiting = cashBackBatchWaitingJpa.findAllByCashbackCode(code);
		cashbackRes.setCashbackBatchWaitingList(cashbackBatchWaiting);
		List<CashbackCondition> cashbackCondition = cashbackConditionJpa.findAllByCashbackCode(code);
		List<CashbackConditionRes> cashbackConditionRes = modelMapper.map(cashbackCondition,new TypeToken<List<CashbackCondition>>(){}.getType());
		cashbackRes.setCashbackConditionResList(cashbackConditionRes);
		return cashbackRes;
	}
}
