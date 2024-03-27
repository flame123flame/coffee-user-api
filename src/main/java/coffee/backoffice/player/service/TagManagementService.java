package coffee.backoffice.player.service;

import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.player.model.TagManagement;
import coffee.backoffice.player.repository.dao.TagManagementDao;
import coffee.backoffice.player.repository.jpa.TagManagementJpa;
import coffee.backoffice.player.vo.req.TagManagementReq;
import coffee.backoffice.player.vo.res.TagManagementRes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TagManagementService {

    @Autowired
    private TagManagementDao tagManagementDao;

    @Autowired
    private TagManagementJpa tagManagementJpa;

    public TagManagementRes getOne(Long id) throws Exception {
        Optional<TagManagement> data = tagManagementJpa.findById(id);
        if (!data.isPresent())
            return null;
        TagManagementRes returnData = new TagManagementRes();
        returnData.setEntityToRes(data.get());
        return returnData;
    }
    public TagManagementRes getOneByCode(String code) throws Exception {
        Optional<TagManagement> data = tagManagementJpa.findByTagCode(code);
        if (!data.isPresent())
            return null;
        TagManagementRes returnData = new TagManagementRes();
        returnData.setEntityToRes(data.get());
        return returnData;
    }

    public List<TagManagementRes> getAll() throws Exception {
        List<TagManagement> data = tagManagementJpa.findAll();
        if (data.isEmpty())
            return null;
        List<TagManagementRes> returnData = new ArrayList<>();
        for (TagManagement i : data) {
            TagManagementRes oneData = new TagManagementRes();
            oneData.setEntityToRes(i);
            returnData.add(oneData);
        }
        return returnData;
    }

    public DataTableResponse<TagManagementRes> getPaginateModel(DatatableRequest req) {
        DataTableResponse<TagManagementRes> dataTable = new DataTableResponse<>();
        DataTableResponse<TagManagementRes> tag = new DataTableResponse<>();
        tag = tagManagementDao.paginate(req);
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        dataTable.setData(tag.getData());
        return dataTable;
    }

    public TagManagementRes insertOne(TagManagementReq form) throws Exception {
        //  create insert
        TagManagement dataInsert = new TagManagement();
        dataInsert.setCreatedAt(new Date());
        dataInsert.setDescription(form.getDescription());
        dataInsert.setName(form.getName());
        dataInsert.setRemark(form.getRemark());
        dataInsert.setTotalPlayers(form.getTotalPlayers());
        dataInsert.setUpdatedBy(UserLoginUtil.getUsername());
        dataInsert.setTagCode(GenerateRandomString.generate());
        dataInsert.setUpdatedAt(new Date());
        TagManagement saveData = tagManagementJpa.save(dataInsert);
        //  return
        TagManagementRes returnData = new TagManagementRes();
        returnData.setEntityToRes(saveData);
        return returnData;
    }

    public TagManagementRes updateOne(TagManagementReq form) throws Exception {
        //  create insert
        TagManagement dataUpdate = tagManagementJpa.findById(form.getId()).get();
        dataUpdate.setDescription(form.getDescription());
        dataUpdate.setName(form.getName());
        dataUpdate.setRemark(form.getRemark());
        dataUpdate.setTotalPlayers(form.getTotalPlayers());
        dataUpdate.setUpdatedBy(UserLoginUtil.getUsername());
        TagManagement saveData = tagManagementJpa.save(dataUpdate);
        //  return
        TagManagementRes returnData = new TagManagementRes();
        returnData.setEntityToRes(saveData);
        return returnData;
    }

    public TagManagementRes delete(long id) throws Exception {
        //  create insert
        Optional<TagManagement> dataDelete = tagManagementJpa.findById(id);
        if (!dataDelete.isPresent())
            return null;
        tagManagementJpa.deleteById(id);
        TagManagementRes returnData = new TagManagementRes();
        returnData.setEntityToRes(dataDelete.get());
        return returnData;
    }
}
