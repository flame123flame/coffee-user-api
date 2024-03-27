package coffee.backoffice.admin.service;

import coffee.backoffice.admin.model.FwRole;
import coffee.backoffice.admin.repository.dao.FwRoleDao;
import coffee.backoffice.admin.repository.jpa.FwRoleRepository;
import coffee.backoffice.admin.repository.jpa.FwUserRoleMappingRepository;
import coffee.backoffice.admin.vo.req.FwRoleMenuAccessReq;
import coffee.backoffice.admin.vo.req.FwRoleReq;
import coffee.backoffice.admin.vo.res.FwRoleMenuAccessRes;
import coffee.backoffice.admin.vo.res.FwRoleRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.security.model.FwUser;
import framework.security.repository.FwUserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FwRoleService {
    @Autowired
    FwRoleRepository fwRoleRepository;

    @Autowired
    FwUserRepo fwUserRepository;
    @Autowired
    FwUserRoleMappingRepository fwUserRoleMappingRepository;

    @Autowired
    FwRoleDao fwRoleDao;

    @Autowired
    FwRoleMenuAccessService fwRoleMenuAccessService;

    private ModelMapper modelMapper = new ModelMapper();


    public DataTableResponse<FwRoleRes> paginate(DatatableRequest req) throws Exception {
        DataTableResponse<FwRoleRes> paginateData = fwRoleDao.paginate(req);
        DataTableResponse<FwRoleRes> dataTable = new DataTableResponse<>();
        List<FwRoleRes> data = paginateData.getData();
        for (FwRoleRes item : data
        ) {
            item.setFwUserCount(fwUserRoleMappingRepository.countByRoleCode(item.getCode()));
        }
        dataTable.setRecordsTotal(paginateData.getRecordsTotal());
        dataTable.setDraw(paginateData.getDraw());
        dataTable.setData(data);
        dataTable.setPage(req.getPage());
        return paginateData;
    }

    @Transactional
    public FwRoleRes insertOne(FwRoleReq req) throws Exception {
        Optional<FwRole> fwUserRoleModel = fwRoleRepository.findByName(req.getName());
        if (fwUserRoleModel.isPresent()) {
            throw new Exception("Duplicate Name");
        }
        FwRole fwRole = new FwRole();
        modelMapper.map(req, fwRole);
        FwRole savedData = fwRoleRepository.save(fwRole);
        for (FwRoleMenuAccessReq item : req.getFwRoleMenuAccessReq()
        ) {
            item.setFwUserRoleCode(savedData.getCode());
            fwRoleMenuAccessService.insertOne(item);
        }
        ;
        FwRoleRes fwRoleRes = new FwRoleRes();
        modelMapper.map(savedData, fwRoleRes);
        return fwRoleRes;
    }

    @Transactional
    public FwRoleRes update(Long id, FwRoleReq req) throws Exception {
        Optional<FwRole> fwUserRoleModel = fwRoleRepository.findById(id);
        if (!fwUserRoleModel.isPresent()) {
            throw new Exception("Not Found");
        }
        FwRole fwRole = fwUserRoleModel.get();
        modelMapper.map(req, fwRole);
        FwRole savedData = fwRoleRepository.save(fwRole);
        fwRoleMenuAccessService.deleteAllByUserRoleCode(savedData.getCode());
        for (FwRoleMenuAccessReq item : req.getFwRoleMenuAccessReq()
        ) {
            item.setFwUserRoleCode(savedData.getCode());
            fwRoleMenuAccessService.insertOne(item);
        }
        ;
        FwRoleRes fwRoleRes = new FwRoleRes();
        modelMapper.map(savedData, fwRoleRes);
        return fwRoleRes;
    }

    public FwRoleRes getMenuAccess(String fwUserRoleCode) throws Exception {
        FwRole fwRole = fwRoleRepository.findByCode(fwUserRoleCode);
        List<FwRoleMenuAccessRes> fwRoleMenuAccessResList = fwRoleMenuAccessService.getByUserRoleCode(fwUserRoleCode);
        FwRoleRes fwRoleRes = new FwRoleRes();
        modelMapper.map(fwRole, fwRoleRes);
        fwRoleRes.setFwRoleMenuAccessRes(fwRoleMenuAccessResList);
        return fwRoleRes;
    }

    public void deleteOne(Long id) throws Exception {
//      checked if this role have user
        Optional<FwRole> fwUserRole = fwRoleRepository.findById(id);
        if (!fwUserRole.isPresent())
            throw new Exception("Not Found");
//        List<FwUser> fwUser = fwUserRepository.findAllByFwUserRoleCode(fwUserRole.get().getCode());
//        if (fwUser!=null || fwUser.size() != 0)
//            throw new Exception("Have user in this role");
        fwRoleMenuAccessService.deleteAllByUserRoleCode(fwUserRole.get().getCode());
        fwRoleRepository.delete(fwUserRole.get());
    }

    public List<FwRoleRes> getAll() throws Exception {
        List<FwRole> fwRole = (List<FwRole>) fwRoleRepository.findAll();
        List<FwRoleRes> fwRoleRes = modelMapper.map(fwRole,new TypeToken<List<FwRoleRes>>(){}.getType());
        return fwRoleRes;
    }

    public FwRoleRes getById(Long id) throws Exception {
        Optional<FwRole> fwUserRole = fwRoleRepository.findById(id);
        if (!fwUserRole.isPresent())
            throw new Exception("Not Found");
        List<FwRoleMenuAccessRes> fwRoleMenuAccessResList = fwRoleMenuAccessService.getByUserRoleCode(fwUserRole.get().getCode());
        FwRoleRes fwRoleRes = new FwRoleRes();
        modelMapper.map(fwUserRole.get(), fwRoleRes);
        fwRoleRes.setFwRoleMenuAccessRes(fwRoleMenuAccessResList);
        return fwRoleRes;
    }
}
