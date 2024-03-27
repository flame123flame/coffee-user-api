package coffee.backoffice.admin.service;

import coffee.backoffice.admin.model.FwRoleMenuAccess;
import coffee.backoffice.admin.repository.jpa.FwRoleMenuAccessRepository;
import coffee.backoffice.admin.vo.req.FwRoleMenuAccessReq;
import coffee.backoffice.admin.vo.res.FwRoleMenuAccessRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FwRoleMenuAccessService {
    @Autowired
    FwRoleMenuAccessRepository fwRoleMenuAccessRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public void insertOne(FwRoleMenuAccessReq res){
        FwRoleMenuAccess fwUserRole = new FwRoleMenuAccess();
        fwUserRole.setFwUserRoleCode(res.getFwUserRoleCode());
        fwUserRole.setMenuId(res.getMenuId());
        fwRoleMenuAccessRepository.save(fwUserRole);
    }

    public void deleteAllByUserRoleCode(String code){
        List<FwRoleMenuAccess> fwRoleMenuAccesses = fwRoleMenuAccessRepository.findAllByFwUserRoleCode(code);
        fwRoleMenuAccessRepository.deleteAll(fwRoleMenuAccesses);
    }

    public List<FwRoleMenuAccessRes> getByUserRoleCode(String fwUserRoleCode) {
        List<FwRoleMenuAccess> fwRoleMenuAccesses = fwRoleMenuAccessRepository.findAllByFwUserRoleCode(fwUserRoleCode);
        List<FwRoleMenuAccessRes> fwRoleMenuAccessRes = new ArrayList<>();
        for (FwRoleMenuAccess item: fwRoleMenuAccesses
             ) {
            FwRoleMenuAccessRes listItem = new FwRoleMenuAccessRes();
            modelMapper.map(item,listItem);
            fwRoleMenuAccessRes.add(listItem);
        }
        return fwRoleMenuAccessRes;
    }

    public List<String> getByUserRoleCodeListString(String fwUserRoleCode) {
        List<FwRoleMenuAccess> fwRoleMenuAccesses = fwRoleMenuAccessRepository.findAllByFwUserRoleCode(fwUserRoleCode);
        List<String> fwRoleMenuAccessRes = new ArrayList<>();
        for (FwRoleMenuAccess item: fwRoleMenuAccesses
        ) {
            fwRoleMenuAccessRes.add(item.getMenuId());
        }
        return fwRoleMenuAccessRes;
    }
}
