package coffee.backoffice.admin.service;

import coffee.backoffice.admin.model.FwRole;
import coffee.backoffice.admin.model.FwUserRoleMapping;
import coffee.backoffice.admin.repository.dao.FwUserDao;
import coffee.backoffice.admin.repository.jpa.FwRoleRepository;
import coffee.backoffice.admin.repository.jpa.FwUserRoleMappingRepository;
import coffee.backoffice.admin.vo.req.FwUserReq;
import coffee.backoffice.admin.vo.req.FwUserRoleMappingReq;
import coffee.backoffice.admin.vo.res.FwUserRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.security.model.FwUser;
import framework.security.repository.FwUserRepo;
import framework.utils.UserLoginUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FwUserService {

    @Autowired
    FwUserDao fwUserDao;
    @Autowired
    FwUserRepo fwUserRepository;
    @Autowired
    FwRoleRepository fwRoleRepository;
    @Autowired
    FwUserRoleMappingRepository fwUserRoleMappingRepository;
    @Autowired
    FwUserRoleMappingService fwUserRoleMappingService;
    
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DataTableResponse<FwUserRes> paginate(DatatableRequest req) throws Exception {
        DataTableResponse<FwUserRes> paginateData = fwUserDao.paginate(req);
        DataTableResponse<FwUserRes> dataTable = new DataTableResponse<>();
        List<FwUserRes> data = paginateData.getData();
        for (FwUserRes item:data
             ) {
            List<FwRole> fwRole = fwRoleRepository.getUserRole(item.getUsername());
            if (fwRole !=null)
                item.setRole(fwRole);
        }
        dataTable.setRecordsTotal(paginateData.getRecordsTotal());
        dataTable.setDraw(paginateData.getDraw());
        dataTable.setData(data);
        dataTable.setPage(req.getPage());
        return paginateData;
    }

    @Transactional
    public FwUserRes insertOne(FwUserReq req) throws Exception{
        FwUser checkUserName = fwUserRepository.findByUsername(req.getUsername());
            if (checkUserName != null)
                throw new Exception("Duplicate Username");
        FwUser fwUserRole = new FwUser();
        modelMapper.map(req, fwUserRole);
        for (FwUserRoleMappingReq fwUserRoleMappingReq:req.getFwUserRoleMappingReqList()
             ) {
            fwUserRoleMappingService.insertOne(fwUserRoleMappingReq,req.getUsername());
        }
        fwUserRole.setPassword(passwordEncoder.encode(req.getPassword()));
        FwUser savedData = fwUserRepository.save(fwUserRole);
        FwUserRes fwUserRes = new FwUserRes();
        modelMapper.map(savedData,fwUserRes);
        return fwUserRes;
    }

    @Transactional
    public FwUserRes update(Long id, FwUserReq req) throws Exception{
        Optional<FwUser> fwUser = fwUserRepository.findById(id);
        if (!fwUser.isPresent()){
            throw new Exception("Not Found");
        }
        FwUser fwUserRole = fwUser.get();
        List<FwUserRoleMapping> fwUserRoleMappings = fwUserRoleMappingRepository.findAllByUsername(fwUser.get().getUsername());
        fwUserRoleMappingRepository.deleteAll(fwUserRoleMappings);
        for (FwUserRoleMappingReq fwUserRoleMappingReq:req.getFwUserRoleMappingReqList()
        ) {
            fwUserRoleMappingService.insertOne(fwUserRoleMappingReq,fwUser.get().getUsername());
        }
        fwUserRole.setFwUsersId(id);
        fwUserRole.setIsDisable(req.getIsDisable());
        fwUserRole.setIsActive(req.getIsActive());
        fwUserRole.setUpdatedBy(UserLoginUtil.getUsername());
        fwUserRole.setUpdatedDate(new Date());
        FwUser savedData = fwUserRepository.save(fwUserRole);
        FwUserRes fwUserRes = new FwUserRes();
        modelMapper.map(savedData,fwUserRes);
        return fwUserRes;
    }

    @Transactional
    public FwUserRes updatePassword(Long id, FwUserReq req) throws Exception{
        Optional<FwUser> fwUser = fwUserRepository.findById(id);
        if (!fwUser.isPresent()){
            throw new Exception("Not Found");
        }
        FwUser fwUserRole = fwUser.get();
        fwUserRole.setFwUsersId(id);
        fwUserRole.setPassword(passwordEncoder.encode(req.getPassword()));
        fwUserRole.setUpdatedBy(UserLoginUtil.getUsername());
        fwUserRole.setUpdatedDate(new Date());
        FwUser savedData = fwUserRepository.save(fwUserRole);
        FwUserRes fwUserRes = new FwUserRes();
        modelMapper.map(savedData,fwUserRes);
        return fwUserRes;
    }


    @Transactional
    public void deleteOne(Long id) throws Exception {
        Optional<FwUser> fwUser = fwUserRepository.findById(id);
        if (!fwUser.isPresent())
            throw new Exception("Not Found");
        fwUserRepository.delete(fwUser.get());
        List<FwUserRoleMapping> fwUserRoleMappings = fwUserRoleMappingRepository.findAllByUsername(fwUser.get().getUsername());
        fwUserRoleMappingRepository.deleteAll(fwUserRoleMappings);
    }

    public FwUserRes getById(Long id) throws Exception {
        Optional<FwUser> fwUser = fwUserRepository.findById(id);
        if (!fwUser.isPresent())
            throw new Exception("Not Found");
        FwUserRes res = new FwUserRes();
        modelMapper.map(fwUser.get(),res);
        List<FwRole> fwRole = fwRoleRepository.getUserRole(fwUser.get().getUsername());
        if (fwRole !=null)
            res.setRole(fwRole);
        return res;
    }
    
    public FwUserRes getUserRole() throws Exception {
        FwUser fwUser = fwUserRepository.findByUsername(UserLoginUtil.getUsername());
        FwUserRes res = new FwUserRes();
        modelMapper.map(fwUser,res);
        List<FwRole> fwRole = fwRoleRepository.getUserRole(fwUser.getUsername());
        if (fwRole !=null)
            res.setRole(fwRole);
        return res;
    }
}
