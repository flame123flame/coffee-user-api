package coffee.backoffice.cashback.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.cashback.model.CashbackCondition;
import coffee.backoffice.cashback.repository.dao.CashbackConditionDao;
import coffee.backoffice.cashback.repository.jpa.CashbackConditionJpa;
import coffee.backoffice.cashback.vo.req.CashbackConditionReq;
import coffee.backoffice.cashback.vo.res.CashbackConditionRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class CashbackConditionService {
	@Autowired
    private CashbackConditionDao cashBackConditionDao;

    @Autowired
    private CashbackConditionJpa cashBackConditionJpa;

    public CashbackConditionRes getOne(Long id) throws Exception {
        Optional<CashbackCondition> data = cashBackConditionJpa.findById(id);
        if (!data.isPresent())
            return null;
        CashbackConditionRes returnData = new CashbackConditionRes();
        returnData.setEntityToRes(data.get());
        return returnData;
    }

    public List<CashbackConditionRes> getAll() throws Exception {
        List<CashbackCondition> data = cashBackConditionJpa.findAll();
        if (data.isEmpty())
            return null;
        List<CashbackConditionRes> returnData = new ArrayList<>();
        for (CashbackCondition i : data) {
            CashbackConditionRes oneData = new CashbackConditionRes();
            oneData.setEntityToRes(i);
            returnData.add(oneData);
        }
        return returnData;
    }

    public DataTableResponse<CashbackConditionRes> getPaginateModel(DatatableRequest req) {
        DataTableResponse<CashbackConditionRes> dataTable = new DataTableResponse<>();
        DataTableResponse<CashbackConditionRes> tag = new DataTableResponse<>();
        tag = cashBackConditionDao.paginate(req);
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        dataTable.setData(tag.getData());
        return dataTable;
    }

    public CashbackConditionRes insertOne(CashbackConditionReq form) throws Exception {
        //  create insert
        CashbackCondition dataInsert = new CashbackCondition();
        dataInsert.setReqToEntity(form);
        dataInsert.setCreatedDate(new Date());
        dataInsert.setUpdatedDate(new Date());
        dataInsert.setCode(GenerateRandomString.generate());
        dataInsert.setUpdatedBy(UserLoginUtil.getUsername());
        CashbackCondition saveData = cashBackConditionJpa.save(dataInsert);
        //  return
        CashbackConditionRes returnData = new CashbackConditionRes();
        returnData.setEntityToRes(saveData);
        return returnData;
    }

    public CashbackConditionRes updateOne(CashbackConditionReq form) throws Exception {
        //  create insert
        CashbackCondition dataUpdate = cashBackConditionJpa.findById(form.getId()).get();
        dataUpdate.setReqToEntity(form);
        dataUpdate.setUpdatedBy(UserLoginUtil.getUsername());
        CashbackCondition saveData = cashBackConditionJpa.save(dataUpdate);
        //  return
        CashbackConditionRes returnData = new CashbackConditionRes();
        returnData.setEntityToRes(saveData);
        return returnData;
    }

    public CashbackConditionRes delete(long id) throws Exception {
        //  create insert
        Optional<CashbackCondition> dataDelete = cashBackConditionJpa.findById(id);
        if (!dataDelete.isPresent())
            return null;
        cashBackConditionJpa.deleteById(id);
        CashbackConditionRes returnData = new CashbackConditionRes();
        returnData.setEntityToRes(dataDelete.get());
        return returnData;
    }

}
