package coffee.backoffice.admin.service;

import coffee.backoffice.admin.model.FwUserRoleMapping;
import coffee.backoffice.admin.repository.jpa.FwRoleMenuAccessRepository;
import coffee.backoffice.admin.repository.jpa.FwUserRoleMappingRepository;
import coffee.backoffice.admin.vo.req.FwUserRoleMappingReq;
import coffee.backoffice.admin.vo.res.FwUserRoleMappingRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwUserRoleMappingService {
    @Autowired
    FwUserRoleMappingRepository fwUserRoleMappingRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public FwUserRoleMappingRes insertOne(FwUserRoleMappingReq req,String username){
        FwUserRoleMapping fwUserRoleMapping = new FwUserRoleMapping();
        modelMapper.map(req,fwUserRoleMapping);
        fwUserRoleMapping.setUsername(username);
        FwUserRoleMapping savedData = fwUserRoleMappingRepository.save(fwUserRoleMapping);
        FwUserRoleMappingRes fwUserRoleMappingRes = new FwUserRoleMappingRes();
        modelMapper.map(savedData,fwUserRoleMappingRes);
        return fwUserRoleMappingRes;
    }
}
