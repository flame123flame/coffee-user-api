package coffee.backoffice.rebate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.rebate.model.RebateCondition;
import coffee.backoffice.rebate.repository.dao.RebateConditionDao;
import coffee.backoffice.rebate.repository.jpa.RebateConditionJpa;
import coffee.backoffice.rebate.vo.req.RebateConditionReq;
import coffee.backoffice.rebate.vo.res.RebateConditionRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class RebateConditionService {
    @Autowired
    private RebateConditionDao rebateConditionDao;

    @Autowired
    private RebateConditionJpa rebateConditionJpa;

    public RebateConditionRes getOne(Long id) throws Exception {
        Optional<RebateCondition> data = rebateConditionJpa.findById(id);
        if (!data.isPresent())
            return null;
        RebateConditionRes returnData = new RebateConditionRes();
        returnData.setEntityToRes(data.get());
        return returnData;
    }

    public List<RebateConditionRes> getAll() throws Exception {
        List<RebateCondition> data = rebateConditionJpa.findAll();
        if (data.isEmpty())
            return null;
        List<RebateConditionRes> returnData = new ArrayList<>();
        for (RebateCondition i : data) {
            RebateConditionRes oneData = new RebateConditionRes();
            oneData.setEntityToRes(i);
            returnData.add(oneData);
        }
        return returnData;
    }

    public DataTableResponse<RebateConditionRes> getPaginateModel(DatatableRequest req) {
        DataTableResponse<RebateConditionRes> dataTable = new DataTableResponse<>();
        DataTableResponse<RebateConditionRes> tag = new DataTableResponse<>();
        tag = rebateConditionDao.paginate(req);
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        dataTable.setData(tag.getData());
        return dataTable;
    }

    public RebateConditionRes insertOne(RebateConditionReq form) throws Exception {
        //  create insert
        RebateCondition dataInsert = new RebateCondition();
        dataInsert.setReqToEntity(form);
        dataInsert.setCreatedDate(new Date());
        dataInsert.setUpdatedDate(new Date());
        dataInsert.setCode(GenerateRandomString.generate());
        dataInsert.setUpdatedBy(UserLoginUtil.getUsername());
        RebateCondition saveData = rebateConditionJpa.save(dataInsert);
        //  return
        RebateConditionRes returnData = new RebateConditionRes();
        returnData.setEntityToRes(saveData);
        return returnData;
    }

    public RebateConditionRes updateOne(RebateConditionReq form) throws Exception {
        //  create insert
        RebateCondition dataUpdate = rebateConditionJpa.findById(form.getId()).get();
        dataUpdate.setReqToEntity(form);
        dataUpdate.setUpdatedBy(UserLoginUtil.getUsername());
        RebateCondition saveData = rebateConditionJpa.save(dataUpdate);
        //  return
        RebateConditionRes returnData = new RebateConditionRes();
        returnData.setEntityToRes(saveData);
        return returnData;
    }

    public RebateConditionRes delete(long id) throws Exception {
        //  create insert
        Optional<RebateCondition> dataDelete = rebateConditionJpa.findById(id);
        if (!dataDelete.isPresent())
            return null;
        rebateConditionJpa.deleteById(id);
        RebateConditionRes returnData = new RebateConditionRes();
        returnData.setEntityToRes(dataDelete.get());
        return returnData;
    }

}
